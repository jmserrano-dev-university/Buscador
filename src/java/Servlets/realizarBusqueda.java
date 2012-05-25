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
import javax.servlet.http.HttpSession;

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
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"./Estilo/style.css\">");
            out.println("<script type=\"text/javascript\" src=\"./Scripts/jquery-1.6.2.min.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"./Scripts/codigoAjaxjQuery.js\"></script>");
            out.println("</head>");
            out.println("<body>");
           
            //Sesión -- Comprobación para no repetir campo de búsqueda
            HttpSession sesion= request.getSession();
            Boolean acceso = (Boolean)sesion.getAttribute("acceso");
            
            //Recuperamos la consulta y el idioma en el cual se desea realizar
            String consulta = request.getParameter("campoBusqueda");
            String idioma = request.getParameter("idioma");
            
            
            //Realizamos la consulta
            AppFachada app = new AppFachada();
            
            //Imagen
            out.println("");
            
            //Formulario de búsqueda
            if(acceso == null){
                
                out.println("<form action=\"javascript:llamarAsincronoSolicitarBusqueda('realizarBusqueda','contenidoBusqueda')\" method=\"post\">"
                        + "<img src=\"./Recursos/logo.jpg\" width=\"200px\">"
                        + "<select name =\"idioma\" id=\"idioma\" style={float:right;}>"
                        +    "<option value=\"0\">Español</option>"
                        +    "<option value=\"1\">Inglés</option>"
                        +    "</select>"
                        +    "<br>"
                        +    "<input type=\"text\" name=\"campoBusqueda\" style={width:40%} id =\"campoBusqueda\">"
                        +    "<input type=\"submit\" value=\"Buscar\">"
                        + "</form>");
                sesion.setAttribute("acceso", true);
            }
            
            //Div que contiene la inforamción de la búsqueda
            out.println("<div id=\"contenidoBusqueda\">");
            
            if(idioma.equalsIgnoreCase("0")){ //Español
                List <String> resultados = app.consultaEspanol(consulta);
                if(resultados != null){
                    out.println("<h3>" + resultados.get(0) + " en Español</h3><br>");
                    
                    for(int i = 1; i < resultados.size(); i++){
                        
                        out.println("<table border=\"1\" CELLSPACING=\"0\">");
                        out.println("<tr>");
                        
                            out.println("<td style={width:90%;}>");
                            out.println("<h4> <a href=\" "+resultados.get(i++).replace("/home/serrano/NetBeansProjects/Buscador/Documentos/", "") +"\">" + resultados.get(i++) + "</a> </h4>");
                            out.println("</td>");

                            out.println("<td>");
                            out.println("<small>Relevancia: <font color=red>" + resultados.get(i++)+ "</font></small>");
                            out.println("</td>");
                        
                        out.println("</tr>");
                        
                        out.println("<tr>");
                            out.println("<td colspan=\"2\">");
                            while(!resultados.get(i).equalsIgnoreCase("ComienzoResultado")){
                                out.println("<small>" + resultados.get(i) + "</small><br>");
                                i++;
                            }
                            out.println("</td>");
                        out.println("</tr>");
                        
                        out.println("</table><br>");
                    }
                }else{
                    out.println("<h3>No se han encontrado resultados para la búsqueda</h3>");
                }
                
            }else{ //Inglés
                idioma = "inglés";
                List<String> resultados = app.consultaIngles(consulta);
                if(resultados != null){
                    out.println("<h3>" + resultados.get(0) + " en Inglés</h3><br>");
                    
                    for(int i = 1; i < resultados.size(); i++){
                        
                        out.println("<table border=\"1\" CELLSPACING=\"0\">");
                        out.println("<tr>");
                        
                            out.println("<td style={width:90%;}>");
                            out.println("<h4> <a href=\" "+resultados.get(i++).replace("/home/serrano/NetBeansProjects/Buscador/Documentos/", "") +"\">" + resultados.get(i++) + "</a> </h4>");
                            out.println("</td>");

                            out.println("<td>");
                            out.println("<small>Relevancia: <font color=red>" + resultados.get(i++)+ "</font></small>");
                            out.println("</td>");
                        
                        out.println("</tr>");
                        
                        out.println("<tr>");
                            out.println("<td colspan=\"2\">");
                            while(!resultados.get(i).equalsIgnoreCase("ComienzoResultado")){
                                out.println("<small>" + resultados.get(i) + "</small><br>");
                                i++;
                            }
                            out.println("</td>");
                        out.println("</tr>");
                        
                        out.println("</table><br>");
                    }
                }else{
                    out.println("<h3>No se han encontrado resultados para la búsqueda</h3>");
                }
            }            
            
            out.println("</div>");
            
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
