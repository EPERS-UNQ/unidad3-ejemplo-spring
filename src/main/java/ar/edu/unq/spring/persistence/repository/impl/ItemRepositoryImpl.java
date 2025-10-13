package ar.edu.unq.spring.persistence.repository.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.persistence.dto.ItemJPADTO;
import ar.edu.unq.spring.persistence.repository.ItemRepository;
import ar.edu.unq.spring.persistence.repository.jpa.ItemDAO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemDAO itemDAO;
    private final ModelMapper modelMapper;

    public ItemRepositoryImpl(ItemDAO itemDAO,
                              ModelMapper modelMapper) {
        this.itemDAO = itemDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Item> findAll() {
        return itemDAO.findAll().stream().map((element) -> modelMapper.map(element, Item.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemDAO.findById(id).map((element) -> modelMapper.map(element, Item.class));
    }

    @Override
    public Item save(Item item) {
        ItemJPADTO dto = modelMapper.map(item, ItemJPADTO.class);
        ItemJPADTO savedDTO = itemDAO.save(dto);
        item.setId(savedDTO.getId());
        return item;
    }

    @Override
    public void delete(Item item) {
        ItemJPADTO dto = modelMapper.map(item, ItemJPADTO.class);
        itemDAO.delete(dto);
    }

    @Override
    public void deleteAll() {
        itemDAO.deleteAll();
    }

    @Override
    public Item findTopByOrderByPesoDesc() {
        ItemJPADTO dto = itemDAO.findTopByOrderByPesoDesc();
        return modelMapper.map(dto, Item.class);
    }

    @Override
    public Set<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso).stream().map((element) -> modelMapper.map(element, Item.class)).collect(Collectors.toSet());
    }

    @Override
    public Set<Item> getItemsDePersonajesDebiles(int vida) {
        return itemDAO.getItemsDePersonajesDebiles(vida).stream().map((element) -> modelMapper.map(element, Item.class)).collect(Collectors.toSet());
    }
}
