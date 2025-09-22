package ar.edu.unq.spring.persistence.dto;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
public class ItemJPADTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private int peso;

    @ManyToOne
    private PersonajeJPADTO owner;

    public ItemJPADTO(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
    }


    public Item aModelo() {
        Item item = new Item(this.nombre, this.peso);
        item.setId(this.id);

        if (this.owner != null) {
            Personaje personaje = new Personaje(this.owner.getNombre(), this.owner.getVida(), this.owner.getPesoMaximo());
            personaje.setId(this.owner.getId());
            item.setOwner(personaje);
        }

        return item;
    }

    public static ItemJPADTO desdeModelo(Item item) {
        ItemJPADTO dto = new ItemJPADTO(item.getNombre(), item.getPeso());
        dto.setId(item.getId());

        if (item.getOwner() != null) {
            PersonajeJPADTO ownerDTO = new PersonajeJPADTO(item.getOwner().getNombre(),
                    item.getOwner().getVida(),
                    item.getOwner().getPesoMaximo());
            ownerDTO.setId(item.getOwner().getId());
            dto.setOwner(ownerDTO);
        }

        return dto;
    }

}