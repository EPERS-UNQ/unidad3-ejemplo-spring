package ar.edu.unq.spring.domain.model.exception;


public class NombreDePersonajeRepetidoException extends RuntimeException {
    
    private final String nombre;
    
    public NombreDePersonajeRepetidoException(String nombre) {
        super("Ya existe un personaje con el nombre: " + nombre);
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }
} 