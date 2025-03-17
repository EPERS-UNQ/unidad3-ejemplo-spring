package ar.edu.unq.spring.application.port.input;

import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.PersonajeId;

import java.util.List;

/**
 * Puerto de entrada para operaciones con Personajes
 */
public interface PersonajeUseCase {
    
    List<Personaje> obtenerTodosLosPersonajes();
    
    Personaje obtenerPersonajePorId(PersonajeId id);
    
    Personaje crearPersonaje(Personaje personaje);
    
    void eliminarTodosLosPersonajes();
} 