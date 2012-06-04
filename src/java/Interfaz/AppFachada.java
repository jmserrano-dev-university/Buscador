/**
 * Clase AppFachada.java
 * @author José Manuel Serrano Mármol
 * @author Raul Salazar de Torres
 * Clase que sirve de fachada entre el modelo y la vista
 */
package Interfaz;

import Procesamiento.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;



public class AppFachada {
    //----------------- Atributos
    private List<Documento> listaDocumentos;
    private Analyzer analizador;
    private String ruta, rutaIndexador;
    
    //----------------- Métodos
    
    
    /**
     * Coonstructor
     */
    public AppFachada() {
        listaDocumentos = new ArrayList<Documento>();
    }
       
    /**
     * Realiza el procesamiento de los archivos - LUCENE
     * @throws IOException 
     */
    private void procesamiento() throws IOException{
           for(int i = 0; i < listaDocumentos.size(); i++){
                try {
                    TokenStream resultado = analizador.tokenStream("content", new FileReader(ruta + listaDocumentos.get(i).getNombreDocumento()));
                    
                    //Recorremos los token y los almacenamos
                    Token t;
                    String tokens = "";
                    while((t=resultado.next()) != null){
                        tokens = tokens + " "+ t.term();
                    }
                    listaDocumentos.get(i).setCuerpo(tokens);

                } catch (FileNotFoundException ex) {
                    System.out.println("Error al leer archivo en procesamiento");
                }
            }

            //Escribimos de nuevo los archivos - PREPROCESADOS
            if(ruta.equalsIgnoreCase(Rutas.RUTA_ARCHIVOS_PROCESADOS_SPANISH)){
                ParseadorHTML.escribirDocumentosDisco(listaDocumentos,true);
            }else{
                ParseadorHTML.escribirDocumentosDisco(listaDocumentos,false);
            }
            

            //Indexamos los archivos
            Indexer indexador = new Indexer(rutaIndexador, analizador);
            try {
                indexador.index(ruta);
                indexador.close();
            } catch (Exception ex) {
                System.out.println("ERROR indexando archivos");
            }
    }
    
    /**
     * Realiza el procesamiento de los archivos - RAPIDMINER
     * @throws IOException 
     */
    private void procesamientoRapidminer() throws IOException{
            //Preprocesamiento - Realizado en un script SH
            Runtime sistema = Runtime.getRuntime();
            Process proceso = sistema.exec(Rutas.RUTA_RAPIDMINER_SPANISH);
            
            
            //Parseamos el cuerpo del HTML
            BufferedReader buffer = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea = "";
            while((linea = buffer.readLine()) != null){
                System.out.println(linea);
            }
            
            //Indexamos los archivos
            Indexer indexador = new Indexer(rutaIndexador, analizador);
            try {
                indexador.index(ruta);
                indexador.close();
            } catch (Exception ex) {
                System.out.println("ERROR indexando archivos");
            }
    }
    
    /**
     * Procesar los archivos en español - LUCENE
     */
    public void procesarArchivosEspanol() throws IOException{
         listaDocumentos = ParseadorHTML.ejecutarParserHTML(Rutas.RUTA_ARCHIVOS_ORIGINALES_SPANISH);
         analizador = new SpanishAnalyzer();
         ParseadorHTML.escribirDocumentosDisco(listaDocumentos,true);
         ruta = Rutas.RUTA_ARCHIVOS_PROCESADOS_SPANISH;
         rutaIndexador = Rutas.RUTA_INDEXADOR_SPANISH;
         procesamiento();
    }
    
    public void procesarArchivosEspanolRapid() throws IOException{
         analizador = new SpanishAnalyzer();
         ruta = Rutas.RUTA_ARCHIVOS_PROCESADOS_SPANISH;
         rutaIndexador = Rutas.RUTA_INDEXADOR_SPANISH;
         procesamientoRapidminer();
    }
    
    /**
     * Procesar los archivos en inglés - LUCENE
     */
    public void procesarArchivosIngles() throws IOException{
        listaDocumentos = ParseadorHTML.ejecutarParserHTML(Rutas.RUTA_ARCHIVOS_ORIGINALES_ENGLISH);
        analizador = new StandardAnalyzer();
        ParseadorHTML.escribirDocumentosDisco(listaDocumentos,false);
        ruta = Rutas.RUTA_ARCHIVOS_PROCESADOS_ENGLISH;
        rutaIndexador = Rutas.RUTA_INDEXADOR_ENGLISH;
        procesamiento();
    }
    
    
    public void procesarArchivosInglesRapid() throws IOException{
        analizador = new StandardAnalyzer();
        ruta = Rutas.RUTA_ARCHIVOS_PROCESADOS_ENGLISH;
        rutaIndexador = Rutas.RUTA_INDEXADOR_ENGLISH;
        procesamientoRapidminer();
    }
    
    /**
     * Realizamos una consulta en Español
     */
    public List<String> consultaEspanol(String consulta){
        try {
            analizador = new SpanishAnalyzer();
        } catch (IOException ex) {
            System.out.println("STOP WORDS FALLA...");
        }
         ruta = Rutas.RUTA_ARCHIVOS_PROCESADOS_SPANISH;
         rutaIndexador = Rutas.RUTA_INDEXADOR_SPANISH;
        try {
            //return Searcher.search(rutaIndexador, consulta,analizador);            
            return Searcher.searchResaltado(rutaIndexador,analizador, consulta);
        } catch (Exception ex) {
            System.out.println("ERROR al realizar la búsqueda en Español");
            return null;
        }
    }
    
    /**
     * Realizamos una consulta en Inglés
     */
    public List<String> consultaIngles(String consulta){
        analizador = new StandardAnalyzer();
        ruta = Rutas.RUTA_ARCHIVOS_PROCESADOS_ENGLISH;
        rutaIndexador = Rutas.RUTA_INDEXADOR_ENGLISH;
        try {
            return Searcher.searchResaltado(rutaIndexador,analizador, consulta);
            //return Searcher.search(rutaIndexador, consulta, analizador);
        } catch (Exception ex) {
            System.out.println("ERROR al realizar la búsqueda en inglés");
            return null;
        }
    }
}
    
   