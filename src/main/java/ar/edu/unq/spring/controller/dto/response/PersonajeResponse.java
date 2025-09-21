package ar.edu.unq.spring.controller.dto.response;

import ar.edu.unq.spring.modelo.Personaje;

import java.util.Set;
import java.util.stream.Collectors;


public record PersonajeResponse(
        Long id, 
        String nombre, 
        int vida, 
        int pesoMaximo, 
        int pesoActual,
        Set<ItemResponse> inventario
) {

    public static PersonajeResponse desdeModelo(Personaje personaje) {
        return new PersonajeResponse(
                personaje.getId(),
                personaje.getNombre(),
                personaje.getVida(),
                personaje.getPesoMaximo(),
                personaje.getPesoActual(),
                personaje.getInventario().stream()
                        .map(ItemResponse::desdeModelo)
                        .collect(Collectors.toSet())
        );
    }
}
