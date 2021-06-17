package com.bbd.tariq.Blackjack.Repos;

import com.bbd.tariq.Blackjack.Models.Auth.User;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthRepo {

    private ArrayList<User> users;

    public AuthRepo() {
        users = new ArrayList<>();
    }

    public void Insert(User user) {
        users.add(user);
    }

    public User Get(String username) {
        return users.stream().filter(u -> u.get_userName().equals(username)).findFirst().orElse(null);
    }
}
