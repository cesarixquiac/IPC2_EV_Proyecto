/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.ipc2_ev_diciembre.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar
 */
@WebServlet(name = "AdminCategoriaServlet", urlPatterns = {"/api/admin/categorias"})
public class AdminCategoriaServlet extends HttpServlet {

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
            out.println("<title>Servlet AdminCategoriaServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminCategoriaServlet at " + request.getContextPath() + "</h1>");
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
        com.mycompany.ipc2_ev_diciembre.util.SessionUtil.requireRole(req, "ADMIN");

        Map<String, String> body =
            new com.google.gson.Gson().fromJson(req.getReader(), Map.class);

        String nombre = body == null ? null : body.get("nombre");

        new com.mycompany.ipc2_ev_diciembre.Service.CategoriaService().crear(nombre);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("{\"mensaje\":\"Categoría creada\"}");

    } catch (Exception e) {
        resp.setStatus(e.getMessage().contains("aut") ? 401 : 400);
        resp.getWriter().write(
            "{\"error\":\"" + e.getMessage().replace("\"","'") + "\"}"
        );
    }
}

        
        @Override protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try {
            com.mycompany.ipc2_ev_diciembre.util.SessionUtil.requireRole(req, "ADMIN");
            int id = Integer.parseInt(req.getParameter("id"));
            new com.mycompany.ipc2_ev_diciembre.Service.CategoriaService().eliminar(id);
            resp.getWriter().write("{\"mensaje\":\"Categoría eliminada\"}");
        } catch (Exception e) {
            resp.setStatus(e.getMessage().contains("aut") ? 401 : 400);
            resp.getWriter().write("{\"error\":\"" + e.getMessage().replace("\"","'") + "\"}");
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
