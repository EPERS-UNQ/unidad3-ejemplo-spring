package ar.edu.unq.spring.persistence.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class MagoJPADTO extends PersonajeJPADTO {

    private Integer mana;

    public MagoJPADTO() {}
}