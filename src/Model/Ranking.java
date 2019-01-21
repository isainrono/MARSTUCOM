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
public class Ranking {
    private String userName;
    private int level;
    private int gemNumber;
    
    public Ranking(){
    }
    
    public Ranking(String userName, int level, int genmNumber){
        this.userName = userName;
        this.level = level;
        this.gemNumber = genmNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGemNumber() {
        return gemNumber;
    }

    public void setGemNumber(int gemNumber) {
        this.gemNumber = gemNumber;
    }

    @Override
    public String toString() {
        return "Ranking{" + "userName=" + userName + ", level=" + level + ", gemNumber=" + gemNumber + '}';
    }
    
    
}
