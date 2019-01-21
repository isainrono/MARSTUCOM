/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marstucom;

import Exceptions.UserExceptions;
import Model.SuperHero;
import Model.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author isain
 */
public class Marstucom {

    /**
     * @param args the command line arguments
     */
    public static User loggedUser = new User("");
    public static void main(String[] args) throws SQLException, UserExceptions {
        
        GameOptions.init();

        boolean exit = false;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        
        try {
            do {
                line = br.readLine();
                String[] datas = line.split(" ");

                // He abierto un switch con el filtro de opciones para el juego, segun los dartos que se ingresen por terminal.
                switch (datas.length) {
                    case 1:
                        if(datas[0].equalsIgnoreCase("V")){
                            GameOptions.showSuperHeros(datas[0]);
                        } else {
                            GameOptions.moveUser(datas[0]);
                        } 
                        break;
                    case 2:
                        if(datas[0].equalsIgnoreCase("D")){
                            GameOptions.deleteUser(datas[1]);
                        } else {
                            System.out.println("invalit option");
                        }
                        break;
                    case 3:
                        GameOptions.UserLogin(datas[0],datas[1], datas[2]);
                        break;
                    case 4:
                        // Desde aquí accedo a un metodo que registra usuarios en la base de datos
                        //GameOptions.userRegister();
                        GameOptions.userRegister(datas[0],datas[1], datas[2], datas[3]);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("invalit option!!");
                        break;
                }

            } while (!exit);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        GameOptions.exit();
    }

}
