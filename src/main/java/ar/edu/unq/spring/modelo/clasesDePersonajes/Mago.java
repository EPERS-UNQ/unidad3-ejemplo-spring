package ar.edu.unq.spring.modelo.clasesDePersonajes;

import ar.edu.unq.spring.modelo.Personaje;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Mago extends Personaje {

    private int mana = 0;

    public Mago() {}

    public Mago(String nombre) {
        this.setNombre(nombre);
    }
}
