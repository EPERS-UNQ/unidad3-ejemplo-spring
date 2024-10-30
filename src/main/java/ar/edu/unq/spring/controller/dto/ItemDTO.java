package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDTO {

    public final Long id;
    public final String nombre;
    public final int peso;
    public final Long ownerId;

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
}
