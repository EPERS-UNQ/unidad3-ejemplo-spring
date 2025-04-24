package ar.edu.unq.spring.inventario.application.service;

import ar.edu.unq.spring.inventario.ports.api.EliminarTodosLosItemsUseCase;
import ar.edu.unq.spring.inventario.ports.infra.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EliminarTodosLosItemsService implements EliminarTodosLosItemsUseCase {

    private final ItemRepository itemRepository;

    public EliminarTodosLosItemsService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void ejecutar() {
        itemRepository.deleteAll();
    }
} 