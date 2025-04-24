package ar.edu.unq.spring.inventario.ports.api;

import ar.edu.unq.spring.inventario.domain.model.ItemId;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;

public interface AgregarItemAPersonajeUseCase {
    
    void ejecutar(ItemId itemId, PersonajeId personajeId);
} 