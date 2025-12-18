/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.service;

import com.mycompany.ipc2_ev_diciembre.Conexion.ConexionDB;
import com.mycompany.ipc2_ev_diciembre.DAO.PerfilGamerDAO;
import com.mycompany.ipc2_ev_diciembre.DAO.UsuarioAuthDAO;
import com.mycompany.ipc2_ev_diciembre.dto.RegistroGamerDTO;

import java.sql.Connection;

public class RegistroGamerService {

    public void registrar(RegistroGamerDTO dto) throws Exception {

        Connection con = ConexionDB.getInstance().getConnection();

        try {
            con.setAutoCommit(false);

            UsuarioAuthDAO authDAO = new UsuarioAuthDAO(con);
            PerfilGamerDAO perfilDAO = new PerfilGamerDAO(con);

            // Validaciones
            if (authDAO.existeEmail(dto.getEmail())) {
                throw new Exception("Email ya registrado");
            }

            if (perfilDAO.existeNickname(dto.getNickname())) {
                throw new Exception("Nickname ya registrado");
            }

            int userId = authDAO.crearUsuario(
                    dto.getEmail(),
                    dto.getPassword()
            );

            perfilDAO.crearPerfil(
                    userId,
                    dto.getNickname(),
                    dto.getFechaNacimiento()
            );

            con.commit();

        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }
}


