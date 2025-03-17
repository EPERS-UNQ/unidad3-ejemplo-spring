package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.CrearPersonajeUseCase;
import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.exception.NombreDePersonajeRepetidoException;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
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
            throw new NombreDePersonajeRepetidoException(personaje.getNombre());
        }
        return personajeRepository.save(personaje);
    }
} 