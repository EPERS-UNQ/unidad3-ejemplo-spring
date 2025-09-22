package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.repository.ItemRepository;
import ar.edu.unq.spring.persistence.repository.PersonajeRepository;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {
    private final PersonajeRepository personajeRepository;
    private final ItemRepository itemRepository;

    public InventarioServiceImpl(PersonajeRepository personajeRepository, ItemRepository itemRepository) {
        this.personajeRepository = personajeRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId));
    }

    @Override
    public Set<Item> allItems() {
        return Set.copyOf(itemRepository.findAll());
    }

    @Override
    public Item heaviestItem() {
        return itemRepository.findTopByOrderByPesoDesc();
    }

    @Override
    public Item guardarItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void recoger(Long personajeId, Long itemId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new NoSuchElementException("Personaje not found with id: " + personajeId));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId));

        personaje.recoger(item);
        personajeRepository.save(personaje);
    }

    @Override
    public Set<Item> getMasPesados(int peso) {
        return itemRepository.getMasPesados(peso);
    }

    @Override
    public Set<Item> getItemsPersonajesDebiles(int vida) {
        return itemRepository.getItemsDePersonajesDebiles(vida);
    }

    @Override
    public void clearAll() {
        itemRepository.deleteAll();
    }

    @Override
    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }
}