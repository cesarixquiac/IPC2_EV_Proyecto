/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.mycompany.ipc2_ev_diciembre.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.ipc2_ev_diciembre.Conexion.TypeAdapter.LocalDateAdapter;
import com.mycompany.ipc2_ev_diciembre.dto.RegistroGamerDTO;
import com.mycompany.ipc2_ev_diciembre.service.RegistroGamerService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import javax.servlet.ServletException;


/**
 *
 * @author cesar
 */
@WebServlet(name = "RegistroGamerServlet", urlPatterns = {"/api/gamer/registro"})
public class RegistroGamerServlet extends HttpServlet {

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
            out.println("<title>Servlet RegistroGamerServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistroGamerServlet at " + request.getContextPath() + "</h1>");
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
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

    resp.setContentType("application/json");

    try {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

        RegistroGamerService service = new RegistroGamerService();

        RegistroGamerDTO dto = gson.fromJson(req.getReader(), RegistroGamerDTO.class);
        service.registrar(dto);

        resp.getWriter().write("{\"mensaje\":\"Registro exitoso\"}");

    } catch (Exception e) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter()
            .write("{\"error\":\"" + e.getMessage() + "\"}");
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
