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

    //En esta clase tenemos dos desdeModelos y dos aModelo
    //para evitar problemas de recursion a la hora de transformar aModelo o desdeModelo

    companion object {
        fun desdeModelo(item: Item): ItemJPADTO{
            val dto = ItemJPADTO()
            dto.id = item.id
            dto.nombre = item.nombre
            dto.peso = item.peso
            if (item.owner != null) {
                dto.owner = PersonajeJPADTO.desdeModelo(item.owner!!)
            }
            return dto
        }

        fun desdeModelo(item: Item, personajeJPADTO: PersonajeJPADTO): ItemJPADTO{
            val dto = ItemJPADTO()
            dto.id = item.id
            dto.nombre = item.nombre
            dto.peso = item.peso
            dto.owner = personajeJPADTO
            return dto
        }
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

    override fun hashCode(): Int {
        return Objects.hash(id)
    }


}
