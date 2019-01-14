/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import Model.Notifications;

/**
 *
 * @author isain
 */
public class UserExceptions extends Exception{
    
    private String errorMessage = "Notificaión de Usuarios";
    private Notifications notifications = new Notifications();
    
    public UserExceptions(String notification) {
        switch(notification) {
            case "NOT 001":
                System.out.println(this.notifications.getNotifications().get("NOT 001"));
                break;
            case "NOT 002":
                System.out.println(this.notifications.getNotifications().get("NOT 002"));
                break;
            case "NOt 003":
                System.out.println(this.notifications.getNotifications().get("NOT 003"));
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "Verificar Contraseña";
    }
    
    
    
}
