package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.EliminarTodosLosPersonajesUseCase;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EliminarTodosLosPersonajesService implements EliminarTodosLosPersonajesUseCase {

    private final PersonajeRepository personajeRepository;

    public EliminarTodosLosPersonajesService(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public void ejecutar() {
        personajeRepository.deleteAll();
    }
} 