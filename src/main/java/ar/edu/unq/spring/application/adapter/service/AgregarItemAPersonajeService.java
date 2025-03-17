package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.AgregarItemAPersonajeUseCase;
import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class AgregarItemAPersonajeService implements AgregarItemAPersonajeUseCase {

    private final PersonajeRepository personajeRepository;

    public AgregarItemAPersonajeService(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public void ejecutar(PersonajeId personajeId, Item item) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new NoSuchElementException("Personaje no encontrado con id: " + personajeId.value()));
        
        personaje.recoger(item);
        personajeRepository.save(personaje);
    }
} 