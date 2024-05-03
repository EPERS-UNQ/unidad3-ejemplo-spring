package ar.edu.unq.spring.controller.dto

import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.modelo.clasesDePersonajes.Guerrero
import ar.edu.unq.spring.modelo.clasesDePersonajes.Mago
import ar.edu.unq.spring.modelo.clasesDePersonajes.Picaro

class PersonajeDTO(
    val id: Long?,
    val nombre: String?,
    val vida: Int,
    val pesoMaximo: Int,
    val tipo: String,
    val estadisticaDeClase: Int,
    val inventario: MutableSet<ItemDTO>?
) {
    companion object {
        fun desdeModelo(personaje: Personaje) =
            PersonajeDTO(
                id = personaje.id,
                nombre = personaje.nombre,
                vida = personaje.vida,
                pesoMaximo = personaje.pesoMaximo,
                tipo = when (personaje) {
                    is Guerrero -> "Guerrero"
                    is Mago -> "Mago"
                    is Picaro -> "Picaro"
                    else -> throw IllegalArgumentException("No existe esa subclase")
                },
                estadisticaDeClase = when (personaje) {
                    is Guerrero -> (personaje as Guerrero).fuerza
                    is Mago -> (personaje as Mago).mana
                    is Picaro -> (personaje as Picaro).sigilo
                    else -> throw IllegalArgumentException("No existe esa subclase")
                },
                inventario = personaje.inventario
                    ?.map { item -> ItemDTO.desdeModelo(item) }
                    ?.toCollection(HashSet())
            )
    }

    fun aModelo(): Personaje {
        val personaje: Personaje = when (tipo) {
            "Guerrero" -> Guerrero().apply { fuerza = estadisticaDeClase }
            "Mago" -> Mago().apply { mana = estadisticaDeClase }
            "Picaro" -> Picaro().apply { sigilo = estadisticaDeClase }
            else -> throw IllegalArgumentException("No existe esa subclase")
        }
        personaje.id = this.id
        personaje.nombre = this.nombre
        personaje.vida = this.vida
        personaje.pesoMaximo = this.pesoMaximo
        personaje.inventario = this.inventario
            ?.map { itemDTO -> itemDTO.aModelo(personaje) }
            ?.toCollection(HashSet())
            ?: HashSet()
        return personaje
    }
}
