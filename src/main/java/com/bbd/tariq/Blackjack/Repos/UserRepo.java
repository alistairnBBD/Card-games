package com.bbd.tariq.Blackjack.Repos;

import com.bbd.tariq.Blackjack.Exceptions.BadRequestException;
import com.bbd.tariq.Blackjack.Models.Auth.User;
import com.bbd.tariq.Blackjack.Models.BlackjackGame.BlackjackGameModel;
import com.bbd.tariq.Blackjack.Models.GameModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserRepo {

    private ArrayList<User> users;

    public UserRepo() {
        users = new ArrayList<>();
    }

    public String Insert(User user) {
        var dbConn = BlackjackRepo.getConnection();
        String sql = "INSERT INTO public.\"Users\" (\"username\", \"hashed_password\") VALUES (?,?)";
        try {
            PreparedStatement statement = dbConn.prepareStatement(sql);
            statement.setString(1,user.get_userName());
            statement.setString(2,user.get_hashedPassword());
            statement.execute();
            dbConn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
           return throwables.getMessage();
        }
        return "success";
    }

    public User Get(String username) {
        var dbConn = BlackjackRepo.getConnection();
        User user = new User();
        String sql = "SELECT *  FROM  public.\"Users\" WHERE \"username\" = ?";
        try {
            PreparedStatement statement = dbConn.prepareStatement(sql);
            statement.setString(1,username);
            var res = statement.executeQuery();

            if(!res.next())
                return null;

            user.setId(Integer.parseInt(res.getString("id")));
            user.set_userName(res.getString("username"));
            user.set_hashedPassword(res.getString("hashed_password"));

            dbConn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return user;
    }

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
