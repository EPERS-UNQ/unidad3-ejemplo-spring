package ar.edu.unq.spring.controller.dto

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje

class ItemJsonDTO (){
    var id: Long? = null
    var nombre : String?  = null
    var peso: Int  = 0
    var ownerId: Long?  = null

    companion object {
        fun desdeModelo(item: Item): ItemJsonDTO {
            val dto = ItemJsonDTO ()
            dto.id = item.id
            dto.nombre = item.nombre
            dto.peso = item.peso
            dto.ownerId = if (item.owner != null) item.owner?.id else null
            return dto
        }
    }

    fun aModelo(personaje: Personaje): Item {
        val item = aModelo()
        item.owner = personaje
        return item
    }

    fun aModelo(): Item {
        val item = Item(this.nombre!!, this.peso)
        item.id = this.id
        return item
    }
}