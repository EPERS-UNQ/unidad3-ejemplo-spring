package ar.edu.unq.spring.service.interfaces

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje

/* Eviten el Spanglish :D */
interface InventarioService {
    fun allItems(): Collection<Item>
    fun heaviestItem(): Item
    fun guardarItem(item: Item)
    fun recoger(personajeId: Long, itemId: Long)
    fun getMasPesados(peso: Int): Collection<Item>
    fun getItemsPersonajesDebiles(vida: Int): Collection<Item>
    fun clearAll()
}