package ar.edu.unq.spring.persistence.repository.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.persistence.dto.ItemJPADTO;
import ar.edu.unq.spring.persistence.repository.ItemRepository;
import ar.edu.unq.spring.persistence.repository.jpa.ItemDAO;
import ar.edu.unq.spring.persistence.mapper.ItemMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemDAO itemDAO;
    private final ItemMapper itemMapper;

    public ItemRepositoryImpl(ItemDAO itemDAO,
                              ItemMapper itemMapper) {
        this.itemDAO = itemDAO;
        this.itemMapper = itemMapper;
    }

    @Override
    public List<Item> findAll() {
        return itemDAO.findAll().stream()
                .map(itemMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemDAO.findById(id)
                .map(itemMapper::toEntity);
    }

    @Override
    public Item save(Item item) {
        ItemJPADTO dto = itemMapper.toDTO(item);
        ItemJPADTO savedDTO = itemDAO.save(dto);
        item.setId(savedDTO.getId());
        return item;
    }

    @Override
    public void delete(Item item) {
        ItemJPADTO dto = itemMapper.toDTO(item);
        itemDAO.delete(dto);
    }

    @Override
    public void deleteAll() {
        itemDAO.deleteAll();
    }

    @Override
    public Item findTopByOrderByPesoDesc() {
        ItemJPADTO dto = itemDAO.findTopByOrderByPesoDesc();
        return itemMapper.toEntity(dto);
    }

    @Override
    public Set<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso).stream()
                .map(itemMapper::toEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Item> getItemsDePersonajesDebiles(int vida) {
        return itemDAO.getItemsDePersonajesDebiles(vida).stream()
                .map(itemMapper::toEntity)
                .collect(Collectors.toSet());
    }
}
