package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.ObtenerTodosLosPersonajesUseCase;
import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ObtenerTodosLosPersonajesService implements ObtenerTodosLosPersonajesUseCase {

    private final PersonajeRepository personajeRepository;

    public ObtenerTodosLosPersonajesService(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public List<Personaje> ejecutar() {
        return personajeRepository.findAll();
    }
} 