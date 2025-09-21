package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.request.CrearPersonajeRequest;
import ar.edu.unq.spring.controller.dto.response.PersonajeResponse;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personajes")
public final class PersonajeControllerREST {
    private final PersonajeService personajeService;

    public PersonajeControllerREST(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @GetMapping
    public Set<PersonajeResponse> obtenerTodosLosPersonajes() {
        return personajeService.allPersonajes().stream()
                .map(PersonajeResponse::desdeModelo)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeResponse> obtenerPersonajePorId(@PathVariable Long id) {
        Personaje personaje = personajeService.recuperarPersonaje(id);
        return ResponseEntity.ok(PersonajeResponse.desdeModelo(personaje));
    }

    @PostMapping
    public void crearPersonaje(@RequestBody CrearPersonajeRequest request) {
        personajeService.guardarPersonaje(request.aModelo());
    }
}