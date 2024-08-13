package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.PersonajeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personajes")
public class PersonajeControllerREST {

    @Autowired
    private PersonajeDAO personajeDAO;

    @GetMapping
    public Iterable<Personaje> getAllPersonajes() {
        return personajeDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personaje> getPersonajeById(@PathVariable Long id) {
        Optional<Personaje> personaje = personajeDAO.findById(id);
        return personaje.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Personaje createPersonaje(@RequestBody Personaje personaje) {
        return personajeDAO.save(personaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personaje> updatePersonaje(@PathVariable Long id, @RequestBody Personaje personajeDetails) {
        Optional<Personaje> optionalPersonaje = personajeDAO.findById(id);
        if (optionalPersonaje.isPresent()) {
            Personaje personaje = optionalPersonaje.get();
            personaje.setNombre(personajeDetails.getNombre());
            personaje.setVida(personajeDetails.getVida());
            final Personaje updatedPersonaje = personajeDAO.save(personaje);
            return ResponseEntity.ok(updatedPersonaje);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonaje(@PathVariable Long id) {
        Optional<Personaje> personaje = personajeDAO.findById(id);
        if (personaje.isPresent()) {
            personajeDAO.delete(personaje.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}