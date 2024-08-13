package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.ItemDAO;
import ar.edu.unq.spring.persistence.PersonajeDAO;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class InventarioServiceImp implements InventarioService {

    @Autowired
    private PersonajeDAO personajeDAO;

    @Autowired
    private ItemDAO itemDAO;

    @Override
    public Item getItem(Long itemId) {
        return itemDAO.findById(itemId).orElse(null);
    }

    @Override
    public Collection<Item> allItems() {
        return itemDAO.findAll();
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
        Optional<Personaje> personaje = personajeDAO.findById(personajeId);
        Optional<Item> item = itemDAO.findById(itemId);
        if (personaje.isPresent() && item.isPresent()) {
            personaje.get().recoger(item.get());
            personajeDAO.save(personaje.get());
        }
    }

    @Override
    public Collection<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso);
    }

    @Override
    public Collection<Item> getItemsPersonajesDebiles(int vida) {
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