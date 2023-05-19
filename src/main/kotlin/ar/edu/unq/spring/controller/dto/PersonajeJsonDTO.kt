package ar.edu.unq.spring.controller.dto

import ar.edu.unq.spring.modelo.Personaje

class PersonajeJsonDTO() {
    var id: Long? = null
    var nombre: String? = null
    var vida: Int = 0
    var pesoMaximo: Int = 0
    var inventario: MutableSet<ItemJsonDTO>? = null

    companion object {
        fun desdeModelo(personaje: Personaje): PersonajeJsonDTO {
            val dto = PersonajeJsonDTO()
            dto.id = personaje.id
            dto.nombre = personaje.nombre
            dto.vida = personaje.vida
            dto.pesoMaximo = personaje.pesoMaximo
            dto.inventario = personaje.inventario
                    .map { item -> ItemJsonDTO.desdeModelo(item) }
                    .toCollection(HashSet())
            return dto
        }

    }

    fun aModelo(): Personaje {
        val personaje = Personaje()
        personaje.id = this.id
        personaje.nombre = this.nombre
        personaje.vida = this.vida
        personaje.pesoMaximo = this.pesoMaximo
        personaje.inventario = this.inventario?.map { itemDTO -> itemDTO.aModelo(personaje) }?.toCollection(HashSet())
                ?: HashSet()
        return personaje
    }
}