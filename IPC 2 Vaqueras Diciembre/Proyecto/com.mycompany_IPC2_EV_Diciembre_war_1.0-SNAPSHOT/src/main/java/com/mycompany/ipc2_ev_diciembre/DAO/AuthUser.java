/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.DAO;

/**
 *
 * @author cesar
 */


public class AuthUser {
    private int userId;
    private String email;
    private String rol;

    public AuthUser(int userId, String email, String rol) {
        this.userId = userId;
        this.email = email;
        this.rol = rol;
    }

    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
}

