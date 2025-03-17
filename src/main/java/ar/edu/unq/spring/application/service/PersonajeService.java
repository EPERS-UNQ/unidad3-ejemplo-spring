package ar.edu.unq.spring.application.service;

import ar.edu.unq.spring.application.port.input.PersonajeUseCase;
import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.domain.model.exception.NombreDePersonajeRepetidoException;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class PersonajeService implements PersonajeUseCase {

    private final PersonajeRepository personajeRepository;

    public PersonajeService(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public List<Personaje> obtenerTodosLosPersonajes() {
        return personajeRepository.findAll();
    }

    @Override
    public Personaje obtenerPersonajePorId(PersonajeId id) {
        return personajeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Personaje no encontrado con id: " + id.value()));
    }

    @Override
    public Personaje crearPersonaje(Personaje personaje) {
        if (personajeRepository.existsByNombre(personaje.getNombre())) {
            throw new NombreDePersonajeRepetidoException(personaje.getNombre());
        }
        return personajeRepository.save(personaje);
    }

    @Override
    public void eliminarTodosLosPersonajes() {
        personajeRepository.deleteAll();
    }
} 