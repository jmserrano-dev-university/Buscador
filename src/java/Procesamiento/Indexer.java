/**
 * Clase Indexer.java
 * @author José Manuel Serrano Mármol
 * @author Raul Salazar de Torres
 * Clase que realiza la indexación de los archivos en los cuales se quiere realizar la búsqueda
 */

package Procesamiento;

import java.io.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;

public class Indexer {

	private IndexWriter writer;
        private Analyzer _analizador;
	
	/**
	 * Crea un nuevo objeto de tipo Indexer
	 * @param indexDir
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public Indexer(String indexDir) throws IOException {
		writer = new IndexWriter(new File(indexDir), new StandardAnalyzer(), true);		
	}
        
        public Indexer(String indexDir,Analyzer analizador) throws IOException {
                _analizador = analizador;
		writer = new IndexWriter(new File(indexDir),_analizador, true);		
	}
	
	/**
	 * Cierra el índice creado
	 * @throws IOException
	 */
	public void close() throws IOException {
		writer.close();
	}
	
	/**
	 * Lanza la indexación de un directorio completo
	 * @param dataDir
	 * @return número de ficheros indexados
	 * @throws Exception
	 */
	public int index(String dataDir) throws Exception {
		File[] files = new File(dataDir).listFiles();
		
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (!f.isDirectory() &&	!f.isHidden() && f.exists() &&	f.canRead()) {
				indexFile(f);
			}
		}
		return writer.numDocs(); //5
	}
	
	/**
	 * Devuelve un documento a partir de un fichero
	 * @param f
	 * @return Documento
	 * @throws Exception
	 */
	protected Document getDocument(File f) throws Exception {
		Document doc = new Document();
                String titulo;
                System.out.println("Indexando... " +  f.getCanonicalPath());
                //Extraemos el título de los documentos
                Runtime sistema = Runtime.getRuntime();
                Process proceso = sistema.exec("java -jar /home/serrano/NetBeansProjects/Buscador/htmlparser1_6/lib/htmlparser.jar " + f.getCanonicalPath().replace("Procesados", "Originales") + " title");
                BufferedReader buffer = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                titulo = buffer.readLine();
                titulo = titulo.substring(7);
                System.out.println("Titulo: " + titulo);
                
                //Guardamos el ducumento original
//                File original = new File(f.getCanonicalPath().replace("Procesados", "Originales"));
//                Scanner scan = new Scanner(original);
//                String contenidoOriginal = "";
//                while(scan.hasNext()){
//                    contenidoOriginal = contenidoOriginal + scan.nextLine();
//                }
                //proceso = sistema.exec("java -jar /home/serrano/NetBeansProjects/Buscador/htmlparser1_6/lib/htmlparser.jar " + f.getCanonicalPath().replace("Procesados", "Originales") + " p");
                proceso = sistema.exec("/home/serrano/NetBeansProjects/Buscador/htmlparser1_6/bin/stringextractor " + f.getCanonicalPath().replace("Procesados", "Originales"));
                buffer = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                String  linea, contenidoOriginal = "";
                while((linea = buffer.readLine()) != null){
                    contenidoOriginal = contenidoOriginal + linea + "\n";
                }
                
                
                doc.add(new Field("original",contenidoOriginal,Field.Store.COMPRESS,Field.Index.ANALYZED,Field.TermVector.WITH_POSITIONS_OFFSETS));
                doc.add(new Field("titulo", titulo, Field.Store.YES, Field.Index.NO));
		doc.add(new Field("contents", new FileReader(f)));
		doc.add(new Field("filename", f.getCanonicalPath().replace("Procesados", "Originales"), Field.Store.YES, Field.Index.NOT_ANALYZED));
		return doc;
	}
	
	/**
	 * Indexa un fichero
	 * @param f
	 * @throws Exception
	 */
	public void indexFile(File f) throws Exception {
		//generales.log("Indexando " + f.getCanonicalPath());
		Document doc = getDocument(f);
		if (doc != null) {
			writer.addDocument(doc);
		}
	}
	
	/**
	 * Indexa un fichero modificando su importancia
	 * @param f
	 * @param boost
	 * @throws Exception
	 */
	public void indexFileWithBoost(File f, float boost) throws Exception {
		//generales.log("Indexando " + f.getCanonicalPath());
		Document doc = getDocument(f);
		if (doc != null) {
			doc.setBoost(boost);
			writer.addDocument(doc);
		}
	}
	
	/**
	 * Optimiza el índice y espera o lanza en segundo plano
	 * @param wait
	 * @throws Exception
	 */
	public void optimizeIndex(boolean wait) throws Exception {
		writer.optimize(wait);
	}
	
	/**
	 * Muestra información de depuración del índice actual por pantalla
	 * @throws Exception
	 */
	public void debugIndex() throws Exception {
		writer.setInfoStream(System.out);
	}
	
	// **********************************************************
	// BORRADO
	/**
	 * Borra del índice un documento concreto por su id
	 * @param id
	 * @throws Exception
	 */
	public void deleteFromID(String id) throws Exception{
		writer.deleteDocuments(new Term("id", id));
		writer.optimize(); // Optimiza el índice tras el borrado
		writer.commit();		
	}
	
	/**
	 * Borra del índice un documento concreto por una query
	 * @param query
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void deleteFromQuery(String q) throws Exception{
		QueryParser parser = new QueryParser("contents", new StandardAnalyzer());
		Query query = parser.parse(q);
		writer.deleteDocuments(query);
		writer.optimize(); // Optimiza el índice tras el borrado
		writer.commit();		
	}
	// ***************************************************************
	// ACTUALIZACION
	/**
	 * Actualiza el índice con un nuevo doc y eliminando un ID
	 * @param id
	 * @param doc
	 */
	public void updateFromID(String id, Document doc) throws Exception{
		writer.updateDocument(new Term("id", id), doc);
		writer.commit();
	}
	
}