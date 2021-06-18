package com.bbd.tariq.Blackjack.Models.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {

    private int id;
    private String _userName;
    private String _hashedPassword;

    public User(){

    }

    public User(String userName, String hashedPassword) {
        _userName = userName;
        _hashedPassword = hashedPassword;
    }

}
