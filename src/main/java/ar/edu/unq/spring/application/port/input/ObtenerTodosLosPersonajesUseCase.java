package ar.edu.unq.spring.application.port.input;

import ar.edu.unq.spring.domain.model.Personaje;

import java.util.List;

/**
 * Puerto de entrada para obtener todos los personajes
 */
public interface ObtenerTodosLosPersonajesUseCase {
    
    List<Personaje> ejecutar();
} 