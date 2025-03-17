package ar.edu.unq.spring.application.adapter.rest.dto;

import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.ItemId;

public record ItemCommunication(Long id, String nombre, int peso) {

    public Item toDomain() {
        Item item = new Item(this.nombre, this.peso);
        
        if (this.id != null) {
            item.setId(ItemId.of(this.id));
        }
        
        return item;
    }
} 