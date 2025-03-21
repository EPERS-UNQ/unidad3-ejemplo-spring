package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

public interface PersonajeDAO {
    Personaje save(Personaje personaje);
    Optional<Personaje> findById(Long id);
    Collection<Personaje> findAll();
    void deleteAll();
    void delete(Personaje personaje);
}