package ar.edu.unq.spring.personaje.ports.api;

import ar.edu.unq.spring.personaje.domain.model.Personaje;

public interface CrearPersonajeUseCase {
    
    Personaje ejecutar(Personaje personaje);
} 