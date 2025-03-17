package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.CrearItemUseCase;
import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CrearItemService implements CrearItemUseCase {

    private final ItemRepository itemRepository;

    public CrearItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item ejecutar(Item item) {
        return itemRepository.save(item);
    }
} 