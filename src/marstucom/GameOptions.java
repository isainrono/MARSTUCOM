/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marstucom;

import Exceptions.UserExceptions;
import Model.SuperHero;
import Model.User;
import Persistence.MarstucomDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author isain
 */
public class GameOptions {

    static MarstucomDAO MarsDAO = new MarstucomDAO();

    public static void init() throws SQLException {
        MarsDAO.connect();
    }

    public static void exit() throws SQLException {
        MarsDAO.disconnect();
    }

    public static void userRegister() throws SQLException, UserExceptions {
        User user = askUserDatas();
        MarsDAO.insertUser(user);
    }

    public static User askUserDatas() throws SQLException {
        
        User user = new User();

        boolean exitPass = false;
        boolean  exitHero = false;
        
        String name = Control.askString("Ingrese un nombre de usuario!!");
        user.setName(name);
        String pass = Control.askString("Ingrese contraseña");

        do {
            String validatePass = Control.askString("Verique contraseña");
            try {
                if (validatePass.equals(pass)) {
                    exitPass = true;
                } else {
                    throw new Exceptions.UserExceptions("NOT 002");
                }
            } catch (UserExceptions ux) {
            }

        } while (!exitPass);
        user.setPass(pass);

        heroList();

        do {
            int hero = Control.askInteger("Con que Heroe desea Jugar?");
            try {
                if (hero > 6 || hero < 0) {
                    System.out.println("invalido");
                    throw new Exceptions.UserExceptions("NOT 003");
                } else {
                    // rescatara el heroe de de la lista no es necesario el for mirarlo
                    for (int i = 0; i < MarsDAO.selectHeroList().size(); i++) {
                        MarsDAO.selectHeroList().get(hero - 1);
                    }
                    exitHero = true;
                }
            } catch (UserExceptions ux) {
            }

        } while (!exitHero);
        
        
        
        return user;
    }

    public static void heroList() throws SQLException {
        System.out.println("=====================");
        System.out.println("     LISTA HEROES    ");
        System.out.println("=====================\n");

        ArrayList heroList = MarsDAO.selectHeroList();
        for (int i = 0; i < heroList.size(); i++) {
            System.out.println(i + 1 + " - " + heroList.get(i).toString());
        }
    }
}
