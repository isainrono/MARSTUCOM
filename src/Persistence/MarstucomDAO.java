/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Exceptions.UserExceptions;
import Model.SuperHero;
import Model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.omg.CORBA.UserException;

/**
 *
 * @author isain
 */
public class MarstucomDAO {

    private Connection connection;
    
    // Metodo que lista todos los superHeroes
    public ArrayList<SuperHero> selectHeroList() throws SQLException {
        ArrayList heroList = new ArrayList<>();
        
        Statement st = connection.createStatement();
        String query = "select * from superhero;";
        ResultSet rs = st.executeQuery(query);

        while(rs.next()) {
            heroList.add(new SuperHero(rs.getString("name"), rs.getString("superpower")));
        }
        
        st.close();
        rs.close();
        disconnect();
        return heroList;
    }
    
    public void insertUser(User user) throws SQLException, UserExceptions {
        if (existUser(user)) {
            throw new Exceptions.UserExceptions("NOT 001");
        }
        
        PreparedStatement ps = connection.prepareStatement("insert into user values (?, ?, ?, ?, ?, ?);");
        ps.setString(1, user.getName());
        ps.setString(2, user.getPass());
        ps.setInt(3, user.getLevel());
        ps.setString(4, user.getHero());
        ps.setString(5, user.getPlace());
        ps.setInt(6, user.getPoints());
        ps.executeUpdate();
        ps.close();
        
    }

    private boolean existUser(User user) throws SQLException {
        Statement st = connection.createStatement();
        String query = "select * from user where nombre='" + user.getName() + "';";
        ResultSet rs = st.executeQuery(query);
        boolean exist = rs.next();
        rs.close();
        st.close();

        return exist;
    }

    public void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/marvel";
        String user = "root";
        String password = "";
        connection = DriverManager.getConnection(url, user, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
