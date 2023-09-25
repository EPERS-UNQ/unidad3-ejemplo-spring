package ar.edu.unq.spring.modelo.exception

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje

class NombreDePersonajeRepetido(private val nombre: String) : RuntimeException() {

    override val message: String?
        get() = "El nombre de personaje [$nombre] ya esta siendo utilizado y no puede volver a crearse"

    companion object {

        private val serialVersionUID = 1L
    }

}
