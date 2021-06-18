package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.BlackjackApplication;
import com.bbd.tariq.Blackjack.Interfaces.IObserver;
import com.bbd.tariq.Blackjack.Interfaces.ISecurityService;
import com.bbd.tariq.Blackjack.Interfaces.ISubject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

public class SecurityServiceStrategy implements ISecurityService, IObserver {

    private HashMap<String,String> activeJWTS;
    private ArrayList<IObserver> observers;

    public SecurityServiceStrategy() {
        activeJWTS = new HashMap<>();
        observers = new ArrayList<>();
        BlackjackApplication._observers.add(this);
    }

    @Override
    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public boolean comparePasswords(String password, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password,hashedPassword);
    }

    @Override
    public String generateJWT(String username) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long currentMilliseconds = System.currentTimeMillis();
        Date now = new Date(currentMilliseconds);

        long expirationMilliseconds = currentMilliseconds+14400000;

           JwtBuilder builder = Jwts.builder()
                   .setId(username)
                   .setIssuedAt(now)
                   .signWith(signatureAlgorithm, Base64.getEncoder().encodeToString(new String("test secret key").getBytes()))
                   .setExpiration(new Date(expirationMilliseconds));

           var jwt =  builder.compact();

           if(!activeJWTS.containsKey(username)){
               activeJWTS.put(username,jwt);
           }
           else {
               activeJWTS.replace(username,jwt);
           }

           return jwt;

    }

    @Override
    public boolean checkAuthentication(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(new String("test secret key").getBytes()))
                .parseClaimsJws(jwt).getBody();

        if(!activeJWTS.containsKey(claims.getId())){
            return false;
        }

        else if(claims.getExpiration().before(new Date(System.currentTimeMillis())))
        {
            activeJWTS.remove(claims.getId());
            return false;
        }


        return  true;

    }

    @Override
    public void update() {

        activeJWTS.entrySet().removeIf(kv -> {
            var jwt = kv.getValue();
            Claims claim = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(new String("test secret key").getBytes()))
                    .parseClaimsJws(jwt).getBody();
            return claim.getExpiration().before(new Date(System.currentTimeMillis()));
        });

    }
}
