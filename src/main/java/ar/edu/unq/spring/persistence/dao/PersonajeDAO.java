package ar.edu.unq.spring.persistence.dao;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.dto.PersonajeJPADTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeDAO extends JpaRepository<PersonajeJPADTO, Long> {}