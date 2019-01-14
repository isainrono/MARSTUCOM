/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashMap;

/**
 *
 * @author isain
 */
public class Notifications {

    private HashMap<String, String> notifications = new HashMap<String, String>();

    public Notifications() {

        this.notifications.put("NOT 001", "< NOT 001: Este usuario ya existe >");
        this.notifications.put("NOT 002", "< NOT 002: Contraseña incorrecta >");
        this.notifications.put("NOT 003", "< NOT 003: Numero de heroe incorrecto >");
        

    }

    public HashMap<String, String> getNotifications() {
        return notifications;
    }

    public void setNotifications(HashMap<String, String> notifications) {
        this.notifications = notifications;
    }
    
    
}
