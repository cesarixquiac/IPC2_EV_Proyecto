/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.Service;

/**
 *
 * @author cesar
 */
import com.mycompany.ipc2_ev_diciembre.Conexion.ConexionDB;
import com.mycompany.ipc2_ev_diciembre.DAO.CarteraDAO;

import java.sql.Connection;

public class CarteraService {

    public double recargar(int gamerId, double monto) throws Exception {
        if (monto <= 0) throw new Exception("Monto debe ser > 0");

        Connection con = ConexionDB.getInstance().getConnection();
        try {
            con.setAutoCommit(false);

            CarteraDAO dao = new CarteraDAO(con);
            dao.insertarTransaccion(gamerId, monto);
            dao.sumarSaldo(gamerId, monto);

            double nuevoSaldo = dao.obtenerSaldo(gamerId);

            con.commit();
            return nuevoSaldo;
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }

    public double saldo(int gamerId) throws Exception {
        Connection con = ConexionDB.getInstance().getConnection();
        try {
            return new CarteraDAO(con).obtenerSaldo(gamerId);
        } finally {
            con.close();
        }
    }
}
