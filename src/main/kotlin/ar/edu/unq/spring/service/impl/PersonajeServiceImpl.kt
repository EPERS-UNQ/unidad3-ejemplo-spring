package ar.edu.unq.spring.service.impl

import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido
import ar.edu.unq.spring.persistence.PersonajeDAO
import ar.edu.unq.spring.service.interfaces.PersonajeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

@Service
class PersonajeServiceImpl() : PersonajeService {

    @Autowired private lateinit var personajeDAO: PersonajeDAO
    @Autowired private lateinit var transactionManager: PlatformTransactionManager

    override fun allPersonajes(): Collection<Personaje> {
        return personajeDAO.findAll().toMutableSet()
    }

    override fun guardarPersonaje(personaje: Personaje) {
        val definition = DefaultTransactionDefinition()
        definition.propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRED
        val transactionStatus: TransactionStatus = transactionManager.getTransaction(definition)
        try {
            personajeDAO.save(personaje)
            transactionManager.commit(transactionStatus)
        } catch (e: DataIntegrityViolationException) {
            transactionManager.rollback(transactionStatus)
            throw NombreDePersonajeRepetido(personaje.nombre!!)
        } catch (ex: Exception) {
            transactionManager.rollback(transactionStatus)
            throw ex
        }
    }

    override fun recuperarPersonaje(personajeId: Long): Personaje? {
        return personajeDAO.findByIdOrNull(personajeId)
    }
}
