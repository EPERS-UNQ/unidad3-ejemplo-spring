package ar.edu.unq.spring.infrastructure.adapter.persistence;

import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.domain.repository.PersonajeRepository;
import ar.edu.unq.spring.infrastructure.adapter.persistence.entity.ItemEntity;
import ar.edu.unq.spring.infrastructure.adapter.persistence.entity.PersonajeEntity;
import ar.edu.unq.spring.infrastructure.adapter.persistence.mapper.ItemMapper;
import ar.edu.unq.spring.infrastructure.adapter.persistence.mapper.PersonajeMapper;
import ar.edu.unq.spring.infrastructure.adapter.persistence.repository.SpringDataPersonajeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PersonajeRepositoryAdapter implements PersonajeRepository {

    private final SpringDataPersonajeRepository springDataPersonajeRepository;
    private final PersonajeMapper personajeMapper;
    private final ItemMapper itemMapper;

    public PersonajeRepositoryAdapter(
            SpringDataPersonajeRepository springDataPersonajeRepository,
            PersonajeMapper personajeMapper,
            ItemMapper itemMapper) {
        this.springDataPersonajeRepository = springDataPersonajeRepository;
        this.personajeMapper = personajeMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public List<Personaje> findAll() {
        return springDataPersonajeRepository.findAll().stream()
                .map(personajeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Personaje> findById(PersonajeId id) {
        return springDataPersonajeRepository.findById(id.value())
                .map(personajeMapper::toDomain);
    }

    @Override
    public Personaje save(Personaje personaje) {
        PersonajeEntity personajeEntity = personajeMapper.toEntity(personaje);
        MapearItems(personaje, personajeEntity);
        PersonajeEntity savedPersonajeEntity = springDataPersonajeRepository.save(personajeEntity);

        return personajeMapper.toDomain(savedPersonajeEntity);
    }

    private void MapearItems(Personaje personaje, PersonajeEntity personajeEntity) {
        if (personaje.getInventario() != null && !personaje.getInventario().isEmpty()) {
            personajeEntity.setInventario(
                    personaje.getInventario().stream()
                            .map(item -> itemMapper.toEntity(item, personajeEntity))
                            .collect(Collectors.toSet())
            );

        }
    }

    @Override
    public void deleteAll() {
        springDataPersonajeRepository.deleteAll();
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return springDataPersonajeRepository.existsByNombre(nombre);
    }
} 