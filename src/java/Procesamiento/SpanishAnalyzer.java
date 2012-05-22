/**
 * Clase SpanishAnalyzer.java
 * @author José Manuel Serrano Mármol
 * @author Raul Salazar de Torres
 */
package Procesamiento;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class SpanishAnalyzer extends Analyzer{
    //*************** ATRIBUTOS
    private List<String> SPANISH_STOP_WORDS = new ArrayList<String>();
    
    //Esta tabla Hash contiene las palabras vacías que se aplicarán con StopFilter.
    private Set<Object> stopTable = new HashSet<Object>();
    
    //Esta tabla hash contiene las palabras sobre las que NO queremos que se aplique el stemmer
    private Set<Object> exclTable = new HashSet<Object>();

    //*************** METODOS
    
    /**
     * Constructor
     * @param ruta Ruta donde se encuentra el fichero de stop words
     */
    public SpanishAnalyzer(String ruta) {
        //Leemos el fichero de stop words
        File fichero = new File(ruta);
        try {
            Scanner scanner = new Scanner(fichero);
            while(scanner.hasNext()){
                SPANISH_STOP_WORDS.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error al leer el archivo spanishST.txt");
        }
        
        String [] cadenas = new String[SPANISH_STOP_WORDS.size()];
        for(int i = 0; i < SPANISH_STOP_WORDS.size(); i++){
            cadenas[i] = SPANISH_STOP_WORDS.get(i);
        }
        
        stopTable = StopFilter.makeStopSet(cadenas);
    }
    
    public SpanishAnalyzer(){
        this(Rutas.RUTA_STOP_WORDS_SPANISH);
    }

    /** Creo el analizador */
    @Override
    public final TokenStream tokenStream(String fieldName, Reader reader) {
        TokenStream result = new StandardTokenizer(reader);
        result = new StandardFilter(result);
        result = new LowerCaseFilter(result);
        result = new StopFilter(result, stopTable);
        result = new SpanishStemFilter(result);
        return result;
    }
}

