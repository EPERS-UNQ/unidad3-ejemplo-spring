package ar.edu.unq.spring.personaje.adapters.api.rest.dto;

import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.inventario.adapters.api.rest.dto.ItemPresentation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record PersonajePresentation(Long id, String nombre, int vida, int pesoMaximo, Set<ItemPresentation> inventario) {

    public static PersonajePresentation fromDomain(Personaje personaje) {
        return new PersonajePresentation(
                personaje.getId() != null ? personaje.getId().value() : null,
                personaje.getNombre(),
                personaje.getVida(),
                personaje.getPesoMaximo(),
                personaje.getInventario().stream()
                        .map(ItemPresentation::fromDomain)
                        .collect(Collectors.toCollection(HashSet::new))
        );
    }
} 