package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.dao.ItemDAO;
import ar.edu.unq.spring.persistence.dao.PersonajeDAO;
import ar.edu.unq.spring.persistence.dto.ItemJPADTO;
import ar.edu.unq.spring.persistence.dto.PersonajeJPADTO;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {
    private final PersonajeDAO personajeDAO;
    private final ItemDAO itemDAO;
    public InventarioServiceImpl(PersonajeDAO personajeDAO, ItemDAO itemDAO) {
        this.personajeDAO = personajeDAO;
        this.itemDAO = itemDAO;
    }

    @Override
    public Item getItem(Long itemId) {
        ItemJPADTO item = itemDAO.findById(itemId).orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId));
        return item.aModelo();
    }

    @Override
    public Set<Item> allItems() {
        return Set.copyOf(itemDAO.findAll())
                .stream()
                .map(ItemJPADTO::aModelo)
                .collect(Collectors.toSet());
    }

    @Override
    public Item heaviestItem() {
        return itemDAO.findTopByOrderByPesoDesc().aModelo();
    }

    @Override
    public Long guardarItem(Item item) {
        ItemJPADTO itemJPADTO = ItemJPADTO.desdeModelo(item);
        itemDAO.save(itemJPADTO);
        item.setId(itemJPADTO.getId());
        return item.getId();
    }


    @Override
    public void recoger(Long personajeId, Long itemId) {
        Personaje personaje = personajeDAO.findById(personajeId)
                .orElseThrow(() -> new NoSuchElementException("Personaje not found with id: " + personajeId))
                .aModelo();

        Item item = itemDAO.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId))
                .aModelo();

        personaje.recoger(item);
        personajeDAO.save(PersonajeJPADTO.desdeModelo(personaje));
    }

    @Override
    public Set<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso)
                .stream()
                .map(ItemJPADTO::aModelo)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Item> getItemsPersonajesDebiles(int vida) {
        return itemDAO.getItemsDePersonajesDebiles(vida)
                .stream()
                .map(ItemJPADTO::aModelo)
                .collect(Collectors.toSet());
    }

    @Override
    public void clearAll() {
        itemDAO.deleteAll();
    }

    @Override
    public void deleteItem(Item item) {
        itemDAO.delete(ItemJPADTO.desdeModelo(item));
    }
}