package com.bbd.tariq.Blackjack.Repos;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.Exceptions.BadRequestException;
import com.bbd.tariq.Blackjack.Models.BlackjackGame.BlackjackGameModel;
import com.bbd.tariq.Blackjack.Models.GameModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class BlackjackRepo extends Repo {

    private HashMap<String,GameModel> games = new HashMap<>();
    @Override
    public GameModel Get(String id) {

        var dbConn = BlackjackRepo.getConnection();
        GameModel gameInstance = null;
        String sql = "SELECT \"Games\".\"instanceData\" FROM  public.\"Games\" WHERE \"gameId\" = ?";
        try {
            PreparedStatement statement = dbConn.prepareStatement(sql);
            statement.setString(1,id);
            var res = statement.executeQuery();
            res.next();
            gameInstance = objectMapper.readValue(res.getString("instanceData"), BlackjackGameModel.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        if(gameInstance==null){
            throw new BadRequestException("Failed to find game instance with provided game Id!");
        }
        return gameInstance;
    }

    @Override
    public void Insert(GameModel model) {
        var dbConn = BlackjackRepo.getConnection();
        String sql = "INSERT INTO public.\"Games\" (\"gameId\", \"instanceData\") VALUES (?,to_json(?::json))";
        try {
            PreparedStatement statement = dbConn.prepareStatement(sql);
            statement.setString(1,model.gameId);
            statement.setString(2,objectMapper.writeValueAsString(model));
            statement.execute();
            dbConn.close();
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void Update(GameModel model) {
        var dbConn = BlackjackRepo.getConnection();
        String sql = "UPDATE  public.\"Games\" set \"instanceData\"= (to_json(?::json))";
        try {
            PreparedStatement statement = dbConn.prepareStatement(sql);
            statement.setString(1,objectMapper.writeValueAsString(model));
            statement.execute();
            dbConn.close();
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }
    }

}