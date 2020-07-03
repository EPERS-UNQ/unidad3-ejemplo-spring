package ar.edu.unq.spring.modelo.exception

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje

class MuchoPesoException(private val personaje: Personaje, private val item: Item) : RuntimeException() {

    override val message: String?
        get() = "El personaje [$personaje] no puede recoger [$item] porque cagar mucho peso ya"

    companion object {

        private val serialVersionUID = 1L
    }

}
