package ar.edu.unq.spring.inventario.ports.infra;

import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.inventario.domain.model.ItemId;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    
    List<Item> findAll();
    
    Optional<Item> findById(ItemId id);
    
    Item save(Item item);
    
    List<Item> findByPersonajeId(PersonajeId personajeId);

    void deleteAll();
} 