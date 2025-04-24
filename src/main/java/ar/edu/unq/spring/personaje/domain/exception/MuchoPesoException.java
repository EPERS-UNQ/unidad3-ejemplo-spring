package ar.edu.unq.spring.personaje.domain.exception;

import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.personaje.domain.model.Personaje;

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