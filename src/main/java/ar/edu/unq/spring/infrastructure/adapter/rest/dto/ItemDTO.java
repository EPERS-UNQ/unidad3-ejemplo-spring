package ar.edu.unq.spring.infrastructure.adapter.rest.dto;

import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.ItemId;

public record ItemDTO(Long id, String nombre, int peso) {

    public static ItemDTO fromDomain(Item item) {
        return new ItemDTO(
                item.getId() != null ? item.getId().value() : null,
                item.getNombre(),
                item.getPeso()
        );
    }

    public Item toDomain() {
        Item item = new Item(this.nombre, this.peso);
        
        if (this.id != null) {
            item.setId(ItemId.of(this.id));
        }
        
        return item;
    }
} 