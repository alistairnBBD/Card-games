package com.bbd.tariq.Blackjack.Controllers;

import com.bbd.tariq.Blackjack.Exceptions.ContentNotFoundException;
import com.bbd.tariq.Blackjack.Models.Auth.User;
import com.bbd.tariq.Blackjack.Repos.UserRepo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepo _userRepo;

    public UserController() {
        _userRepo = new UserRepo();
    }

    @GetMapping("/getUser")
    public User getUser(@RequestParam String username) {
        var user = _userRepo.Get(username);
        if(user == null)
            throw new ContentNotFoundException("User not found");

        user.set_hashedPassword("");
        return user;
    }
}
