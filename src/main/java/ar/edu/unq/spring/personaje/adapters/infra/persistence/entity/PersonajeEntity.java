package ar.edu.unq.spring.personaje.adapters.infra.persistence.entity;

import ar.edu.unq.spring.inventario.adapters.infra.persistence.entity.ItemEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "personaje")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PersonajeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    private String nombre;
    
    private int vida;
    
    private int pesoMaximo;

    @OneToMany(
            mappedBy = "dueño",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<ItemEntity> inventario = new HashSet<>();
} 