package ar.edu.unq.spring.controller

import ar.edu.unq.spring.controller.dto.PersonajeDTO
import ar.edu.unq.spring.service.interfaces.PersonajeService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/personaje")
class PersonajeControllerREST(private val personajeService: PersonajeService) {

    @PostMapping("/personaje")
    fun guardarPersonaje(@RequestBody personaje: PersonajeDTO) = personajeService.guardarPersonaje(personaje.aModelo())

    @GetMapping("/{personajeId}")
    fun recuperarPersonaje(@PathVariable personajeId: Long) = PersonajeDTO.desdeModelo(personajeService.recuperarPersonaje(personajeId)!!)

    @GetMapping("/allPersonajes")
    fun allPersonajes() = personajeService.allPersonajes().map { personaje -> PersonajeDTO.desdeModelo(personaje)  }

}