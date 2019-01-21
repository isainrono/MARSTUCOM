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
public class Enemie {
    private String name;
    private String debility;
    private int level;
    private String place;
    
    public Enemie() {
    }
    
    public Enemie(String name, String debility, int level, String place) {
        this.name = name;
        this.debility = debility;
        this.level = level;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDebility() {
        return debility;
    }

    public void setDebility(String debility) {
        this.debility = debility;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    
    
    @Override
    public String toString() {
        return "Name: " + this.name + " - " + "Debility: " + this.debility + " - " + "Level: " + this.level; 
    }
    
    
}
