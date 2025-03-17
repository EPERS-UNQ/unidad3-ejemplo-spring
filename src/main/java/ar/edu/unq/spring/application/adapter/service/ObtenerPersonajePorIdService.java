package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.ObtenerPersonajePorIdUseCase;
import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class ObtenerPersonajePorIdService implements ObtenerPersonajePorIdUseCase {

    private final PersonajeRepository personajeRepository;

    public ObtenerPersonajePorIdService(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public Personaje ejecutar(PersonajeId id) {
        return personajeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Personaje no encontrado con id: " + id.value()));
    }
} 