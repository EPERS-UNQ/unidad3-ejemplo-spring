package ar.edu.unq.spring

import ar.edu.unq.spring.modelo.Personaje
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Tiene la responsabilidad de guardar y recuperar personajes desde
 * el medio persistente
 */
interface PersonajeDAO : JpaRepository<Personaje, Long> {


}
