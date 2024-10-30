package ar.edu.unq.spring.modelo.clasesDePersonajes;

import ar.edu.unq.spring.modelo.Personaje;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Picaro extends Personaje {

    private int sigilo = 0;

    public Picaro() {}

    public Picaro(String nombre) {
        this.setNombre(nombre);
    }
}