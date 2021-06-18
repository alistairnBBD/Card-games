package com.bbd.tariq.Blackjack.Controllers;

import com.bbd.tariq.Blackjack.Interfaces.ISecurityService;
import com.bbd.tariq.Blackjack.Models.Auth.User;
import com.bbd.tariq.Blackjack.Repos.UserRepo;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final ISecurityService _securityService;
    private final UserRepo _authRepo;

    public AuthController(ISecurityService securityService) {

        _securityService = securityService;
        _authRepo = new UserRepo();
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, String password) {
        String hashedPassword = _securityService.hashPassword(password);
        var res = _authRepo.Insert(new User(username,hashedPassword));

        if(!res.equals("success")) {
            return res;
        }

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
