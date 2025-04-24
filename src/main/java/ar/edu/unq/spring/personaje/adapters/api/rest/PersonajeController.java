package ar.edu.unq.spring.personaje.adapters.api.rest;

import ar.edu.unq.spring.personaje.ports.api.CrearPersonajeUseCase;
import ar.edu.unq.spring.personaje.ports.api.EliminarTodosLosPersonajesUseCase;
import ar.edu.unq.spring.personaje.ports.api.ObtenerPersonajePorIdUseCase;
import ar.edu.unq.spring.personaje.ports.api.ObtenerTodosLosPersonajesUseCase;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;
import ar.edu.unq.spring.personaje.adapters.api.rest.dto.PersonajeCommunication;
import ar.edu.unq.spring.personaje.adapters.api.rest.dto.PersonajePresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personajes")
public class PersonajeController {

    private final ObtenerTodosLosPersonajesUseCase obtenerTodosLosPersonajesUseCase;
    private final ObtenerPersonajePorIdUseCase obtenerPersonajePorIdUseCase;
    private final CrearPersonajeUseCase crearPersonajeUseCase;
    private final EliminarTodosLosPersonajesUseCase eliminarTodosLosPersonajesUseCase;

    public PersonajeController(
            ObtenerTodosLosPersonajesUseCase obtenerTodosLosPersonajesUseCase,
            ObtenerPersonajePorIdUseCase obtenerPersonajePorIdUseCase,
            CrearPersonajeUseCase crearPersonajeUseCase,
            EliminarTodosLosPersonajesUseCase eliminarTodosLosPersonajesUseCase) {
        this.obtenerTodosLosPersonajesUseCase = obtenerTodosLosPersonajesUseCase;
        this.obtenerPersonajePorIdUseCase = obtenerPersonajePorIdUseCase;
        this.crearPersonajeUseCase = crearPersonajeUseCase;
        this.eliminarTodosLosPersonajesUseCase = eliminarTodosLosPersonajesUseCase;
    }

    @GetMapping
    public List<PersonajePresentation> getAllPersonajes() {
        return obtenerTodosLosPersonajesUseCase.ejecutar().stream()
                .map(PersonajePresentation::fromDomain)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajePresentation> getPersonajeById(@PathVariable Long id) {
        return ResponseEntity.ok(
                PersonajePresentation.fromDomain(
                        obtenerPersonajePorIdUseCase.ejecutar(PersonajeId.of(id))
                )
        );
    }

    @PostMapping
    public ResponseEntity<PersonajePresentation> createPersonaje(@RequestBody PersonajeCommunication personajeCommunication) {
        return ResponseEntity.ok(
                PersonajePresentation.fromDomain(
                        crearPersonajeUseCase.ejecutar(personajeCommunication.toDomain())
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPersonajes() {
        eliminarTodosLosPersonajesUseCase.ejecutar();
        return ResponseEntity.noContent().build();
    }
} 