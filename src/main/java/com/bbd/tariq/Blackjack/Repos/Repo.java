package com.bbd.tariq.Blackjack.Repos;


import com.bbd.tariq.Blackjack.Models.GameModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Repo{

    public ObjectMapper objectMapper;
    //Probably setup the connection to the db
    public Repo(){
        this.objectMapper = new ObjectMapper();
    }

    public abstract GameModel Get(String id);
    public abstract void Insert(GameModel model);
    public abstract void Update(GameModel model);

    public static Connection getConnection() {
        String dbUrl = System.getenv("POSTGRES_URL");
        String username = System.getenv("POSTGRES_UNAME");
        String pass = System.getenv("POSTGRES_PWD");
        try {
            return DriverManager.getConnection(dbUrl,username,pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

}
