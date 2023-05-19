package ar.edu.unq.spring.persistence

import ar.edu.unq.spring.modelo.Personaje
import javax.persistence.*

@Entity(name = "Personaje")
class PersonajeJPADTO() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(nullable = false, length = 500)
    var nombre: String? = null
    var vida: Int = 0
    var pesoMaximo: Int = 0

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var inventario: MutableSet<ItemJPADTO> = HashSet()

    companion object {
        fun desdeModelo(personaje: Personaje): PersonajeJPADTO{
            val dto = PersonajeJPADTO()
            dto.vida = personaje.vida
            dto.nombre = personaje.nombre
            dto.pesoMaximo = personaje.pesoMaximo
            dto.id = personaje.id
            dto.inventario = personaje.inventario.map{i -> ItemJPADTO.desdeModelo(i, dto)}.toMutableSet()
            return dto
        }
    }

    fun aModelo() : Personaje {
        val personaje =  Personaje(nombre!!)
        personaje.vida = vida
        personaje.pesoMaximo = pesoMaximo
        personaje.inventario = inventario.map{i -> i.aModelo(personaje)}.toMutableSet()
        personaje.id = id
        return personaje
    }
}
