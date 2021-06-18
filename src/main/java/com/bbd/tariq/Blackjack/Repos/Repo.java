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
//        String dbUrl = System.getenv("POSTGRES_URL");
//        String username = System.getenv("POSTGRES_UNAME");
//        String pass = System.getenv("POSTGRES_PWD");

        String dbUrl = ("jdbc:postgresql://ec2-54-225-228-142.compute-1.amazonaws.com:5432/ddfr5ouosdofbs");
        String username = ("jsyzbseolvtiic");
        String pass = ("bddf4efc065dae2484562b869685e7890eb9291aa6692e3cc4754e47470f6e7c");
        try {
            return DriverManager.getConnection(dbUrl,username,pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

}
