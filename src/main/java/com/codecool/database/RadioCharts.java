package com.codecool.database;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RadioCharts {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public RadioCharts(String url, String user, String password) {
        this.dbUrl = url;
        this.dbUser = user;
        this.dbPassword = password;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.err.println("Database not reachable.");
        }
        return connection;
    }

    public String getMostPlayedSong() {

        String result = "";
        String query = "SELECT song FROM music_broadcast GROUP BY song ORDER BY SUM(times_aired) DESC LIMIT 1;";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }



    public String getMostActiveArtist() {
        String result = "";
        String query = "SELECT artist FROM music_broadcast GROUP BY artist ORDER BY COUNT(DISTINCT song) DESC LIMIT 1;";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                result = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
