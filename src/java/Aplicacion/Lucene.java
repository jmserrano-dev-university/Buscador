/**
 * Proyecto de LUCENE
 * @author José Manuel Serrano Mármol
 * @author Raul Salazar de Torres
 */

package Aplicacion;

import Interfaz.AppFachada;
import java.io.IOException;

public class Lucene {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        AppFachada interfaz = new AppFachada();
        interfaz.ejecutar();
    }
}
