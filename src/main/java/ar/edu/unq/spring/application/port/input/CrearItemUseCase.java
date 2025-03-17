package ar.edu.unq.spring.application.port.input;

import ar.edu.unq.spring.domain.model.Item;

/**
 * Puerto de entrada para crear un item
 */
public interface CrearItemUseCase {
    
    Item ejecutar(Item item);
} 