package ar.edu.unq.spring.modelo;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Mago extends Personaje {

    private Integer mana;

    public Mago(String nombre, int vida, int pesoMaximo, Integer mana) {
        super(nombre, vida, pesoMaximo);
        this.mana = mana;
    }
}