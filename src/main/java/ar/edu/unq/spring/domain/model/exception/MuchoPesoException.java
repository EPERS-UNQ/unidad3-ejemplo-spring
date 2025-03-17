package ar.edu.unq.spring.domain.model.exception;

import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.Personaje;

public class MuchoPesoException extends RuntimeException {
    
    private final Personaje personaje;
    private final Item item;
    
    public MuchoPesoException(Personaje personaje, Item item) {
        super("El personaje " + personaje.getNombre() + " no puede cargar el item " + 
              item.getNombre() + " porque excede su peso máximo. " +
              "Peso actual: " + personaje.getPesoActual() + 
              ", Peso del item: " + item.getPeso() + 
              ", Peso máximo: " + personaje.getPesoMaximo());
        this.personaje = personaje;
        this.item = item;
    }
    
    public Personaje getPersonaje() {
        return personaje;
    }
    
    public Item getItem() {
        return item;
    }
} 