package ar.edu.unq.spring.application.port.input;

import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.PersonajeId;

import java.util.List;

/**
 * Puerto de entrada para obtener el inventario de un personaje
 */
public interface ObtenerInventarioDePersonajeUseCase {
    
    List<Item> ejecutar(PersonajeId personajeId);
} 