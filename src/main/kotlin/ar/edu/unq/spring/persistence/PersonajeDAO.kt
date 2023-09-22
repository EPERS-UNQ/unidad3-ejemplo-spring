package ar.edu.unq.spring.persistence

import ar.edu.unq.spring.modelo.Personaje
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface PersonajeDAO : JpaRepository<Personaje, Long> {

    fun findByNombre(nombre:String): Personaje?
}
