package ar.edu.unq.spring.application.port.input;

import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.PersonajeId;

/**
 * Puerto de entrada para agregar un item a un personaje
 */
public interface AgregarItemAPersonajeUseCase {
    
    void ejecutar(PersonajeId personajeId, Item item);
} 