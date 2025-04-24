package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.EliminarTodosLosItemsUseCase;
import ar.edu.unq.spring.application.port.input.EliminarTodosLosPersonajesUseCase;
import ar.edu.unq.spring.domain.repository.ItemRepository;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
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