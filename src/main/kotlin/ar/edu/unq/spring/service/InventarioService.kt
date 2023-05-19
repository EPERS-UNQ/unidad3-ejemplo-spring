package ar.edu.unq.spring.service

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.persistence.ItemJPADTO
import ar.edu.unq.spring.persistence.PersonajeJPADTO

/* IMPORTANTE: En este service estamos manejando tanto personajes como items solo a modo de ejemplo.
* Recuerden que es importante dividir responsabilidades bien en nuestros objetos.
* Este servicio deberia estar dividido en un personaje service, y en un inventario service */
interface InventarioService {
    fun allItems(): Collection<Item>
    fun allPersonajes(): Collection<Personaje>
    fun heaviestItem(): Item
    fun guardarItem(item: Item): Long
    fun guardarPersonaje(personaje: Personaje): Long
    fun recuperarPersonaje(personajeId: Long): Personaje
    fun recoger(personajeId: Long, itemId: Long)
    fun getMasPesados(peso: Int): Collection<Item>
    fun getItemsPersonajesDebiles(vida: Int): Collection<Item>
    fun clearAll()
}