package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonajeDAO extends JpaRepository<Personaje, Long> {
    Personaje findByNombre(String nombre);
}
