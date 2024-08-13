package ar.edu.unq.spring.modelo.exception;

public class NombreDePersonajeRepetido extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NombreDePersonajeRepetido(String message) {
        super("El nombre de personaje [" + message + "] ya esta siendo utilizado y no puede volver a crearse");
    }
}