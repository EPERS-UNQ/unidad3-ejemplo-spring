package ar.edu.unq.spring.service

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje

interface InventarioService {
    fun allItems(): Collection<Item>
    fun heaviestItem(): Item
    fun guardarItem(item: Item)
    fun guardarPersonaje(personaje: Personaje)
    fun recuperarPersonaje(personajeId: Long?): Personaje?
    fun recoger(personajeId: Long?, itemId: Long?)
    fun getMasPesdos(peso: Int): Collection<Item>
    fun getItemsPersonajesDebiles(vida: Int): Collection<Item>
}