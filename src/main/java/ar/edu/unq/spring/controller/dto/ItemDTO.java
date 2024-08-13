package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;

public class ItemDTO {
    private Long id;
    private String nombre;
    private int peso;
    private Long ownerId;

    public ItemDTO(Long id, String nombre, int peso, Long ownerId) {
        this.id = id;
        this.nombre = nombre;
        this.peso = peso;
        this.ownerId = ownerId;
    }

    public static ItemDTO desdeModelo(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getNombre(),
                item.getPeso(),
                item.getOwner() != null ? item.getOwner().getId() : null
        );
    }

    public Item aModelo(Personaje personaje) {
        Item item = aModelo();
        item.setOwner(personaje);
        return item;
    }

    public Item aModelo() {
        Item item = new Item(this.nombre, this.peso);
        item.setId(this.id);
        return item;
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

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}