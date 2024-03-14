package com.example.bd_iage_fx_2024_dk.repository;

import com.example.bd_iage_fx_2024_dk.model.DB;
import com.example.bd_iage_fx_2024_dk.model.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PersonneRepository {
    private Connection connection;

    public PersonneRepository() {
        this.connection = new DB().getConnection();
    }

    public void add (Personne personne){
         String sql = "INSERT INTO personne (nom,prenom,age) values (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,personne.getNom());
            preparedStatement.setString(2,personne.getPrenom());
            preparedStatement.setInt(3,personne.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update (Personne personne){
        String sql = "UPDATE personne set nom = ?, prenom =?, age=? where id =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,personne.getNom());
            preparedStatement.setString(2,personne.getPrenom());
            preparedStatement.setInt(3,personne.getAge());
            preparedStatement.setInt(4,personne.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ObservableList<Personne> findAll() throws SQLException {
        System.out.println("1234");
        ObservableList<Personne> pers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM personne";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result= statement.executeQuery();

        while(result.next()){

            Personne p = new Personne(result.getString("nom"),
                    result.getString("prenom"),
                    result.getInt("age"));
            p.setId(result.getInt("id"));

            pers.add(p);
        }

        return pers;
    }

    public void delete(int id) throws SQLException {
        String sql = "Delete from personne where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        statement.executeUpdate();

    }
}
