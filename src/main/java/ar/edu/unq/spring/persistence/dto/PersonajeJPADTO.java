package ar.edu.unq.spring.persistence.dto;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
public class PersonajeJPADTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    private String nombre;
    private int vida;
    private int pesoMaximo;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<ItemJPADTO> inventario = new HashSet<>();

    public PersonajeJPADTO(String nombre, int vida, int pesoMaximo) {
        this.nombre = nombre;
        this.vida = vida;
        this.pesoMaximo = pesoMaximo;
    }


    public Personaje aModelo() {
        Personaje personaje = new Personaje(this.nombre, this.vida, this.pesoMaximo);
        personaje.setId(this.id);

        Set<Item> items = this.inventario.stream()
                .map(itemDTO -> {
                    Item item = new Item(itemDTO.getNombre(), itemDTO.getPeso());
                    item.setId(itemDTO.getId());
                    item.setOwner(personaje);
                    return item;
                })
                .collect(Collectors.toSet());

        personaje.getInventario().addAll(items);

        return personaje;
    }


    public static PersonajeJPADTO desdeModelo(Personaje personaje) {
        if (personaje == null) {
            return null;
        }

        PersonajeJPADTO dto = new PersonajeJPADTO(personaje.getNombre(), personaje.getVida(), personaje.getPesoMaximo());
        dto.setId(personaje.getId());

        Set<ItemJPADTO> inventarioDTO = personaje.getInventario().stream()
                .map(item -> {
                    ItemJPADTO itemDTO = new ItemJPADTO(item.getNombre(), item.getPeso());
                    itemDTO.setId(item.getId());
                    itemDTO.setOwner(dto);
                    return itemDTO;
                })
                .collect(Collectors.toSet());

        dto.setInventario(inventarioDTO);

        return dto;
    }

}