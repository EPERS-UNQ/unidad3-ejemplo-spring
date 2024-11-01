package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.PersonajeDTO;
import ar.edu.unq.spring.service.PersonajeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @CrossOrigin
@RequestMapping("/personaje")
public class PersonajeControllerREST {

    private final PersonajeService personajeService;

    public PersonajeControllerREST(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @PostMapping
    public void guardarPersonaje(@RequestBody PersonajeDTO personaje) {
        personajeService.guardarPersonaje(personaje.aModelo());
    }

    @GetMapping("/{personajeId}")
    public PersonajeDTO recuperarPersonaje(@PathVariable Long personajeId) {
        return PersonajeDTO.desdeModelo(personajeService.recuperarPersonaje(personajeId));
    }

    @PutMapping("/{personajeId}/recoger/{itemId}")
    public void recoger(@PathVariable Long personajeId, @PathVariable Long itemId) {
        personajeService.recoger(personajeId, itemId);
    }

    @GetMapping("/nombre/{nombre}")
    public PersonajeDTO recuperarPersonajePorNombre(@PathVariable String nombre) {
        var personaje = personajeService.recuperarPersonajePorNombre(nombre);
        return PersonajeDTO.desdeModelo(personaje);
    }

    @GetMapping("/all")
    public List<PersonajeDTO> allPersonajes() {
        return personajeService.allPersonajes().stream()
                .map(PersonajeDTO::desdeModelo)
                .toList();
    }

    @GetMapping("/allPaginated")
    public List<PersonajeDTO> personajesPaginados(@RequestParam(value = "size", defaultValue = "10") int size,
                                                  @RequestParam(value = "page", defaultValue = "0") int page) {
        return personajeService.personajesPaginados(size, page).stream()
                .map(PersonajeDTO::desdeModelo)
                .toList();
    }
}
