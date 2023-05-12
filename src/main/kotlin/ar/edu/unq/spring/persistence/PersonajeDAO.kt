package ar.edu.unq.spring.persistence

import org.springframework.data.repository.CrudRepository

interface PersonajeDAO : CrudRepository<PersonajeJPADTO, Long> {}
