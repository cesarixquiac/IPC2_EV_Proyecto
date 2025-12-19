/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.DAO;

import java.sql.*;
import java.util.*;
/**
 *
 * @author cesar
 */
public class TiendaDAO {
    private final Connection con;
    public TiendaDAO(Connection con) { this.con = con; }

    public List<Map<String,Object>> listar(String titulo, Integer categoriaId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT v.juego_id, v.titulo, v.precio_base, v.estado_venta, e.nombre_comercial ");
        sql.append("FROM Videojuego v JOIN Empresa_Desarrolladora e ON e.empresa_id = v.empresa_id ");
        if (categoriaId != null) {
            sql.append("JOIN Juego_Categoria jc ON jc.juego_id = v.juego_id ");
        }
        sql.append("WHERE v.estado_venta='ACTIVO' ");

        List<Object> params = new ArrayList<>();
        if (titulo != null && !titulo.isBlank()) {
            sql.append("AND v.titulo LIKE ? ");
            params.add("%" + titulo.trim() + "%");
        }
        if (categoriaId != null) {
            sql.append("AND jc.categoria_id = ? ");
            params.add(categoriaId);
        }
        sql.append("ORDER BY v.juego_id DESC");

        try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i=0;i<params.size();i++) ps.setObject(i+1, params.get(i));
            ResultSet rs = ps.executeQuery();

            List<Map<String,Object>> out = new ArrayList<>();
            while (rs.next()) {
                Map<String,Object> m = new HashMap<>();
                m.put("juegoId", rs.getInt(1));
                m.put("titulo", rs.getString(2));
                m.put("precio", rs.getDouble(3));
                m.put("estado", rs.getString(4));
                m.put("empresa", rs.getString(5));
                out.add(m);
            }
            return out;
        }
    }
}