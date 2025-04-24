package ar.edu.unq.spring.personaje.ports.api;

import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;

public interface ObtenerPersonajePorIdUseCase {
    
    Personaje ejecutar(PersonajeId id);
} 