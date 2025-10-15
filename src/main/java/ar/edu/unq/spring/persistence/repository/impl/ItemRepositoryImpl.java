package ar.edu.unq.spring.persistence.repository.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.persistence.repository.ItemRepository;
import ar.edu.unq.spring.persistence.repository.jpa.ItemDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemDAO itemDAO;

    public ItemRepositoryImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public List<Item> findAll() {
        return itemDAO.findAll();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemDAO.findById(id);
    }

    @Override
    public Item save(Item item) {
        return itemDAO.save(item);
    }

    @Override
    public void delete(Item item) {
        itemDAO.delete(item);
    }

    @Override
    public void deleteAll() {
        itemDAO.deleteAll();
    }

    @Override
    public Item findTopByOrderByPesoDesc() {
        return itemDAO.findTopByOrderByPesoDesc();
    }

    @Override
    public Set<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso);
    }

    @Override
    public Set<Item> getItemsDePersonajesDebiles(int vida) {
        return itemDAO.getItemsDePersonajesDebiles(vida);
    }
}
