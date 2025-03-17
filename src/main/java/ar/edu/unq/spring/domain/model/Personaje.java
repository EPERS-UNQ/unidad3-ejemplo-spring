package ar.edu.unq.spring.domain.model;

import ar.edu.unq.spring.domain.model.exception.MuchoPesoException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Personaje {

    private PersonajeId id;
    private String nombre;
    private int vida;
    private int pesoMaximo;
    private List<Item> inventario;

    public Personaje(String nombre, int vida, int pesoMaximo) {
        this.nombre = nombre;
        this.vida = vida;
        this.pesoMaximo = pesoMaximo;
        this.inventario = new ArrayList<>();
    }

    public void setId(PersonajeId id) {
        this.id = id;
    }

    public int getPesoActual() {
        return inventario.stream().mapToInt(Item::getPeso).sum();
    }

    public void recoger(Item item) {
        int pesoActual = getPesoActual();
        if (pesoActual + item.getPeso() > this.pesoMaximo) {
            throw new MuchoPesoException(this, item);
        }
        this.inventario.add(item);
        item.asignarDueño(this);
    }
} 