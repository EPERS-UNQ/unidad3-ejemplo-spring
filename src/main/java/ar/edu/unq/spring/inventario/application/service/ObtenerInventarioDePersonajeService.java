package ar.edu.unq.spring.inventario.application.service;

import ar.edu.unq.spring.inventario.ports.api.ObtenerInventarioDePersonajeUseCase;
import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;
import ar.edu.unq.spring.inventario.ports.infra.ItemRepository;
import ar.edu.unq.spring.personaje.ports.infra.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ObtenerInventarioDePersonajeService implements ObtenerInventarioDePersonajeUseCase {

    private final ItemRepository itemRepository;
    private final PersonajeRepository personajeRepository;

    public ObtenerInventarioDePersonajeService(ItemRepository itemRepository, PersonajeRepository personajeRepository) {
        this.itemRepository = itemRepository;
        this.personajeRepository = personajeRepository;
    }

    @Override
    public List<Item> ejecutar(PersonajeId personajeId) {
        return personajeRepository.findById(personajeId)
                .map(Personaje::getInventario)
                .orElse(Collections.emptyList());
    }
} 