package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.clasesDePersonajes.Guerrero;
import ar.edu.unq.spring.modelo.clasesDePersonajes.Mago;
import ar.edu.unq.spring.modelo.clasesDePersonajes.Picaro;
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
    public final String tipo;
    public final int estadisticaDeClase;
    public final Set<ItemDTO> inventario;

    public PersonajeDTO(Long id, String nombre, int vida, int pesoMaximo, String tipo, int estadisticaDeClase, Set<ItemDTO> inventario) {
        this.id = id;
        this.nombre = nombre;
        this.vida = vida;
        this.pesoMaximo = pesoMaximo;
        this.tipo = tipo;
        this.estadisticaDeClase = estadisticaDeClase;
        this.inventario = inventario;
    }

    public static PersonajeDTO desdeModelo(Personaje personaje) {
        String tipo;
        int estadisticaDeClase;

        if (personaje instanceof Guerrero) {
            tipo = "Guerrero";
            estadisticaDeClase = ((Guerrero) personaje).getFuerza();
        } else if (personaje instanceof Mago) {
            tipo = "Mago";
            estadisticaDeClase = ((Mago) personaje).getMana();
        } else if (personaje instanceof Picaro) {
            tipo = "Picaro";
            estadisticaDeClase = ((Picaro) personaje).getSigilo();
        } else {
            throw new IllegalArgumentException("No existe ese tipo de personaje");
        }

        Set<ItemDTO> inventarioDTO = personaje.getInventario() != null ?
                new HashSet<>(personaje.getInventario().stream()
                        .map(ItemDTO::desdeModelo)
                        .toList()) : null;

        return new PersonajeDTO(
                personaje.getId(),
                personaje.getNombre(),
                personaje.getVida(),
                personaje.getPesoMaximo(),
                tipo,
                estadisticaDeClase,
                inventarioDTO
        );
    }

    public Personaje aModelo() {
        Personaje personaje;
        switch (tipo) {
            case "Guerrero":
                personaje = new Guerrero();
                ((Guerrero) personaje).setFuerza(estadisticaDeClase);
                break;
            case "Mago":
                personaje = new Mago();
                ((Mago) personaje).setMana(estadisticaDeClase);
                break;
            case "Picaro":
                personaje = new Picaro();
                ((Picaro) personaje).setSigilo(estadisticaDeClase);
                break;
            default:
                throw new IllegalArgumentException("No existe ese tipo de personaje");
        }

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
