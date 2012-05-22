/**
 * Clase Documento.java
 * @author José Manuel Serrano Mármol
 * @author Raul Salazar de Torres
 */
package Procesamiento;

public class Documento {
    private String _titulo;
    private String _cuerpo;
    private String _nombreDocumento;

    public Documento(String titulo, String cuerpo, String nombre) {
        _titulo = titulo;
        _cuerpo = cuerpo;
        _nombreDocumento = nombre;
    }
    
    public String getTitulo(){
        return _titulo;
    }
    
    public String getCuerpo(){
        return _cuerpo;
    }
    
    public void setCuerpo(String cuerpo){
        _cuerpo = cuerpo;
    }
    
    public String getNombreDocumento(){
        return _nombreDocumento;
    }
    
    public void out(){
        System.out.println("Titulo:" + _titulo);
        System.out.println("Nombre: " + _nombreDocumento + "\n");
    }
}
