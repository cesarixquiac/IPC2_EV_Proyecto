/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.util;

/**
 *
 * @author cesar
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static int requireUserId(HttpServletRequest req) throws Exception {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("USER_ID") == null) throw new Exception("No autenticado");
        return (Integer) s.getAttribute("USER_ID");
    }

    public static String requireRol(HttpServletRequest req) throws Exception {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("ROL") == null) throw new Exception("No autenticado");
        return (String) s.getAttribute("ROL");
    }

    public static void requireRole(HttpServletRequest req, String role) throws Exception {
        String rol = requireRol(req);
        if (!role.equals(rol)) throw new Exception("No autorizado");
    }
}

