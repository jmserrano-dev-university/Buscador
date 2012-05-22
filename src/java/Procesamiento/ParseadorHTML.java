/**
 * Clase ParseadorHTML.java
 * @author José Manuel Serrano Mármol
 * @author Raul Salazar de Torres
 */
package Procesamiento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParseadorHTML {
    
    public static List<Documento> ejecutarParserHTML(String rutaDirectorio) {
        List<Documento> listaDocumentos = new ArrayList<Documento>();
        File directorio = new File(rutaDirectorio);
        String [] ficheros = directorio.list();
       
        
        //Leemos los fichero que contiene el directorio
        Runtime sistema = Runtime.getRuntime();
        
        for(int i = 0; i < ficheros.length; i++){
            try {
                //Parseamos lo html
                Process proceso = sistema.exec("/home/serrano/NetBeansProjects/Buscador/htmlparser1_6/bin/stringextractor " + rutaDirectorio+"/"+ficheros[i]);
                
                //Parseamos el cuerpo del HTML
                BufferedReader buffer = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                String linea, cuerpo = "";
                while((linea = buffer.readLine()) != null){
                    cuerpo = cuerpo + linea + "\n";
                }
                
                //Parseamos el titulo del HTML
                String titulo;
                proceso = sistema.exec("java -jar /home/serrano/NetBeansProjects/Buscador/htmlparser1_6/lib/htmlparser.jar " + rutaDirectorio+"/"+ficheros[i] + " title");
                buffer = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                titulo = buffer.readLine();
                
                //Introducimos los documentos en la lista
                Documento doc = new Documento(titulo, cuerpo, ficheros[i]);
                listaDocumentos.add(doc);
                
            } catch (IOException ex) {
                System.out.println("Error al parsera el archivo " + ficheros[i]);
            }
        }
        return listaDocumentos;
    }
    
    public static void escribirDocumentosDisco(List<Documento> listaDocumento,boolean ficheroEnEspanol) throws IOException{
        String ruta = "";
        if(ficheroEnEspanol){
            ruta = Rutas.RUTA_ARCHIVOS_PROCESADOS_SPANISH;
        }else{
            ruta = Rutas.RUTA_ARCHIVOS_PROCESADOS_ENGLISH;
        }
        
        for(int i = 0; i < listaDocumento.size(); i++){
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta + listaDocumento.get(i).getNombreDocumento()));
            bw.write(listaDocumento.get(i).getCuerpo());
            bw.close();
        }
    }
}  