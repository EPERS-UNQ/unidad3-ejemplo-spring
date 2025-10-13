package ar.edu.unq.spring.persistence.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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


}