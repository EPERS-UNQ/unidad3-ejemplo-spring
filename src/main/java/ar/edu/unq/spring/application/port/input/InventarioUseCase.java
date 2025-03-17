package ar.edu.unq.spring.application.port.input;

import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.ItemId;
import ar.edu.unq.spring.domain.model.PersonajeId;

import java.util.List;

/**
 * Puerto de entrada para operaciones con Inventario
 */
public interface InventarioUseCase {
    
    List<Item> obtenerInventarioDePersonaje(PersonajeId personajeId);
    
    void agregarItemAPersonaje(PersonajeId personajeId, Item item);
    
    Item obtenerItemPorId(ItemId itemId);

    Item crearItem(Item item);
} 