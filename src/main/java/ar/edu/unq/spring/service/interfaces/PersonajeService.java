package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Personaje;

import java.util.Optional;

public interface PersonajeService {
    Iterable<Personaje> allPersonajes();
    void guardarPersonaje(Personaje personaje);
    Optional<Personaje> recuperarPersonaje(Long personajeId);
    void clearAll();
}