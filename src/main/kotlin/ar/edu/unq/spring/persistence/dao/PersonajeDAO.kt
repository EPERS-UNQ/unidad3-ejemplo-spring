package ar.edu.unq.spring.persistence.dao

import ar.edu.unq.spring.persistence.dto.PersonajeJPADTO
import org.springframework.data.repository.CrudRepository

interface PersonajeDAO : CrudRepository<PersonajeJPADTO, Long> {}
