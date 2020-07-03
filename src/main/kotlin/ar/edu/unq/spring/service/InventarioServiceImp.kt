package ar.edu.unq.spring.service

import ar.edu.unq.spring.ItemDAO
import ar.edu.unq.spring.PersonajeDAO
import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InventarioServiceImp(
    private val personajeDAO: PersonajeDAO,
    private val itemDAO: ItemDAO
) : InventarioService {

    override fun allItems(): Collection<Item> {
        return itemDAO.findAll()
    }

    override fun heaviestItem(): Item {
        return itemDAO.findTopByOrderByPesoDesc()
    }

    override fun guardarItem(item: Item) {
        itemDAO.save(item)
    }

    override fun guardarPersonaje(personaje: Personaje) {
        personajeDAO.save(personaje)
    }

    override fun recuperarPersonaje(personajeId: Long?): Personaje? {
        return personajeDAO.findByIdOrNull(personajeId)
    }

    override fun recoger(personajeId: Long?, itemId: Long?) {
        val personaje = personajeDAO.findByIdOrNull(personajeId)
        val item = itemDAO.findByIdOrNull(itemId)
        item?.let { i -> personaje?.recoger(i) }
        personaje?.let { p -> personajeDAO.save(p) }
    }

    override fun getMasPesdos(peso: Int): Collection<Item> {
        return itemDAO.getMasPesados(peso)
    }

    override fun getItemsPersonajesDebiles(vida: Int): Collection<Item> {
        return itemDAO.getItemsDePersonajesDebiles(vida)
    }


}
