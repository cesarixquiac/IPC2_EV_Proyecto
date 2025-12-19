/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.dto;

/**
 *
 * @author cesar
 */


public class LoginResponseDTO {
    private int userId;
    private String email;
    private String rol;


    private Integer gamerId;
    private String nickname;

    public LoginResponseDTO(int userId, String email, String rol, Integer gamerId, String nickname) {
        this.userId = userId;
        this.email = email;
        this.rol = rol;
        this.gamerId = gamerId;
        this.nickname = nickname;
    }

    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public Integer getGamerId() { return gamerId; }
    public String getNickname() { return nickname; }
}
