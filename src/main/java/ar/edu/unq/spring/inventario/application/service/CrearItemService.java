package ar.edu.unq.spring.inventario.application.service;

import ar.edu.unq.spring.inventario.ports.api.CrearItemUseCase;
import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.inventario.ports.infra.ItemRepository;
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