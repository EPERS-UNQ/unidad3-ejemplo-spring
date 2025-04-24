package ar.edu.unq.spring.inventario.ports.api;

import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;

import java.util.List;

public interface ObtenerInventarioDePersonajeUseCase {
    
    List<Item> ejecutar(PersonajeId personajeId);
} 