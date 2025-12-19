/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.DAO;

/**
 *
 * @author cesar
 */
import java.sql.*;

public class CarteraDAO {
    private final Connection con;

    public CarteraDAO(Connection con) { this.con = con; }

    public double obtenerSaldo(int gamerId) throws SQLException {
        String sql = "SELECT saldo_actual FROM Perfil_Gamer WHERE gamer_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, gamerId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) throw new SQLException("Gamer no existe");
            return rs.getDouble(1);
        }
    }

    public long insertarTransaccion(int gamerId, double monto) throws SQLException {
        String sql = "INSERT INTO Cartera_Transaccion (gamer_id, monto, tipo_movimiento) VALUES (?, ?, 'RECARGA')";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, gamerId);
            ps.setDouble(2, monto);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
            throw new SQLException("No se pudo crear transacción");
        }
    }

    public void sumarSaldo(int gamerId, double monto) throws SQLException {
        String sql = "UPDATE Perfil_Gamer SET saldo_actual = saldo_actual + ? WHERE gamer_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, monto);
            ps.setInt(2, gamerId);
            ps.executeUpdate();
        }
    }
}
