package ar.edu.unq.spring.infrastructure.adapter.rest.dto;

import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.PersonajeId;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record PersonajeDTO(Long id, String nombre, int vida, int pesoMaximo, Set<ItemDTO> inventario) {

    public static PersonajeDTO fromDomain(Personaje personaje) {
        return new PersonajeDTO(
                personaje.getId() != null ? personaje.getId().value() : null,
                personaje.getNombre(),
                personaje.getVida(),
                personaje.getPesoMaximo(),
                personaje.getInventario().stream()
                        .map(ItemDTO::fromDomain)
                        .collect(Collectors.toCollection(HashSet::new))
        );
    }

    public Personaje toDomain() {
        Personaje personaje = new Personaje(this.nombre, this.vida, this.pesoMaximo);
        
        if (this.id != null) {
            personaje.setId(PersonajeId.of(this.id));
        }
        
        return personaje;
    }
} 