package ar.edu.unq.spring.persistence.dto;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "Item")
public class ItemJPADTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int peso;

    @ManyToOne
    private PersonajeJPADTO owner;

    public static ItemJPADTO desdeModelo(Item item) {
        ItemJPADTO dto = new ItemJPADTO();
        dto.id = item.getId();
        dto.nombre = item.getNombre();
        dto.peso = item.getPeso();
        if (item.getOwner() != null) {
            dto.owner = PersonajeJPADTO.desdeModelo(item.getOwner());
        }
        return dto;
    }

    public static ItemJPADTO desdeModelo(Item item, PersonajeJPADTO personajeJPADTO) {
        ItemJPADTO dto = new ItemJPADTO();
        dto.id = item.getId();
        dto.nombre = item.getNombre();
        dto.peso = item.getPeso();
        dto.owner = personajeJPADTO;
        return dto;
    }

    public Item aModelo() {
        Item item = new Item(nombre, peso);
        if (owner != null) {
            item.setOwner(owner.aModelo());
        }
        item.setId(id);
        return item;
    }

    public Item aModelo(Personaje personaje) {
        Item item = new Item(nombre, peso);
        item.setOwner(personaje);
        item.setId(id);
        return item;
    }
}