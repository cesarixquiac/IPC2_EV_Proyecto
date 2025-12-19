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
import com.mycompany.ipc2_ev_diciembre.DAO.AuthUser;
import com.mycompany.ipc2_ev_diciembre.DAO.PerfilGamerDAO;
import com.mycompany.ipc2_ev_diciembre.DAO.UsuarioAuthDAO;
import com.mycompany.ipc2_ev_diciembre.dto.LoginResponseDTO;

import java.sql.Connection;

public class LoginService {

    public LoginResponseDTO login(String email, String password) throws Exception {

        if (email == null || email.isBlank()) throw new Exception("Email requerido");
        if (password == null || password.isBlank()) throw new Exception("Password requerido");

        Connection con = ConexionDB.getInstance().getConnection();
        try {
            UsuarioAuthDAO authDAO = new UsuarioAuthDAO(con);
            PerfilGamerDAO gamerDAO = new PerfilGamerDAO(con);

            AuthUser u = authDAO.findByEmailAndPassword(email, password);
            
            if (u == null) throw new Exception("Credenciales inválidas");

            Integer gamerId = null;
            String nickname = null;

            if ("GAMER".equals(u.getRol())) {
                gamerId = u.getUserId();
                nickname = gamerDAO.obtenerNickname(gamerId);
            }

            return new LoginResponseDTO(u.getUserId(), u.getEmail(), u.getRol(), gamerId, nickname);

        } finally {
            con.close();
        }
    }
}

