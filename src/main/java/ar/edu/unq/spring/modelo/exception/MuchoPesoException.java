package ar.edu.unq.spring.modelo.exception;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.Item;

public class MuchoPesoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Personaje personaje;
    private final Item item;

    public MuchoPesoException(Personaje personaje, Item item) {
        this.personaje = personaje;
        this.item = item;
    }

    @Override
    public String getMessage() {
        return "El personaje [" + personaje + "] no puede recoger [" + item + "] porque carga mucho peso ya";
    }
}
