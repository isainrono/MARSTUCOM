/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marstucom;

import Exceptions.UserExceptions;
import Model.SuperHero;
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
                        System.out.println("1 letra");
                        break;
                    case 2:
                        System.out.println("2 letra");
                        break;
                    case 3:
                        System.out.println("3 letra");
                        break;
                    case 4:
                        // Desde aquí accedo a un metodo que registra usuarios en la base de datos                      
                        GameOptions.userRegister();
                    case 5:
                        exit = true;
                        break;
                    default:
                        exit = true;

                }

            } while (!exit);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        GameOptions.exit();
    }

}
