/**
 * Clase Searcher.java
 * @author José Manuel Serrano Mármol
 * @author Raul Salazar de Torres
 * Clase que realiza la búsqueda y nos dá los resultados
 */

package Procesamiento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CachingTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
	
	private static IndexSearcher searcher;
	
	/**
	 * Lanza una búsqueda sobre un índice creado
	 * @param indexDir
	 * @param q
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void search(String indexDir, String q) throws Exception {
		FSDirectory directory = FSDirectory.getDirectory(new File(indexDir));
		searcher = new IndexSearcher(directory);
		QueryParser parser = new QueryParser("contents", new StandardAnalyzer());
		Query query = parser.parse(q);
		
		long start = System.currentTimeMillis();
		TopDocs hits = searcher.search(query, 10);
		
		long end = System.currentTimeMillis();
                
		System.out.println("Encontrados " + hits.totalHits + " documentos (en " + 
				(end - start) + " milisegundos) que son relevantes para la consulta '" +	q + "':");
		
		for(int i=0;i<hits.scoreDocs.length;i++) {
			ScoreDoc scoreDoc = hits.scoreDocs[i];
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("filename"));
		}
		searcher.close();
	} 
     
        
        public static List<String> search(String indexDir, String q, Analyzer analizador) throws Exception {
		FSDirectory directory = FSDirectory.getDirectory(new File(indexDir));
		searcher = new IndexSearcher(directory);
		QueryParser parser = new QueryParser("contents", analizador);
		Query query = parser.parse(q);
		
		long start = System.currentTimeMillis();
		TopDocs hits = searcher.search(query, 10);
		
		long end = System.currentTimeMillis();
                
                List<String> resultados = new ArrayList<String>();
                
		System.out.println("Encontrados " + hits.totalHits + " documentos (en " + 
				(end - start) + " milisegundos) que son relevantes para la consulta '" +	q + "':");
                
                resultados.add("Encontrados " + hits.totalHits + " documentos (en " + 
				(end - start) + " milisegundos) que son relevantes para la consulta '" +	q + "':");
		
		for(int i=0;i<hits.scoreDocs.length;i++) {
			ScoreDoc scoreDoc = hits.scoreDocs[i];
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("titulo"));
                        resultados.add(doc.get("filename"));
                        resultados.add(Float.toString(scoreDoc.score));
		}
		searcher.close();
                
                return resultados;
	}
        
        
        /**
         * MEtodo que nos devuelven fragmentos del fichero origianl en el eque se realiza la búsqueda
         * @param analizador Anaizador usado
         * @param query Consulta que se desea realizar
         * @param fieldName Etiqueta de la información a recuperar anteriormente indexada
         * @param fieldContents 
         * @param fragmentNumber Número de fragmentos a recuperar
         * @param fragmentSize Tamaño de los fragmentos
         * @return Los fragmentos con las palabras resaltadas
         * @throws IOException 
         */
        private static String[] getFragmentsWithHighlightedTerms(Analyzer analizador, Query query, String fieldName,String fieldContents,int fragmentNumber,int fragmentSize) throws IOException{
            TokenStream stream = TokenSources.getTokenStream(fieldName, fieldContents, analizador);
            SpanScorer scorer = new SpanScorer(query,fieldName,new CachingTokenFilter(stream));
            Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, fragmentSize);
            
            Highlighter highlighter = new Highlighter(scorer);
            highlighter.setTextFragmenter(fragmenter);
            highlighter.setMaxDocCharsToAnalyze(Integer.MAX_VALUE);
            
            String[] fragments = highlighter.getBestFragments(stream, fieldContents, fragmentNumber);
            
            return fragments;
        }
        
        /**
         * Relaiza una búsqueda devolviendo los resultados indexados
         * @param indexDir Directorio de indexación
         * @param analizador Analizador usado
         * @param consulta Consulta que se desea realizar
         * @return Devuelve una lista con todos los resultados de la búsqueda
         */
        public static List<String> searchResaltado(String indexDir,Analyzer analizador, String consulta){
            try {
                FSDirectory directory = FSDirectory.getDirectory(new File(indexDir));
		searcher = new IndexSearcher(directory);
                
                QueryParser qp = new QueryParser("original",analizador);
                Query query = qp.parse(consulta);
                
                long start = System.currentTimeMillis();
		TopDocs hits = searcher.search(query, 10);
		long end = System.currentTimeMillis();
                
                List<String> resultados = new ArrayList<String>(); //Lista para devolver los resultados de la búsqueda
                
                resultados.add("Encontrados " + hits.totalHits + " documentos (en " + 
				(end - start) + " milisegundos) que son relevantes para la consulta '" +	consulta + "':");
                
                for(int i = 0; i < hits.scoreDocs.length; i++){
                    ScoreDoc scoreDoc = hits.scoreDocs[i];
                    Document doc = searcher.doc(scoreDoc.doc);
                    
                    String filename = doc.get("titulo"); //Titulo del documento
                    String original = doc.get("original"); //Documento original
                    String rutaArchivo = doc.get("filename"); //Ruta del archivo
                    
                    String relevancia = Float.toString(scoreDoc.score); //Puntuación otorgada al documento en la búsqueda
                    
                    String[] fragmentos = getFragmentsWithHighlightedTerms(analizador,query,"original",original,5,100);
                    
                    //Guadamos los resultados para devolverlos
                    resultados.add(rutaArchivo);
                    resultados.add(filename);
                    resultados.add(relevancia);
                    
                    for(int j = 0; j < fragmentos.length; j++){
                        resultados.add(fragmentos[j]);
                    }
                    
                    resultados.add("ComienzoResultado"); //Indica cuando comienza un resultado nuevo de la búsqueda
                    //Fin en el guardado de datos
                }
                searcher.close();
                return resultados;
            } catch (IOException ex) {
                System.out.println("Error en la búsqueda 1");
                return null;
            } catch (ParseException ex) {
                System.out.println("Error en la búsqueda 2");
                return null;
            }
        }
        
        
	/**
	 * Muestra valores de los resultados obtenidos
	 * @param topDocs
	 * @param query
	 * @throws Exception 
	 */
	public static void explainResults(TopDocs topDocs, Query query) throws Exception{
		for (int i = 0; i < topDocs.totalHits; i++) {
			ScoreDoc match = topDocs.scoreDocs[i];
			Explanation explanation = searcher.explain(query, match.doc);
			System.out.println("----------");
			Document doc = searcher.doc(match.doc);
			System.out.println(doc.get("title"));
			System.out.println(explanation.toString());
		}
	}
}
