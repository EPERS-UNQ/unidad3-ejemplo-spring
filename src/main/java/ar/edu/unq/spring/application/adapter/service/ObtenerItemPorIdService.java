package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.ObtenerItemPorIdUseCase;
import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.ItemId;
import ar.edu.unq.spring.domain.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class ObtenerItemPorIdService implements ObtenerItemPorIdUseCase {

    private final ItemRepository itemRepository;

    public ObtenerItemPorIdService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item ejecutar(ItemId itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item no encontrado con id: " + itemId.value()));
    }
} 