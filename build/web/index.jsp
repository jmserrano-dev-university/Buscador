<%-- 
    Document   : index
    Created on : 18-may-2012, 16:17:40
    Author     : José Manuel Serano Mármol
    Author     : Raul Salazar de Torres
--%>
<%@page import="Servlets.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="Estilo/style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Búscador</title>
        
        <!-- Funciones JavaScript -->
        <script>
            function panelAdministracion(){
                document.location.href= "administracion.html";
            }
            
            function captarIdioma(){
                document.getElementById("idioma").value = document.getElementById("seleccionarIdioma").selectedIndex;
                //alert(document.getElementById("idioma").value);
            }
        </script>
        
    </head>
    <body>
        <%
            //Ponemos la sesion a null
            HttpSession sesion= request.getSession();
            sesion.removeAttribute("acceso");
        %>
        <div id="contenedorGlobal">
            <div id ="barraAdministracion">
                <input type="button" value ="Administrar" onclick="panelAdministracion()">
                <span class="alineadoDerecha">
                    <select id ="seleccionarIdioma">
                        <option>Español</option>
                        <option>Inglés</option>
                    </select>
                </span>
            </div>
            
            <div id="contenedor">
                <img src="Recursos/logo.jpg">
                <form action="realizarBusqueda" method="get" name ="formulario">
                    <input type="text" name ="campoBusqueda" size="50"><br>
                    <input type="submit" value="Buscar" onclick="captarIdioma()">
                    <input type="hidden" name ="idioma" id ="idioma">
                </form>
            </div>
            <div id ="pie">
                <small><b>Creado por:</b></small><br>
                <small> Raul Salazar de Torres</small><br>
                <small> José Manuel Serrano Mármol</small>
            </div>
        </div>
    </body>
</html>
