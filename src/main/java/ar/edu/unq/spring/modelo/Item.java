package ar.edu.unq.spring.modelo;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Item {

    private Long id;
    private String nombre;
    private int peso;

    private Set<Personaje> owners = new HashSet<>();

    public Item(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
    }
}