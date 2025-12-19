/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.controller;

import com.google.gson.Gson;
import com.mycompany.ipc2_ev_diciembre.Service.LoginService;
import com.mycompany.ipc2_ev_diciembre.dto.LoginDTO;
import com.mycompany.ipc2_ev_diciembre.dto.LoginResponseDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cesar
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/api/auth/login"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json;charset=UTF-8");

    try {
        Gson gson = new Gson();
        LoginDTO dto = gson.fromJson(req.getReader(), LoginDTO.class);

        LoginService service = new LoginService();
        LoginResponseDTO result = service.login(dto.getEmail(), dto.getPassword());

        //Crear sesión y guardar datos ANTES de responder
        HttpSession session = req.getSession(true);
        session.setAttribute("USER_ID", result.getUserId());
        session.setAttribute("ROL", result.getRol());
        session.setAttribute("EMAIL", result.getEmail());
        session.setAttribute("GAMER_ID", result.getGamerId());
        session.setAttribute("NICKNAME", result.getNickname());
        session.setMaxInactiveInterval(30 * 60);

        System.out.println("LOGIN sessionId=" + session.getId());
        System.out.println("LOGIN USER_ID=" + session.getAttribute("USER_ID"));

        // responder
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(result));

    } catch (Exception e) {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().write("{\"error\":\"" + e.getMessage().replace("\"", "'") + "\"}");
    }
}


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
