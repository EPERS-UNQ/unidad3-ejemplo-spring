package ar.edu.unq.spring.infrastructure.adapter.persistence;

import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.ItemId;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.domain.repository.ItemRepository;
import ar.edu.unq.spring.infrastructure.adapter.persistence.entity.ItemEntity;
import ar.edu.unq.spring.infrastructure.adapter.persistence.mapper.ItemMapper;
import ar.edu.unq.spring.infrastructure.adapter.persistence.repository.SpringDataItemRepository;
import ar.edu.unq.spring.infrastructure.adapter.persistence.repository.SpringDataPersonajeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryAdapter implements ItemRepository {

    private final SpringDataItemRepository springDataItemRepository;
    private final SpringDataPersonajeRepository springDataPersonajeRepository;
    private final ItemMapper itemMapper;

    public ItemRepositoryAdapter(
            SpringDataItemRepository springDataItemRepository,
            SpringDataPersonajeRepository springDataPersonajeRepository,
            ItemMapper itemMapper) {
        this.springDataItemRepository = springDataItemRepository;
        this.springDataPersonajeRepository = springDataPersonajeRepository;
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
} 