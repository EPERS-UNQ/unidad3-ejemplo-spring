package ar.edu.unq.spring.persistence

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import java.util.*
import javax.persistence.*

@Entity(name = "Item")
class ItemJPADTO() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nombre: String? = null
    var peso: Int = 0

    @ManyToOne
    var owner: PersonajeJPADTO? = null

    constructor(item: Item) : this() {
        this.id = item.id
        this.nombre = item.nombre
        this.peso = item.peso
        if (item.owner != null && this.owner != null) {
            this.owner = PersonajeJPADTO(item.owner!!)
        }
    }

    constructor(item: Item, personajeJPADTO: PersonajeJPADTO): this() {
        this.owner = personajeJPADTO
        this.id = item.id
        this.nombre = item.nombre
        this.peso = item.peso
    }


    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    fun aModelo(): Item {
        val item = Item(nombre!!, peso)
        if (owner != null) {
            item.owner = owner!!.aModelo()
        }
        item.id = id
        return item
    }

    fun aModelo(personaje: Personaje): Item {
        val item = Item(nombre!!, peso)
        item.owner = personaje
        item.id = id
        return item
    }

}
