package ar.edu.unq.spring.inventario.ports.api;

import ar.edu.unq.spring.inventario.domain.model.Item;

public interface CrearItemUseCase {
    
    Item ejecutar(Item item);
} 