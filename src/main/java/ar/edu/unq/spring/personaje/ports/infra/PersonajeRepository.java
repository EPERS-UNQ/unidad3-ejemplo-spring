package ar.edu.unq.spring.personaje.ports.infra;

import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;

import java.util.List;
import java.util.Optional;

public interface PersonajeRepository {
    
    List<Personaje> findAll();
    
    Optional<Personaje> findById(PersonajeId id);
    
    Personaje save(Personaje personaje);
    
    void deleteAll();
    
    boolean existsByNombre(String nombre);
} 