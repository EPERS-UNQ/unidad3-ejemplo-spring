package ar.edu.unq.spring.inventario.ports.api;

import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.inventario.domain.model.ItemId;

public interface ObtenerItemPorIdUseCase {
    
    Item ejecutar(ItemId id);
} 