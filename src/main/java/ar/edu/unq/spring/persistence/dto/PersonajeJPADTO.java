package ar.edu.unq.spring.persistence.dto;

import ar.edu.unq.spring.modelo.Personaje;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Entity(name = "Personaje")
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
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    private Set<ItemJPADTO> inventario = new HashSet<>();

    public static PersonajeJPADTO desdeModelo(Personaje personaje) {
        PersonajeJPADTO dto = new PersonajeJPADTO();
        dto.vida = personaje.getVida();
        dto.nombre = personaje.getNombre();
        dto.pesoMaximo = personaje.getPesoMaximo();
        dto.id = personaje.getId();
        dto.inventario = personaje.getInventario().stream()
                .map(item -> ItemJPADTO.desdeModelo(item, dto))
                .collect(Collectors.toSet());
        return dto;
    }

    public Personaje aModelo() {
        Personaje personaje = new Personaje(nombre, vida, pesoMaximo);
        personaje.setInventario(inventario.stream()
                .map(item -> item.aModelo(personaje))
                .collect(Collectors.toSet()));
        personaje.setId(id);
        return personaje;
    }
}