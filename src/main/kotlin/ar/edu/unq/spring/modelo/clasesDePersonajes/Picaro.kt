package ar.edu.unq.spring.modelo.clasesDePersonajes

import ar.edu.unq.spring.modelo.Personaje
import javax.persistence.Entity

@Entity
class Picaro() : Personaje() {
    var sigilo: Int = 0

    constructor(nombre: String) : this() {
        this.nombre = nombre
    }

}
