package ar.edu.unq.spring.persistence.repository;

import ar.edu.unq.spring.modelo.Personaje;

import java.util.List;
import java.util.Optional;

public interface PersonajeRepository {
    List<Personaje> findAll();
    Optional<Personaje> findById(Long id);
    Personaje save(Personaje personaje);
    void deleteAll();
}
