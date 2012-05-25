/**
 * Clase SpanishStemFilter.java
 * @author José Manuel Serrano Mármol
 * @author Raul Salazar de Torres
 * Clase que realiza la extración de las raices de cada token
 */
package Procesamiento;

import java.io.IOException;
import net.sf.snowball.ext.SpanishStemmer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public final class SpanishStemFilter extends TokenFilter {
    private SpanishStemmer stemmer;
    private Token token = null;

    
    public SpanishStemFilter(TokenStream in) {
        super(in);
        stemmer = new SpanishStemmer(); // Aplica el stemmer para español de Snowball
    }
    
    /** Devuelve el siguiente Token tras aplicar el stemmer*/
    public final Token next() throws IOException {
        if ((token = input.next()) == null) {
            return null;
        }else{
            stemmer.setCurrent(token.termText());
            stemmer.stem();
            String s = stemmer.getCurrent();
            if ( !s.equals( token.termText() ) ) {
                return new Token( s, token.startOffset(),
                token.endOffset(), token.type() );
            }
            return token;
        }
    }

    

       
    /**
      * Si no se ha definido stemmer aplicamos este programado
      */
    public void setStemmer(SpanishStemmer stemmer) {
        if ( stemmer != null ) {
            this.stemmer = stemmer;
        }
    }
}
