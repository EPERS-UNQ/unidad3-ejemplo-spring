package ar.edu.unq.spring.personaje.application.service;

import ar.edu.unq.spring.personaje.ports.api.ObtenerPersonajePorIdUseCase;
import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;
import ar.edu.unq.spring.personaje.ports.infra.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new RuntimeException("No se encontró el personaje con id " + id.value()));
    }
} 