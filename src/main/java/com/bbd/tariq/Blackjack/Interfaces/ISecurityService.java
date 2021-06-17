package com.bbd.tariq.Blackjack.Interfaces;

public interface ISecurityService {

    public String hashPassword(String password);
    public boolean comparePasswords(String password, String hashedPassword);

    public String generateJWT(String username);

    public boolean checkAuthentication(String jwt);
}
