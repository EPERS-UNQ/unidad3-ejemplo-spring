package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.modelo.exception.MuchoPesoException;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Personaje {

    private Long id;
    private String nombre;
    private int vida;
    private int pesoMaximo;
    private Set<Item> inventario = new HashSet<>();

    public Personaje(String nombre, int vida, int pesoMaximo) {
        this.nombre = nombre;
        this.vida = vida;
        this.pesoMaximo = pesoMaximo;
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
        item.setOwner(this);
    }

}