package ar.edu.unq.spring.service

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.persistence.ItemDAO
import ar.edu.unq.spring.persistence.ItemJPADTO
import ar.edu.unq.spring.persistence.PersonajeDAO
import ar.edu.unq.spring.persistence.PersonajeJPADTO
import ar.edu.unq.spring.persistence.PersonajeJPADTO.Companion.desdeModelo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InventarioServiceImp() : InventarioService {

    @Autowired private lateinit var personajeDAO: PersonajeDAO
    @Autowired private lateinit var itemDAO: ItemDAO

    override fun allItems(): Collection<Item> {
        return itemDAO.findAll().map { i -> i.aModelo() }.toMutableList()
    }

    override fun allPersonajes(): Collection<Personaje> {
        return personajeDAO.findAll().map { i -> i.aModelo() }.toMutableList()
    }

    override fun heaviestItem(): Item {
        return itemDAO.findTopByOrderByPesoDesc().aModelo()
    }

    override fun guardarItem(item: Item): Long {
        val itemJPADTO = ItemJPADTO.desdeModelo(item)
        itemDAO.save(itemJPADTO)
        item.id = itemJPADTO.id
        return item.id!!
    }

    override fun guardarPersonaje(personaje: Personaje): Long {
        val personajeJPADTO = desdeModelo(personaje)
        personajeDAO.save(personajeJPADTO)
        personaje.id = personajeJPADTO.id
        return personaje.id!!
    }

    override fun recuperarPersonaje(personajeId: Long): Personaje {
        return personajeDAO.findByIdOrNull(personajeId)!!.aModelo()
    }

    override fun recoger(personajeId: Long, itemId: Long) {
        val personaje = personajeDAO.findByIdOrNull(personajeId)!!.aModelo()
        val item = itemDAO.findByIdOrNull(itemId)!!.aModelo()
        item?.let { i -> personaje?.recoger(i) }
        personaje?.let { p -> personajeDAO.save(desdeModelo(p)) }
    }

    override fun getMasPesados(peso: Int): Collection<Item> {
        return itemDAO.getMasPesados(peso).map { i -> i.aModelo() }.toMutableList()
    }

    override fun getItemsPersonajesDebiles(vida: Int): Collection<Item> {
        return itemDAO.getItemsDePersonajesDebiles(vida).map { i -> i.aModelo() }.toMutableList()
    }

    override fun clearAll() {
        itemDAO.deleteAll()
        personajeDAO.deleteAll()
    }


}
