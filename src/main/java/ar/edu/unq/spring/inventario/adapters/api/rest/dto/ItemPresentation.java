package ar.edu.unq.spring.inventario.adapters.api.rest.dto;

import ar.edu.unq.spring.inventario.domain.model.Item;

public record ItemPresentation(Long id, String nombre, int peso) {

    public static ItemPresentation fromDomain(Item item) {
        return new ItemPresentation(
                item.getId() != null ? item.getId().value() : null,
                item.getNombre(),
                item.getPeso()
        );
    }
} 