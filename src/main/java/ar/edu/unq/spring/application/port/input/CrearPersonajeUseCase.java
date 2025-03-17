package ar.edu.unq.spring.application.port.input;

import ar.edu.unq.spring.domain.model.Personaje;

/**
 * Puerto de entrada para crear un personaje
 */
public interface CrearPersonajeUseCase {
    
    Personaje ejecutar(Personaje personaje);
} 