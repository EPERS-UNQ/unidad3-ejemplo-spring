package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Personaje;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeDAO extends CrudRepository<Personaje, Long> {
}