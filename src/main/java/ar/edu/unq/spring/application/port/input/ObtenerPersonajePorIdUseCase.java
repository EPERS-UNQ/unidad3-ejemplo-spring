package ar.edu.unq.spring.application.port.input;

import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.PersonajeId;

/**
 * Puerto de entrada para obtener un personaje por su ID
 */
public interface ObtenerPersonajePorIdUseCase {
    
    Personaje ejecutar(PersonajeId id);
} 