package ar.edu.unq.spring.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Entidad Item
 */
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Item {

    private ItemId id;
    private String nombre;
    private int peso;
    private Personaje dueño;

    public Item(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

    protected Item() {
    }

    public void setId(ItemId id) {
        this.id = id;
    }

    public void asignarDueño(Personaje dueño) {
        this.dueño = dueño;
    }
} 