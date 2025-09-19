package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.ItemDAO;
import ar.edu.unq.spring.persistence.PersonajeDAO;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import ar.edu.unq.spring.service.interfaces.Randomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {
    private final PersonajeDAO personajeDAO;
    private final ItemDAO itemDAO;
    private final Randomizer randomizer;
    public InventarioServiceImpl(PersonajeDAO personajeDAO, ItemDAO itemDAO, @Qualifier("FakeRandomizer") Randomizer randomizer) {
        this.personajeDAO = personajeDAO;
        this.itemDAO = itemDAO;
        this.randomizer = randomizer;
    }

    @Override
    public Optional<Item> getItem(Long itemId) {
        return itemDAO.findById(itemId);
    }

    @Override
    public Set<Item> allItems() {
        return Set.copyOf(itemDAO.findAll());
    }

    @Override
    public Item heaviestItem() {
        return itemDAO.findTopByOrderByPesoDesc();
    }

    @Override
    public Item guardarItem(Item item) {
        return itemDAO.save(item);
    }

    @Override
    public void recoger(Long personajeId, Long itemId) {
        Personaje personaje = personajeDAO.findById(personajeId)
                .orElseThrow(() -> new NoSuchElementException("Personaje not found with id: " + personajeId));

        Item item = itemDAO.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId));

        var random = randomizer.getRandom();
        if (random > 50) {
            throw new RuntimeException("No es tu día pibe");
        }

        personaje.recoger(item);
        personajeDAO.save(personaje);
    }

    @Override
    public Set<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso);
    }

    @Override
    public Set<Item> getItemsPersonajesDebiles(int vida) {
        return itemDAO.getItemsDePersonajesDebiles(vida);
    }

    @Override
    public void clearAll() {
        itemDAO.deleteAll();
    }

    @Override
    public void deleteItem(Item item) {
        itemDAO.delete(item);
    }
}