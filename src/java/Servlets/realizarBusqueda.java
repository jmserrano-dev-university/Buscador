/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Interfaz.AppFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author serrano
 */
@WebServlet(name = "realizarBusqueda", urlPatterns = {"/realizarBusqueda"})
public class realizarBusqueda extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet realizarBusqueda</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Resultados de la búsqueda:</h1>");
            
            String consulta = request.getParameter("campoBusqueda");
            String idioma = request.getParameter("idioma");
            
            out.println("La consulta es: " + consulta + "<br>");
            
            
            //Realizamos la consulta
            AppFachada app = new AppFachada();
            
            if(idioma.equalsIgnoreCase("0")){ //Español
                idioma = "español";
                out.println("El idioma es: " + idioma);
                List <String> resultados = app.consultaEspanol(consulta);
                if(resultados != null){
                    out.println("<h2>" + resultados.get(0) + "</h2>");
                    for(int i = 1; i < resultados.size(); i++){
                        out.println("<a href="+resultados.get(i) +">"+ "Web " + i +"</a><br>");
                    }
                }else{
                    out.println("<h3>No se han encontrado resultados para la búsqueda</h3>");
                }
                
            }else{ //Inglés
                idioma = "inglés";
                out.println("El idioma es: " + idioma);
                List<String> resultados = app.consultaIngles(consulta);
                if(resultados != null){
                    out.println("<h2>" + resultados.get(0) + "</h2>");
                    for(int i = 1; i < resultados.size(); i++){
                        out.println("<a href="+resultados.get(i) +">"+ "Web " + i +"</a><br>");
                    }
                }else{
                    out.println("<h3>No se han encontrado resultados para la búsqueda</h3>");
                }
            }            
            
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
