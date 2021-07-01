package ar.edu.unq.spring.persistence

import ar.edu.unq.spring.modelo.Personaje
import org.springframework.data.jpa.repository.JpaRepository

interface PersonajeDAO : JpaRepository<Personaje, Long> {}
