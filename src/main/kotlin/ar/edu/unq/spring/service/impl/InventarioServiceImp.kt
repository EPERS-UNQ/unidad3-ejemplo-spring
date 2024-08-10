package ar.edu.unq.spring.service.impl

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.persistence.ItemDAO
import ar.edu.unq.spring.persistence.PersonajeDAO
import ar.edu.unq.spring.service.interfaces.InventarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InventarioServiceImp() : InventarioService {

    @Autowired private lateinit var personajeDAO: PersonajeDAO
    @Autowired private lateinit var itemDAO: ItemDAO
    override fun getItem(itemId: Long): Item {
        return itemDAO.findById(itemId).get()
    }

    override fun allItems(): Collection<Item> {
        return itemDAO.findAll().toMutableSet()
    }

    override fun heaviestItem(): Item {
        return itemDAO.findTopByOrderByPesoDesc()
    }

    override fun guardarItem(item: Item): Item {
       return itemDAO.save(item)
    }

    override fun recoger(personajeId: Long, itemId: Long) {
        val personaje = personajeDAO.findByIdOrNull(personajeId)
        val item = itemDAO.findByIdOrNull(itemId)
        item?.let { i -> personaje?.recoger(i) }
        personaje?.let { p -> personajeDAO.save(p) }
    }

    override fun getMasPesados(peso: Int): Collection<Item> {
        return itemDAO.getMasPesados(peso)
    }

    override fun getItemsPersonajesDebiles(vida: Int): Collection<Item> {
        return itemDAO.getItemsDePersonajesDebiles(vida)
    }

    override fun clearAll() {
        itemDAO.deleteAll()
    }

    override fun deleteItem(item: Item) {
        itemDAO.delete(item)
    }

}
