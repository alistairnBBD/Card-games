package com.bbd.tariq.Blackjack.Controllers;

import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Interfaces.ISecurityService;
import com.bbd.tariq.Blackjack.Models.Auth.User;
import com.bbd.tariq.Blackjack.Repos.AuthRepo;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final ISecurityService _securityService;
    private final AuthRepo _authRepo;

    public AuthController(ISecurityService securityService) {

        _securityService = securityService;
        _authRepo = new AuthRepo();
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, String password) {
        String hashedPassword = _securityService.hashPassword(password);
        _authRepo.Insert(new User(username,hashedPassword));

        return "User successfully registered, please log in!";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, String password) {
        String hashedPassword = _securityService.hashPassword(password);

        User user = _authRepo.Get(username);

        if(user == null)
        {
            return "Invalid username and/or password";
        }

        if(!_securityService.comparePasswords(password,user.get_hashedPassword()))
        {
            return "Invalid username and/or password";
        }

        String jwt = _securityService.generateJWT(username);

        return String.format("token: %s",jwt);
    }

}
