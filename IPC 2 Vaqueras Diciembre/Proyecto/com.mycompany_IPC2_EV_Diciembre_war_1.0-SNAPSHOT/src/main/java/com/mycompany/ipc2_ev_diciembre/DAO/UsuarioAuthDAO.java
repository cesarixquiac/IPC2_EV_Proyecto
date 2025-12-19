/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.DAO;

import com.mycompany.ipc2_ev_diciembre.Conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author cesar
 */

import java.sql.*;

public class UsuarioAuthDAO {

    private final Connection con;

    public UsuarioAuthDAO(Connection con) {
        this.con = con;
    }

    public int crearUsuario(String email, String passwordHash) throws SQLException {

        String sql = "INSERT INTO Usuario_Auth (email, password_hash, rol) VALUES (?, ?, 'GAMER') ";

        try (PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, email);
            ps.setString(2, passwordHash);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("No se pudo obtener el ID del usuario");
        }
    }

    public boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM Usuario_Auth WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
    
    public AuthUser findByEmailAndPassword(String email, String password) throws SQLException {
     
    String sql = "SELECT user_id, email, rol, activo FROM Usuario_Auth WHERE email = ? AND password_hash = ?";
        System.out.println(email);
        System.out.println(password);

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;

        if (!rs.getBoolean("activo")) {
            throw new SQLException("Usuario inactivo");
        }

        return new AuthUser(
            rs.getInt("user_id"),
            rs.getString("email"),
            rs.getString("rol")
        );
    }
}
    
}

