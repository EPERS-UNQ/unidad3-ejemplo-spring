package ar.edu.unq.spring.infrastructure.adapter.rest;

import ar.edu.unq.spring.application.port.input.PersonajeUseCase;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.infrastructure.adapter.rest.dto.PersonajeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personajes")
public class PersonajeController {

    private final PersonajeUseCase personajeUseCase;

    public PersonajeController(PersonajeUseCase personajeUseCase) {
        this.personajeUseCase = personajeUseCase;
    }

    @GetMapping
    public List<PersonajeDTO> getAllPersonajes() {
        return personajeUseCase.obtenerTodosLosPersonajes().stream()
                .map(PersonajeDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeDTO> getPersonajeById(@PathVariable Long id) {
        return ResponseEntity.ok(
                PersonajeDTO.fromDomain(
                        personajeUseCase.obtenerPersonajePorId(PersonajeId.of(id))
                )
        );
    }

    @PostMapping
    public ResponseEntity<PersonajeDTO> createPersonaje(@RequestBody PersonajeDTO personajeDTO) {
        return ResponseEntity.ok(
                PersonajeDTO.fromDomain(
                        personajeUseCase.crearPersonaje(personajeDTO.toDomain())
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPersonajes() {
        personajeUseCase.eliminarTodosLosPersonajes();
        return ResponseEntity.noContent().build();
    }
} 