/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marstucom;

import Exceptions.UserExceptions;
import Model.Enemie;
import Model.SuperHero;
import Model.User;
import Persistence.MarstucomDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Place;
import Model.Gem;
import static marstucom.Marstucom.loggedUser;

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
    
    // Desde el siguiente metodo filtro los errores al intetar un usuario obtener una nueva gema
    public static void getGem(String[] datas) throws SQLException {
        if (loggedUser.getName() != "") {
            if (datas.length != 3) {
                System.out.println("[ Wrong command ]");
            } else {
                takeGem(datas[1], datas[2]);

            }
        } else {
            System.out.println("[ You are not logged in ]");
            System.out.println("You need do loggin for use this option!!");
        }

    }

    // Metodo que lista el ranking de usuarios del juego
    public static void showRanking() throws SQLException {
        System.out.println(" - Ranking -");
        for (User user : MarsDAO.selectRanking()) {
            System.out.println(user.getName() + "    " + user.getHero() + "   " + user.getGems() + "   " + user.getLevel() + "   " + user.getPoints());
        }
    }

    // Metodo que permite eliminar un usuario de la base de datos
    public static void deleteUser(String pass) throws SQLException {
        if (loggedUser.getName() != "") {
            if (loggedUser.getPass().equals(pass)) {
                MarsDAO.deleteUser(loggedUser.getName());
                System.out.println("User deleted correctly");
            } else {
                System.out.println("Incorrect pass");
            }
        } else {
            System.out.println("[ You are not logged in ]");
        }

    }

    // Metodo que permite mover de posición el usuario
    public static void moveUser(String option) throws SQLException {
        if (loggedUser.getName() != "") {
            validateRutOption(option);
        } else {
            System.out.println("You need do loggin for use this option!!");
        }

    }

    public static void UserLogin(String option, String name, String pass) throws SQLException {
        if (option.equalsIgnoreCase("l")) {
            if (checkUserName(name)) {
                if (checkLogin(name, pass)) {
                    userDatas(name);
                } else {
                    System.out.println("[ Pass is incorrect ]");
                }
            } else {
                System.out.println("[ Username is incorrect ]");
            }
        } else {
            System.out.println("[ Wrong number of arguments ]");
        }
        System.out.println("");
    }

    public static void userRegister(String option, String name, String pass, String hero) throws SQLException, UserExceptions {
        boolean exit2 = false;
        User user = new User();

        // filtro de errores y registro de usuario
        if (option.equalsIgnoreCase("r")) {
            if (CheckHero(hero)) {
                if (!checkUserName(name)) {
                    do {
                        String validatePass = Control.askString("Confirm pasword!");
                        if (checkPass(pass, validatePass)) {
                            exit2 = true;
                            user.setName(name);
                            user.setPass(pass);
                            user.setHero(hero);
                            MarsDAO.insertUser(user);
                            // ----------------------
                            System.out.println("User registered!!");
                            // ----------------------
                        }
                    } while (!exit2);
                } else {
                    System.out.println("[ User already exists ]");
                }
            } else {
                System.out.println("[ There isn't a superhero with that name ]");
            }
        } else {
            System.out.println("[ Wrong number of arguments ]");
        }

    }

    public static void showSuperHeros(String option) throws SQLException {
        if (option.equalsIgnoreCase("v")) {
            heroList();
        } else {
            System.out.println("[ Wrong number of arguments ]");
        }
    }

    public static void heroList() throws SQLException {
        System.out.println("=====================");
        System.out.println("     HERO LIST    ");
        System.out.println("=====================\n");

        ArrayList heroList = MarsDAO.selectHeroList();
        for (int i = 0; i < heroList.size(); i++) {
            System.out.println(i + 1 + " - " + heroList.get(i).toString());
        }
    }

    // METODOS DE USUARIO -------------------------------------------------------> M USUARIOS
    //Metodo que valida si el nombre de usuario es correcto
    public static boolean checkUserName(String name) throws SQLException {
        for (int i = 0; i < MarsDAO.selectUser().size(); i++) {
            if (MarsDAO.selectUser().get(i).getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    //Metodo que valida que el superHeroe sea correcto
    public static boolean CheckHero(String hero) throws SQLException {
        for (int i = 0; i < MarsDAO.selectHeroList().size(); i++) {
            if (MarsDAO.selectHeroList().get(i).getName().equalsIgnoreCase(hero)) {
                return true;
            }
        }

        return false;
    }

    //Metodo que valida la contraseña del usuario
    public static boolean checkPass(String pass, String validatePass) throws UserExceptions {
        if (validatePass.equals(pass)) {
            return true;
        } else {
            throw new Exceptions.UserExceptions("NOT 002");
        }
    }

    //Metodo que verifica que la contraseña del usuario sea correckta
    public static boolean checkLogin(String name, String pass) throws SQLException {

        String vPass = MarsDAO.userPass(name);

        if (pass.equals(vPass)) {
            return true;
        }

        return false;
    }

    //Metodo que optiene todo el usuario con solo su nombre
    public static void userDatas(String userName) throws SQLException {
        User user = MarsDAO.userInfo(userName);
        loggedUser = user;
        System.out.println("Welcome, " + user.getName());
        System.out.println("---------------------------------- Your SuperHero: " + user.getHero());
        System.out.println("---------------------------------- Your Level: " + user.getLevel());
        placeInformation(user.getPlace());

    }

    // METODOS DE PLACES -------------------------------------------------------> M PLACES
    // Metodo que lista toda la información de un lugar en espesifico
    public static void placeInformation(String placeName) throws SQLException {
        Place place = MarsDAO.placeInfo(placeName);
        System.out.println("Place: " + place.getName());
        System.out.println(place.getDescription());
        System.out.println("---");
        showEnemiesInPlace(place.getName());
        System.out.println("---");
        showGemsInPlace(place.getName());
        System.out.println("---");
        System.out.println("You can go:");
        confirmRut(place.getNorth(), "N");
        confirmRut(place.getSouth(), "S");
        confirmRut(place.getEast(), "E");
        confirmRut(place.getWest(), "W");
        System.out.println("");
    }

    // Metodo que lista todo los lugares del juego
    public static void showPlaces() throws SQLException {
        System.out.println("=====================");
        System.out.println("     PLACES LIST    ");
        System.out.println("=====================\n");

        for (Place p : MarsDAO.selectPlaceList()) {
            System.out.println(p.toString());
            System.out.println("");
        }
    }

    // METODOS DE GEMAS -------------------------------------------------------> M GEMAS
    // Metodo que lista las gemas que estan en el mismo place en el que se encuentra el usuario
    public static void showGemsInPlace(String placeName) throws SQLException {
        int gemCounter = 0;
        for (Gem gem : MarsDAO.selectGemList()) {
            if (gem.getPlace().equalsIgnoreCase(placeName)) {
                System.out.println(gem.getName() + " owner: " + gem.getOwner());
                gemCounter++;
            }
        }

        if (gemCounter <= 0) {
            System.out.println("There are no gems here");
        }
    }

    // Metodo que lista todas las gemas del juego
    public static void showGemGame() throws SQLException {
        for (Gem gem : MarsDAO.selectGemList()) {
            System.out.println(gem.toString());

        }
    }

    // Metodo que te muestra que gema esta libre en el lugar donde estas
    public static Gem freeGem() throws SQLException {
        Gem freeGem = new Gem();
        for (Gem gem : MarsDAO.selectGemList()) {
            if (gem.getPlace().equalsIgnoreCase(loggedUser.getPlace())) {
                if(gem.getOwner() == null){
                    freeGem = gem;
                } else {
                    freeGem.setName("");
                }
            }
        }
        
        return freeGem;
    }

    // METODOS DE ENEMIGOS -------------------------------------------------------> M ENEMIGOS
    // Metodo que lista todos los enemigos en la posicion del usuario
    public static void showEnemiesInPlace(String placeName) throws SQLException {
        int gemCounter = 0;
        for (Enemie enemie : MarsDAO.selectEnemyList()) {
            if (enemie.getPlace().equalsIgnoreCase(placeName)) {
                System.out.println(enemie.toString());
                gemCounter++;
            }
        }

        if (gemCounter <= 0) {
            System.out.println("There is nobody here");
        }
    }

    // Metodo que lista todas los enemigos del juego
    public static void showEnemies() throws SQLException {
        for (Enemie enemie : MarsDAO.selectEnemyList()) {
            System.out.println(enemie.toString());
        }
    }

    // FILTROS DEL JUEGO  -------------------------------------------------------> JUEGO
    // Metodo que mira las gemas libres en la posicion en la que se encuentra  el usuario, en caso de estar libre, el uuario logeado sera el propietario
    public static void takeGem(String name, String gemName) throws SQLException {
        Gem gem = freeGem();
        if(gem.getName().equalsIgnoreCase(name +" "+gemName)){
            System.out.println("You have got the gem !!");
            MarsDAO.changeGemOwner(loggedUser.getName(), name +" "+gemName);
        } else if(gem.getName().equalsIgnoreCase("")){
            System.out.println("[ isn´t Gems avaible here ]");
        } else {
            System.out.println("[ Here there is no gem with that name ]");
        }

    }
    
    // Metodo que muetra las rutas por pantalla
    public static void confirmRut(String rutPlace, String rut) {
        if (rutPlace != null) {
            System.out.print(rut + ": " + rutPlace + " ");
        }
    }
    
    // Metodo que filtra las rutas segun la posicion del usuario, y filtra en que direcciones es posible ir.
    public static boolean validateRutOption(String rut) throws SQLException {
        Place vRutplace = MarsDAO.placeInfo(loggedUser.getPlace());

        switch (rut.toUpperCase()) {
            case "N":
                if (vRutplace.getNorth() != null) {
                    move(rut, vRutplace.getNorth());
                    return true;
                } else {
                    System.out.println("[ You can't move in that direction ]");
                }
                break;
            case "S":
                if (vRutplace.getSouth() != null) {
                    move(rut, vRutplace.getSouth());
                    return true;
                } else {
                    System.out.println("[ You can't move in that direction ]");
                }
                break;
            case "E":
                if (vRutplace.getEast() != null) {
                    move(rut, vRutplace.getEast());
                    return true;
                } else {
                    System.out.println("[ You can't move in that direction ]");
                }
                break;
            case "W":
                if (vRutplace.getWest() != null) {
                    move(rut, vRutplace.getWest());
                    return true;
                } else {
                    System.out.println("[ You can't move in that direction ]");
                }
                break;
            default:
                System.out.println("[ " + rut + " Is not a correct direction!! ]");
                break;
        }

        return false;
    }
    
    // Metodo que le permiete al usuario logeado moverse y obtener información del lugar actual en el que se encuentra.
    public static void move(String option, String placeName) throws SQLException {

        System.out.println("Moving to " + option + "...");
        loggedUser.setPlace(placeName);
        placeInformation(loggedUser.getPlace());

    }
}
