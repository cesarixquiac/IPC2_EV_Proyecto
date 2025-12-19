/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.Service;
import com.mycompany.ipc2_ev_diciembre.Conexion.ConexionDB;
import com.mycompany.ipc2_ev_diciembre.DAO.CategoriaDAO;

import java.sql.Connection;
import java.util.List;


/**
 *
 * @author cesar
 */



public class CategoriaService {

    public List<String[]> listar() throws Exception {
        Connection con = ConexionDB.getInstance().getConnection();
        try { return new CategoriaDAO(con).listar(); }
        finally { con.close(); }
    }

    public void crear(String nombre) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) throw new Exception("Nombre requerido");
        Connection con = ConexionDB.getInstance().getConnection();
        try { new CategoriaDAO(con).crear(nombre.trim()); }
        finally { con.close(); }
    }

    public void eliminar(int id) throws Exception {
        Connection con = ConexionDB.getInstance().getConnection();
        try { new CategoriaDAO(con).eliminar(id); }
        finally { con.close(); }
    }
}
