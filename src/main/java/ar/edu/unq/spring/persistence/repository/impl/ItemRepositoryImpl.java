package ar.edu.unq.spring.persistence.repository.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.persistence.repository.jpa.ItemDAO;
import ar.edu.unq.spring.persistence.dto.ItemJPADTO;
import ar.edu.unq.spring.persistence.repository.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemDAO itemDAO;

    public ItemRepositoryImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public List<Item> findAll() {
        return itemDAO.findAll().stream()
                .map(ItemJPADTO::aModelo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemDAO.findById(id)
                .map(ItemJPADTO::aModelo);
    }

    @Override
    public Item save(Item item) {
        ItemJPADTO dto = ItemJPADTO.desdeModelo(item);
        ItemJPADTO savedDTO = itemDAO.save(dto);
        item.setId(savedDTO.getId());
        return item;
    }

    @Override
    public void delete(Item item) {
        ItemJPADTO dto = ItemJPADTO.desdeModelo(item);
        itemDAO.delete(dto);
    }

    @Override
    public void deleteAll() {
        itemDAO.deleteAll();
    }

    @Override
    public Item findTopByOrderByPesoDesc() {
        ItemJPADTO dto = itemDAO.findTopByOrderByPesoDesc();
        return dto != null ? dto.aModelo() : null;
    }

    @Override
    public Set<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso).stream()
                .map(ItemJPADTO::aModelo)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Item> getItemsDePersonajesDebiles(int vida) {
        return itemDAO.getItemsDePersonajesDebiles(vida).stream()
                .map(ItemJPADTO::aModelo)
                .collect(Collectors.toSet());
    }
}
