/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbo.Model;

/**
 *
 * @author JAGAD
 */

public class SessionUser {

    private static User user;

    public static void set(User u) {
        user = u;
    }

    public static User get() {
        return user;
    }

    public static void clear() {
        user = null;
    }
}

