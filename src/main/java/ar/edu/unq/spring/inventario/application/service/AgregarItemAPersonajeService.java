package ar.edu.unq.spring.inventario.application.service;

import ar.edu.unq.spring.inventario.ports.api.AgregarItemAPersonajeUseCase;
import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.inventario.domain.model.ItemId;
import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;
import ar.edu.unq.spring.inventario.ports.infra.ItemRepository;
import ar.edu.unq.spring.personaje.ports.infra.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AgregarItemAPersonajeService implements AgregarItemAPersonajeUseCase {

    private final ItemRepository itemRepository;
    private final PersonajeRepository personajeRepository;

    public AgregarItemAPersonajeService(ItemRepository itemRepository, PersonajeRepository personajeRepository) {
        this.itemRepository = itemRepository;
        this.personajeRepository = personajeRepository;
    }

    @Override
    public void ejecutar(ItemId itemId, PersonajeId personajeId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado con id " + itemId.value()));
        
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new RuntimeException("Personaje no encontrado con id " + personajeId.value()));
        
        personaje.recoger(item);
        personajeRepository.save(personaje);
    }
} 