package ar.edu.unq.spring.controller.dto

import ar.edu.unq.spring.modelo.Personaje

class PersonajeJsonDTO(
    val id: Long?,
    val nombre: String?,
    val vida: Int,
    val pesoMaximo: Int,
    val inventario: MutableSet<ItemJsonDTO>?
) {
    companion object {
        fun desdeModelo(personaje: Personaje) =
            PersonajeJsonDTO(
                id = personaje.id,
                nombre = personaje.nombre,
                vida = personaje.vida,
                pesoMaximo = personaje.pesoMaximo,
                inventario = personaje.inventario
                    .map { item -> ItemJsonDTO.desdeModelo(item) }
                    .toCollection(HashSet())
            )
    }


    fun aModelo(): Personaje {
        val personaje = Personaje()
        personaje.id = this.id
        personaje.nombre = this.nombre
        personaje.vida = this.vida
        personaje.pesoMaximo = this.pesoMaximo
        personaje.inventario = this.inventario?.
            map { itemDTO  -> itemDTO.aModelo(personaje) }?.
            toCollection(HashSet())
            ?: HashSet()
        return personaje
    }
}