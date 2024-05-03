package ar.edu.unq.spring.modelo

import ar.edu.unq.spring.modelo.exception.MuchoPesoException
import javax.persistence.*
import kotlin.collections.HashSet

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Personaje() {

    @Id
    @GeneratedValue()
    var id: Long? = null
    @Column(nullable = false, length = 500)
    var nombre: String? = null
    var vida: Int = 0
    var pesoMaximo: Int = 0

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var inventario: MutableSet<Item> = HashSet()

    val pesoActual: Int
        get() = inventario.sumOf { it.peso }

    constructor(nombre: String) : this() {
        this.nombre = nombre
    }

    fun recoger(item: Item) {
        val pesoActual = this.pesoActual
        if (pesoActual + item.peso > this.pesoMaximo) {
            throw MuchoPesoException(this, item)
        }

        this.inventario.add(item)
        item.owner = this
    }

    override fun toString(): String {
        return nombre!!
    }

}
