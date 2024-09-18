package ar.edu.unq.spring.modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Item {

    private Long id;
    private String nombre;
    private int peso;
    private Personaje owner;

    public Item() {}

    public Item(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
