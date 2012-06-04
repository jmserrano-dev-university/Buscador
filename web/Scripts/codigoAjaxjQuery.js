/* 
    Document   : codigoAjaxJQuery.js
    Created on : 16 - January - 2012
    Author     : José Manuel Serrano Mármol
    Author     : Raul Salazar de Torres
*/

/**
 * Realiza una búsqueda
 * Metodo: POST
 */
function llamarAsincronoSolicitarBusqueda (p_url, id_contenedor) {
	var consulta = $('#campoBusqueda').val();
        var idioma = document.getElementById('idioma').selectedIndex;
        
        console.log($('form').serialize());
        $.post(p_url,
               {'campoBusqueda' : consulta,'idioma' : idioma},
               function(datos){
                   var idc = '#' + id_contenedor;
                   $(idc).html(datos);
               }
        );
}


/**
 * Procesamiento administración
 * Metodo: POST
 */
function llamarAsincronoAdmin (p_url, id_contenedor) {
	var opcion = document.getElementById('opcion').checked;
        var metodo = document.getElementById('metodo').checked;
        
        console.log($('form').serialize());
        
        document.getElementById("cargando").style.visibility="visible";
        
        //Hacemos visible el icono loading del procesamiento
        $('#cargando').ajaxStart(function() {
            $(this).show();
            document.getElementById("procesoIndex").innerHTML="";
            document.getElementById("boton").disabled=true;
        });
        
        $.post(p_url,
               {'opcion' : opcion,'metodo':metodo},
               function(data){
                   var idc = '#' + id_contenedor;
                   $(idc).html("Procesamiento Correcto");
               }
        );
        
        //Hacemos oculto el icono loading del procesamiento
        $('#cargando').ajaxStop(function() {
            $(this).hide();
            document.getElementById("boton").disabled=false;
        });  
                
            
}
