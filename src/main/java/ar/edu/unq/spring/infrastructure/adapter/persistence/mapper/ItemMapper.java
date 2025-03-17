package ar.edu.unq.spring.infrastructure.adapter.persistence.mapper;

import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.ItemId;
import ar.edu.unq.spring.infrastructure.adapter.persistence.entity.ItemEntity;
import ar.edu.unq.spring.infrastructure.adapter.persistence.entity.PersonajeEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toDomain(ItemEntity entity) {
        Item item = new Item(entity.getNombre(), entity.getPeso());
        item.setId(ItemId.of(entity.getId()));
        
        return item;
    }

    public ItemEntity toEntity(Item domain, PersonajeEntity dueñoEntity) {
        ItemEntity entity = new ItemEntity();
        
        if (domain.getId() != null) {
            entity.setId(domain.getId().value());
        }
        
        entity.setNombre(domain.getNombre());
        entity.setPeso(domain.getPeso());
        entity.setDueño(dueñoEntity);
        
        return entity;
    }
} 