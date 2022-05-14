package ar.edu.unq.spring.persistence

import ar.edu.unq.spring.modelo.Personaje
import org.springframework.data.repository.CrudRepository

interface PersonajeDAO : CrudRepository<Personaje, Long> {}
