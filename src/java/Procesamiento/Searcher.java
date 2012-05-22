package Procesamiento;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
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
	
	/**
	 * Lanza una búsqueda sobre un índice creado
	 * @param indexDir
	 * @param q
	 * @throws Exception
	 */
//	@SuppressWarnings("deprecation")
//	public static void search(String indexDir, String q, Analyzer analizador) throws Exception {
//		FSDirectory directory = FSDirectory.getDirectory(new File(indexDir));
//		searcher = new IndexSearcher(directory);
//		QueryParser parser = new QueryParser("contents", analizador);
//		Query query = parser.parse(q);
//		
//		long start = System.currentTimeMillis();
//		TopDocs hits = searcher.search(query, 10);
//		
//		long end = System.currentTimeMillis();
//                
//                
//		System.out.println("Encontrados " + hits.totalHits + " documentos (en " + 
//				(end - start) + " milisegundos) que son relevantes para la consulta '" +	q + "':");
//		
//		for(int i=0;i<hits.scoreDocs.length;i++) {
//			ScoreDoc scoreDoc = hits.scoreDocs[i];
//			Document doc = searcher.doc(scoreDoc.doc);
//			System.out.println(doc.get("filename"));
//		}
//		searcher.close();
//	}   
     
        
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
			System.out.println(doc.get("filename"));
                        resultados.add(doc.get("filename"));
		}
		searcher.close();
                
                return resultados;
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
