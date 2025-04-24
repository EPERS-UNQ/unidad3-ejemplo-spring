package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.EliminarTodosLosPersonajesUseCase;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EliminarTodosLosPersonajesService implements EliminarTodosLosPersonajesUseCase {

    private final PersonajeRepository personajeRepository;
    private final EliminarTodosLosItemsService eliminarTodosLosItemsService;

    public EliminarTodosLosPersonajesService(PersonajeRepository personajeRepository, EliminarTodosLosItemsService eliminarTodosLosItemsService) {
        this.personajeRepository = personajeRepository;
        this.eliminarTodosLosItemsService = eliminarTodosLosItemsService;
    }

    @Override
    public void ejecutar() {
        eliminarTodosLosItemsService.ejecutar();
        personajeRepository.deleteAll();
    }
} 