package ar.edu.unq.spring.modelo;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Guerrero extends Personaje {

    private Integer fuerza;

    public Guerrero(String nombre, int vida, int pesoMaximo, Integer fuerza) {
        super(nombre, vida, pesoMaximo);
        this.fuerza = fuerza;
    }

}