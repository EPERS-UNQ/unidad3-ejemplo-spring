package ar.edu.unq.spring.modelo

class Item() {

    var id: Long? = null
    var nombre: String? = null
    var peso: Int = 0
    var owner: Personaje? = null

    constructor(nombre: String, peso: Int):this() {
        this.nombre = nombre
        this.peso = peso
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val item = o as Item?
        return id == item!!.id
    }
    
    override fun toString(): String {
        return nombre!!
    }
}
