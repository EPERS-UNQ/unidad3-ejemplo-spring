package ar.edu.unq.spring.modelo

import javax.persistence.*
import java.util.Objects

@Entity
class Item() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nombre: String? = null
    var peso: Int = 0

    @ManyToOne
    var owner: Personaje? = null

    constructor(nombre: String, peso: Int):this() {
        this.nombre = nombre
        this.peso = peso
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val item = other as Item?
        return id == item!!.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return nombre!!
    }
}
