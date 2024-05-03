package ar.edu.unq.spring.modelo.clasesDePersonajes

import ar.edu.unq.spring.modelo.Personaje
import javax.persistence.Entity

@Entity
class Mago() : Personaje() {
    var mana: Int = 0

    constructor(nombre: String) : this() {
        this.nombre = nombre
    }


}
