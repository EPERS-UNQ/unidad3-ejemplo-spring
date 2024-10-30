package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.modelo.exception.MuchoPesoException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity @Getter @Setter
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// @Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Personaje {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 500)
    private String nombre;

    private int vida;
    private int pesoMaximo;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Item> inventario = new HashSet<>();

    public Personaje() {}

    public Personaje(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return nombre;
    }
}
