package com.bbd.tariq.Blackjack.Models.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {

    private String _userName;
    private String _hashedPassword;

    public User(String userName, String hashedPassword) {
        _userName = userName;
        _hashedPassword = hashedPassword;
    }

}
