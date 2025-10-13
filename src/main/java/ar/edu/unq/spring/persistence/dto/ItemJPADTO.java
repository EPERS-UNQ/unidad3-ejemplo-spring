package ar.edu.unq.spring.persistence.dto;

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

}