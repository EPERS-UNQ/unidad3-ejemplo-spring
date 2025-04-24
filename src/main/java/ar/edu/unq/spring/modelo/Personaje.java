package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.modelo.exception.MuchoPesoException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.HashSet;
import java.util.Set;

@Entity @Getter @Setter @Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String nombre;

    private int vida;
    private int pesoMaximo;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
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
