package ar.edu.unq.spring.personaje.application.service;

import ar.edu.unq.spring.personaje.ports.api.CrearPersonajeUseCase;
import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.ports.infra.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CrearPersonajeService implements CrearPersonajeUseCase {

    private final PersonajeRepository personajeRepository;

    public CrearPersonajeService(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public Personaje ejecutar(Personaje personaje) {
        if (personajeRepository.existsByNombre(personaje.getNombre())) {
            throw new RuntimeException("Ya existe un personaje con el nombre " + personaje.getNombre());
        }
        return personajeRepository.save(personaje);
    }
} 