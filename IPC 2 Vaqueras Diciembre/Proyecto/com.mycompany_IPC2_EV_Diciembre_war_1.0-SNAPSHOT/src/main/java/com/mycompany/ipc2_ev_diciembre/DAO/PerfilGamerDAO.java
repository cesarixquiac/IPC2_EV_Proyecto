/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PerfilGamerDAO {

    private final Connection con;

    public PerfilGamerDAO(Connection con) {
        this.con = con;
    }

    public void crearPerfil(int gamerId, String nickname, LocalDate fechaNacimiento)
            throws SQLException {

        String sql = "INSERT INTO Perfil_Gamer (gamer_id, nickname, fecha_nacimiento) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, gamerId);
            ps.setString(2, nickname);
            ps.setDate(3, Date.valueOf(fechaNacimiento));
            ps.executeUpdate();
        }
    }

    public boolean existeNickname(String nickname) throws SQLException {
        String sql = "SELECT 1 FROM Perfil_Gamer WHERE nickname = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nickname);
            return ps.executeQuery().next();
        }
    }
    
    public String obtenerNickname(int gamerId) throws SQLException {
    String sql = "SELECT nickname FROM Perfil_Gamer WHERE gamer_id = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, gamerId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getString("nickname");
        return null;
    }
}
}

