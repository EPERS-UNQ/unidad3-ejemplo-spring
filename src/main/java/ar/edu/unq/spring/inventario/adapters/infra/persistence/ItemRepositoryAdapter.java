package ar.edu.unq.spring.inventario.adapters.infra.persistence;

import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.inventario.domain.model.ItemId;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;
import ar.edu.unq.spring.inventario.ports.infra.ItemRepository;
import ar.edu.unq.spring.inventario.adapters.infra.persistence.entity.ItemEntity;
import ar.edu.unq.spring.inventario.adapters.infra.persistence.mapper.ItemMapper;
import ar.edu.unq.spring.inventario.adapters.infra.persistence.repository.SpringDataItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryAdapter implements ItemRepository {

    private final SpringDataItemRepository springDataItemRepository;
    private final ItemMapper itemMapper;

    public ItemRepositoryAdapter(
            SpringDataItemRepository springDataItemRepository,
            ItemMapper itemMapper) {
        this.springDataItemRepository = springDataItemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public List<Item> findAll() {
        return springDataItemRepository.findAll().stream()
                .map(itemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(ItemId id) {
        return springDataItemRepository.findById(id.value())
                .map(itemMapper::toDomain);
    }

    @Override
    public Item save(Item item) {
        ItemEntity itemEntity = itemMapper.toEntity(item, null);
        ItemEntity savedItemEntity = springDataItemRepository.save(itemEntity);
        return itemMapper.toDomain(savedItemEntity);
    }

    @Override
    public List<Item> findByPersonajeId(PersonajeId personajeId) {
        return springDataItemRepository.findByDueñoId(personajeId.value()).stream()
                .map(itemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataItemRepository.deleteAll();
    }
} 