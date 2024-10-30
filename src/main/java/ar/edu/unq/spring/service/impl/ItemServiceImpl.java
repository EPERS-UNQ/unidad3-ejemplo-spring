package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.persistence.ItemDAO;
import ar.edu.unq.spring.service.ItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service @Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired private ItemDAO itemDAO;


    @Override
    public void guardarItem(Item item) {
        itemDAO.save(item);
    }

    @Override
    public Collection<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso);
    }

    @Override
    public Item heaviestItem() {
        return itemDAO.findTopByOrderByPesoDesc();
    }

    @Override
    public Collection<Item> getItemsPersonajesDebiles(int vida) {
        return itemDAO.getItemsDePersonajesDebiles(vida);
    }

    @Override
    public Collection<Item> allItems() {
        return itemDAO.findAll();
    }

    @Override
    public void clearAll() {
        itemDAO.deleteAll();
    }
}
