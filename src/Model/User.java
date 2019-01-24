/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author isain
 */
public class User {
    private String name;
    private String pass;
    private String hero;
    private int level = 1;
    private int points = 0;
    private String place = "New York";
    private int gems;
    
    public User() {
    }
    
    public User(String name, String hero, int level, int points, int gems) {
        this.name = name;
        this.hero = hero;
        this.level = level;
        this.points = points;
        this.gems = gems;
    }
    
    public User(String name, String pass, String hero, int level, int points) {
        this.name = name;
        this.pass = pass;
        this.hero = hero;
        this.level = level;
        this.points = points;
    }
    
    public User(String name) {
        this.name = name;
    }
    
    public User(String name, String pass, String hero) {
        this.name = name;
        this.pass = pass;
        this.hero = hero;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getGems() {
        return gems;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }
    
    
}
