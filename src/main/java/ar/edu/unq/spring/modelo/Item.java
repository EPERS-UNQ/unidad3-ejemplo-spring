package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private int peso;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Personaje owner;

    public Item() {
    }

    public Item(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Personaje getOwner() {
        return owner;
    }

    public void setOwner(Personaje owner) {
        this.owner = owner;
    }
}