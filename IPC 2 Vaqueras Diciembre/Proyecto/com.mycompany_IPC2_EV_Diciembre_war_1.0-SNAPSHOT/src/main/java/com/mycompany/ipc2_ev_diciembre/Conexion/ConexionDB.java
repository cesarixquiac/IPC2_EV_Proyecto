/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cesar
 */
public class ConexionDB {

    private static final String IP = "localhost";
    private static final int PUERTO = 3310;
    private static final String SCHEMA = "gamestoredb"; 
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";

    private static final String URL =
            "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA +
            "?useSSL=false&serverTimezone=UTC";

    private static ConexionDB instance;

    private ConexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver MySQL", e);
        }
    }

    public static ConexionDB getInstance() {
        if (instance == null) {
            instance = new ConexionDB();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }
}
