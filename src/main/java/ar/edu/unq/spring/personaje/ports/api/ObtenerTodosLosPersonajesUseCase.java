package ar.edu.unq.spring.personaje.ports.api;

import ar.edu.unq.spring.personaje.domain.model.Personaje;

import java.util.List;

/**
 * Puerto de entrada para obtener todos los personajes
 */
public interface ObtenerTodosLosPersonajesUseCase {
    
    List<Personaje> ejecutar();
} 