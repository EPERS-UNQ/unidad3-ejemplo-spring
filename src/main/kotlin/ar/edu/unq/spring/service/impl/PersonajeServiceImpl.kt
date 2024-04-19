package ar.edu.unq.spring.service.impl

import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido
import ar.edu.unq.spring.persistence.PersonajeDAO
import ar.edu.unq.spring.service.interfaces.PersonajeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PersonajeServiceImpl() : PersonajeService {

    @Autowired private lateinit var personajeDAO: PersonajeDAO

    override fun allPersonajes(): Collection<Personaje> {
        return personajeDAO.findAll().toMutableSet()
    }

    override fun guardarPersonaje(personaje: Personaje) {
        try {
            personajeDAO.save(personaje)
        } catch (e: DataIntegrityViolationException) {
            throw NombreDePersonajeRepetido(personaje.nombre!!)
        }
    }

    override fun recuperarPersonaje(personajeId: Long): Personaje? {
        return personajeDAO.findByIdOrNull(personajeId)
    }

}