package ar.edu.unq.spring.personaje.application.service;

import ar.edu.unq.spring.personaje.ports.api.ObtenerTodosLosPersonajesUseCase;
import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.ports.infra.PersonajeRepository;
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