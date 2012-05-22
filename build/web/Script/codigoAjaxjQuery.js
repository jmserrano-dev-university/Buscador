/* 
    Document   : codigoAjaxJQuery.js
    Created on : 16 - January - 2012
    Author     : José Manuel Serrano Mármol
*/


/**
 * Envio de las páginas al pulsar en el menú
 */
function llamarAsincrono (p_url, id_contenedor) {
	$.ajax({
        url: p_url,
        async:true,
        dataType: "html",
        success: function(datos){
			var idc = '#' + id_contenedor;
			$(idc).html(datos);
        },
        type: "GET"
	});
}


/**
 * Envio de LOGIN
 * Metodo: POST
 */
function llamarAsincronoLogin (p_url, id_contenedor) {
	var nombre = $('#nombre').val();
        var pass = $('#pass').val();
        
        console.log($('form').serialize());
        $.post(p_url,
               {'nombreUsu' : nombre,'passUsu' : pass},
               function(data){
                   var idc = '#' + id_contenedor;
                   
                   if(data == "SI"){
                       $(idc).html("Correcto");
                       location.href="appPrincipal.jsp";
                   }else{
                       $(idc).html("Error: Usuario o clave invalida");
                   }
               }
        );
}


/**
 * Envio de formulario MENSAJE PUBLICO
 * Metodo: POST
 */
function llamarAsincronoForm (p_url, id_contenedor) {
	var mensaje = $('#areaMensajePublico').val();
        console.log($('form').serialize());
        $.post(p_url,
               {'areaMensajePublico' : mensaje},
               function(datos){
                   var idc = '#' + id_contenedor;
                   $(idc).html(datos);
               }
        );
}

/**
 * Envio de formulario MENSAJE PRIVADO
 * Metodo: POST
 */
function llamarAsincronoFormPrivado (p_url, id_contenedor) {
	var mensaje = $('#areaMensajePrivado').val();
        var amigo = $('#amigos').val();
        console.log($('form').serialize());
        $.post(p_url,
               {'areaMensajePrivado' : mensaje,'amigos' : amigo},
               function(datos){
                   var idc = '#' + id_contenedor;
                   $(idc).html(datos);
               }
        );
}


/**
 * Encadena los valores (email) de las solicitudes elegidas - Solicitud de amistad
 */
function encadenaSolicitudes(){
    //Comprobar que alguna solicitud está marcada
    
    formulario = document.getElementById("formulario");
    var bandera = 0;
    var cadena = ""
    
    for(var i=0; i<formulario.elements.length; i++) {
      var elemento = formulario.elements[i];
      if(elemento.type == "checkbox") {
        if(elemento.checked) {
          bandera = bandera + 1;
          if(bandera == 1){
              cadena = elemento.value;
          }else{
              cadena = cadena + "," + elemento.value;
          }
          
        }
      }
    }
    return cadena;
}



/**
 * Envio de formulario SOLICITUDES DE AMISTAD RECIBIDAS
 * Metodo: POST
 */
function llamarAsincronoSocilitudes (p_url, id_contenedor) {
	var solicitudes = encadenaSolicitudes();
        console.log($('form').serialize());
        $.post(p_url,
               {'solicitudes' : solicitudes},
               function(datos){
                   var idc = '#' + id_contenedor;
                   $(idc).html(datos);
               }
        );
}

/**
 * Envio de formulario Búsqueda de amigos
 * Metodo: POST
 */
function llamarAsincronoTerminoBusqueda (p_url, id_contenedor) {
	var terminoBusqueda = $('#buscador').val();
        
        console.log($('form').serialize());
        $.post(p_url,
               {'termino' : terminoBusqueda},
               function(datos){
                   var idc = '#' + id_contenedor;
                   $(idc).html(datos);
               }
        );
}

/**
 * Envio de formulario Enviar solicitud de amistad (Enviar)
 * Metodo: POST
 */
function llamarAsincronoSolicitarAmistad (p_url, id_contenedor) {
	var amigo = $('#coincidencias').val();
        
        console.log($('form').serialize());
        $.post(p_url,
               {'solicitudAmistad' : amigo},
               function(datos){
                   var idc = '#' + id_contenedor;
                   $(idc).html(datos);
               }
        );
}


/**
 * Envio de formulario Registro de usuario
 * Metodo: POST
 */
function llamarAsincronoRegistro (p_url, id_contenedor) {
	var rnombre = $('#rNombre').val();
        var remail = $('#rEmail').val();
        var rclave = $('#rClave').val();
        var rdescripcion = $('#rDescripcion').val();
        
        console.log($('form').serialize());
        $.post(p_url,
               {'nombre' : rnombre,'email' : remail,'clave' : rclave,'descripcion' : rdescripcion},
               function(data){
                   var idc = '#' + id_contenedor;
                   
                   if(data == "SI"){
                       $(idc).html("Registro correcto");
                   }
                   
                   if(data == "NO"){
                       $(idc).html("ERROR: Correo incorrecto");
                   }
                   
                   if(data == "OCUPADO"){
                       $(idc).html("ATENCIÓN: Usuario escogido");
                   }
               }
        );
}
