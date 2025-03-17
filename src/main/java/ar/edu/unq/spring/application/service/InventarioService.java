package ar.edu.unq.spring.application.service;

import ar.edu.unq.spring.application.port.input.InventarioUseCase;
import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.ItemId;
import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.domain.repository.ItemRepository;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventarioService implements InventarioUseCase {

    private final ItemRepository itemRepository;
    private final PersonajeRepository personajeRepository;

    public InventarioService(ItemRepository itemRepository, PersonajeRepository personajeRepository) {
        this.itemRepository = itemRepository;
        this.personajeRepository = personajeRepository;
    }

    @Override
    public List<Item> obtenerInventarioDePersonaje(PersonajeId personajeId) {
        if (!personajeRepository.findById(personajeId).isPresent()) {
            throw new NoSuchElementException("Personaje no encontrado con id: " + personajeId.value());
        }
        return itemRepository.findByPersonajeId(personajeId);
    }

    @Override
    public void agregarItemAPersonaje(PersonajeId personajeId, Item item) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new NoSuchElementException("Personaje no encontrado con id: " + personajeId.value()));
        
        personaje.recoger(item);
        personajeRepository.save(personaje);
    }

    @Override
    public Item obtenerItemPorId(ItemId itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item no encontrado con id: " + itemId.value()));
    }

    
    @Override
    public Item crearItem(Item item) {
        return itemRepository.save(item);
    }
} 