/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Exceptions.UserExceptions;
import Model.Enemie;
import Model.Gem;
import Model.Place;
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

    // ------------------------------------------------------------   Metodos de juegos
    // Metodo sql que regresa el ranking ordenado
    public ArrayList<User> selectRanking() throws SQLException {
        ArrayList<User> rankingList = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "select user.username, user.superhero, count(*) as gemas,user.level, user.points \n"
                + "from user inner join gem on user.username = gem.user where gem.user = gem.owner group by user.username order by gemas desc, level desc,points desc;";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            //rankingList.add(new User(rs.getString("username"), rs.getString("superhero"), Integer.parseInt(rs.getString("level")),Integer.parseInt(rs.getString("points")), Integer.parseInt(rs.getString("gemas"))));
            rankingList.add(new User(rs.getString("username"), rs.getString("superhero"), Integer.parseInt(rs.getString("level")), Integer.parseInt(rs.getString("points")), Integer.parseInt(rs.getString("gemas"))));
        }

        st.close();
        rs.close();

        return rankingList;
    }

    // ------------------------------------------------------------   Metodos de gemas
    // Metodo que cambia el propietario de una gema
    public void changeGemOwner(String gemOwnwer, String gemName) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("update gem set owner = ? where name = ? and owner is null;");
        
        ps.setString(1, gemOwnwer);
        ps.setString(2, gemName);
        ps.executeUpdate();
        ps.close();

    }

    // Metodo que muestra totas las gemas del juego
    public ArrayList<Gem> selectGemList() throws SQLException {
        ArrayList<Gem> gemsList = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "select * from gem;";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            gemsList.add(new Gem(rs.getString("name"), rs.getString("user"), rs.getString("owner"), rs.getString("place")));
        }

        st.close();
        rs.close();

        return gemsList;
    }

    // ------------------------------------------------------------   Metodos de enemigos
    public ArrayList<Enemie> selectEnemyList() throws SQLException {
        ArrayList<Enemie> enemiesList = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "select * from enemy;";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            enemiesList.add(new Enemie(rs.getString("name"), rs.getString("debility"), Integer.parseInt(rs.getString("level")), rs.getString("place")));
        }

        st.close();
        rs.close();

        return enemiesList;
    }

    // ------------------------------------------------------------   Metodos de lugares
    // Metodo que muestra informacion de un lugar en especifico
    public Place placeInfo(String name) throws SQLException {
        Place place = new Place();

        Statement st = connection.createStatement();
        String query = "select * from place where name='" + name + "';";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            place = new Place(rs.getString("name"), rs.getString("description"), rs.getString("north"), rs.getString("south"), rs.getString("east"), rs.getString("west"));
        }

        st.close();
        rs.close();
        return place;
    }

    // Metodo que muestra todo los lugares del juego
    public ArrayList<Place> selectPlaceList() throws SQLException {
        ArrayList placesList = new ArrayList();

        Statement st = connection.createStatement();
        String query = "select * from place;";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            placesList.add(new Place(rs.getString("name"), rs.getString("description"), rs.getString("north"), rs.getString("south"), rs.getString("east"), rs.getString("west")));
        }

        st.close();
        rs.close();
        return placesList;
    }

    // ------------------------------------------------------------   Metodos de Usuario
    // Metodo que elimina usuario
    public void deleteUser(String name) throws SQLException {
        Statement st = connection.createStatement();
        PreparedStatement pst = connection.prepareStatement("delete from user where username = ?;");
        pst.setString(1, name);
        pst.execute();
        pst.close();

    }

    // Metodo que regresa todos los datos del usuario
    public User userInfo(String name) throws SQLException {
        User user = new User();
        Statement st = connection.createStatement();
        String query = "select * from user where username = '" + name + "';";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            user = new User(rs.getString("username"), rs.getString("pass"), rs.getString("superhero"), Integer.parseInt(rs.getString("level")), Integer.parseInt(rs.getString("points")));
        }

        st.close();
        rs.close();
        return user;
    }

    // Metodo que regresa el pass del usuario
    public String userPass(String name) throws SQLException {
        String pass = "";
        Statement st = connection.createStatement();
        String query = "select pass from user  where username = '" + name + "';";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            pass = rs.getString("pass");
        }

        st.close();
        rs.close();
        return pass;
    }

    // Metodo que lista todos los usuarios
    public ArrayList<User> selectUser() throws SQLException {
        ArrayList userList = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "select * from user;";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            userList.add(new User(rs.getString("username")));
        }

        st.close();
        rs.close();
        return userList;
    }

    // Metodo que lista todos los superHeroes
    public ArrayList<SuperHero> selectHeroList() throws SQLException {
        ArrayList heroList = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "select * from superhero;";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            heroList.add(new SuperHero(rs.getString("name"), rs.getString("superpower")));
        }

        st.close();
        rs.close();
        return heroList;
    }

    public void insertUser(User user) throws SQLException, UserExceptions {
        if (existUser(user)) {
            throw new Exceptions.UserExceptions("NOT 001");
        } else {
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

    }

    private boolean existUser(User user) throws SQLException {
        Statement st = connection.createStatement();
        String query = "select * from user where username='" + user.getName() + "';";
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
