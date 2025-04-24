package ar.edu.unq.spring.personaje.adapters.infra.persistence.mapper;

import ar.edu.unq.spring.personaje.adapters.infra.persistence.entity.PersonajeEntity;
import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;
import ar.edu.unq.spring.inventario.adapters.infra.persistence.mapper.ItemMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class PersonajeMapper {

    private final ItemMapper itemMapper;

    public PersonajeMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public Personaje toDomain(PersonajeEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Personaje personaje = new Personaje(entity.getNombre(), entity.getVida(), entity.getPesoMaximo());
        personaje.setId(PersonajeId.of(entity.getId()));
        
        // Convertir los items
        if (entity.getInventario() != null) {
            entity.getInventario().stream()
                    .map(itemMapper::toDomain)
                    .forEach(personaje::recoger);
        }
        
        return personaje;
    }

    public PersonajeEntity toEntity(Personaje domain) {
        if (domain == null) {
            return null;
        }
        
        PersonajeEntity entity = new PersonajeEntity();
        
        if (domain.getId() != null) {
            entity.setId(domain.getId().value());
        }
        
        entity.setNombre(domain.getNombre());
        entity.setVida(domain.getVida());
        entity.setPesoMaximo(domain.getPesoMaximo());
        
        // Los items se manejan por separado para evitar referencias circulares
        entity.setInventario(new HashSet<>());
        
        return entity;
    }
} 