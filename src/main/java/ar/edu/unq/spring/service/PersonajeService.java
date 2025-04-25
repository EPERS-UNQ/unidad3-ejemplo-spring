package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Personaje;

import java.util.Collection;

public interface PersonajeService {
    void guardarPersonaje(Personaje personaje);
    Personaje recuperarPersonaje(Long personajeId) throws InterruptedException;
    Personaje recuperarPersonajePorNombre(String nombre);

    void recoger(Long personajeId, Long itemId);
    Collection<Personaje> allPersonajes();
    Collection<Personaje> personajesPaginados(int size, int page);

    void clearAll();
}
