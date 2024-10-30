package ar.edu.unq.spring.modelo.clasesDePersonajes;

import ar.edu.unq.spring.modelo.Personaje;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Guerrero extends Personaje {

    private int fuerza = 0;

    public Guerrero() {}

    public Guerrero(String nombre) {
        this.setNombre(nombre);
    }
}
