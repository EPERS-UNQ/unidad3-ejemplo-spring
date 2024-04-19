package ar.edu.unq.spring.service.interfaces

import ar.edu.unq.spring.modelo.Personaje

/* Eviten el Spanglish :D */
interface PersonajeService {
    fun allPersonajes(): Collection<Personaje>
    fun guardarPersonaje(personaje: Personaje)
    fun recuperarPersonaje(personajeId: Long): Personaje?
}