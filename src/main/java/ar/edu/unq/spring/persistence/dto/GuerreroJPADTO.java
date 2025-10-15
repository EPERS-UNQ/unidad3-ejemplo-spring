package ar.edu.unq.spring.persistence.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class GuerreroJPADTO extends PersonajeJPADTO {
    private Integer fuerza;

    public GuerreroJPADTO() {
    }
}