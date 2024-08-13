package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Personaje;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonajeDTO {
    private Long id;
    private String nombre;
    private int vida;
    private int pesoMaximo;
    private Set<ItemDTO> inventario;

    public PersonajeDTO(Long id, String nombre, int vida, int pesoMaximo, Set<ItemDTO> inventario) {
        this.id = id;
        this.nombre = nombre;
        this.vida = vida;
        this.pesoMaximo = pesoMaximo;
        this.inventario = inventario;
    }

    public static PersonajeDTO desdeModelo(Personaje personaje) {
        return new PersonajeDTO(
                personaje.getId(),
                personaje.getNombre(),
                personaje.getVida(),
                personaje.getPesoMaximo(),
                personaje.getInventario().stream()
                        .map(ItemDTO::desdeModelo)
                        .collect(Collectors.toCollection(HashSet::new))
        );
    }

    public Personaje aModelo() {
        Personaje personaje = new Personaje();
        personaje.setId(this.id);
        personaje.setNombre(this.nombre);
        personaje.setVida(this.vida);
        personaje.setPesoMaximo(this.pesoMaximo);
        personaje.setInventario(this.inventario != null ?
                this.inventario.stream()
                        .map(itemDTO -> itemDTO.aModelo(personaje))
                        .collect(Collectors.toCollection(HashSet::new)) :
                new HashSet<>()
        );
        return personaje;
    }

    // Getters and setters
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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(int pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public Set<ItemDTO> getInventario() {
        return inventario;
    }

    public void setInventario(Set<ItemDTO> inventario) {
        this.inventario = inventario;
    }
}