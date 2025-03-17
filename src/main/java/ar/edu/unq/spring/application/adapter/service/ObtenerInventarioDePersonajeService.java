package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.ObtenerInventarioDePersonajeUseCase;
import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.domain.repository.ItemRepository;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ObtenerInventarioDePersonajeService implements ObtenerInventarioDePersonajeUseCase {

    private final ItemRepository itemRepository;
    private final PersonajeRepository personajeRepository;

    public ObtenerInventarioDePersonajeService(ItemRepository itemRepository, PersonajeRepository personajeRepository) {
        this.itemRepository = itemRepository;
        this.personajeRepository = personajeRepository;
    }

    @Override
    public List<Item> ejecutar(PersonajeId personajeId) {
        if (!personajeRepository.findById(personajeId).isPresent()) {
            throw new NoSuchElementException("Personaje no encontrado con id: " + personajeId.value());
        }
        return itemRepository.findByPersonajeId(personajeId);
    }
} 