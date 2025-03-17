package ar.edu.unq.spring.infrastructure.adapter.rest.dto;

import ar.edu.unq.spring.domain.model.Personaje;

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