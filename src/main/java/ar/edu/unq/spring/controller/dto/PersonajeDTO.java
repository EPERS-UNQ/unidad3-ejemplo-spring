package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Personaje;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class PersonajeDTO {

    public final Long id;
    public final String nombre;
    public final int vida;
    public final int pesoMaximo;
    public final Set<ItemDTO> inventario;

    public PersonajeDTO(Long id, String nombre, int vida, int pesoMaximo, Set<ItemDTO> inventario) {
        this.id = id;
        this.nombre = nombre;
        this.vida = vida;
        this.pesoMaximo = pesoMaximo;
        this.inventario = inventario;
    }

    public static PersonajeDTO desdeModelo(Personaje personaje) {
        Set<ItemDTO> inventarioDTO = personaje.getInventario() != null ?
                new HashSet<>(personaje.getInventario().stream()
                        .map(ItemDTO::desdeModelo)
                        .toList()) : null;

        return new PersonajeDTO(
                personaje.getId(),
                personaje.getNombre(),
                personaje.getVida(),
                personaje.getPesoMaximo(),
                inventarioDTO
        );
    }

    public Personaje aModelo() {
        Personaje personaje = new Personaje();

        personaje.setId(this.id);
        personaje.setNombre(this.nombre);
        personaje.setVida(this.vida);
        personaje.setPesoMaximo(this.pesoMaximo);
        personaje.setInventario(inventario != null ?
                new HashSet<>(inventario.stream()
                        .map(itemDTO -> itemDTO.aModelo(personaje))
                        .toList()) : new HashSet<>());

        return personaje;
    }
}
