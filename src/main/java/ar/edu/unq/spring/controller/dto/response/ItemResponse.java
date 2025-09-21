package ar.edu.unq.spring.controller.dto.response;

import ar.edu.unq.spring.modelo.Item;


public record ItemResponse(Long id, String nombre, int peso, Long ownerId, String ownerNombre) {

    public static ItemResponse desdeModelo(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getNombre(),
                item.getPeso(),
                item.getOwner() != null ? item.getOwner().getId() : null,
                item.getOwner() != null ? item.getOwner().getNombre() : null
        );
    }
}
