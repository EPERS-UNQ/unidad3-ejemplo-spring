package ar.edu.unq.spring.inventario.adapters.infra.persistence.entity;

import ar.edu.unq.spring.personaje.adapters.infra.persistence.entity.PersonajeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private int peso;

    @ManyToOne
    @JoinColumn(name = "personaje_id")
    private PersonajeEntity dueño;
} 