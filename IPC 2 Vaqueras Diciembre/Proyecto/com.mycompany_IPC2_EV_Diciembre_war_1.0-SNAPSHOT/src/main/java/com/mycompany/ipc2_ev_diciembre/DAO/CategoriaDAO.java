/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author cesar
 */




public class CategoriaDAO {
    private final Connection con;
    public CategoriaDAO(Connection con) { this.con = con; }

    public List<String[]> listar() throws SQLException {
        String sql = "SELECT categoria_id, nombre FROM Categoria ORDER BY nombre";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<String[]> out = new ArrayList<>();
            while (rs.next()) out.add(new String[]{ String.valueOf(rs.getInt(1)), rs.getString(2) });
            return out;
        }
    }

    public void crear(String nombre) throws SQLException {
        String sql = "INSERT INTO Categoria (nombre) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Categoria WHERE categoria_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
