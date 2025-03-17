package ar.edu.unq.spring.application.port.input;

import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.ItemId;

/**
 * Puerto de entrada para obtener un item por su ID
 */
public interface ObtenerItemPorIdUseCase {
    
    Item ejecutar(ItemId itemId);
} 