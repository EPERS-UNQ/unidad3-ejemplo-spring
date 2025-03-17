package ar.edu.unq.spring.infrastructure.adapter.persistence.repository;

import ar.edu.unq.spring.infrastructure.adapter.persistence.entity.PersonajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPersonajeRepository extends JpaRepository<PersonajeEntity, Long> {
    boolean existsByNombre(String nombre);
} 