package ar.edu.unq.spring.controller


import ar.edu.unq.spring.controller.dto.ItemDTO
import ar.edu.unq.spring.controller.dto.PersonajeDTO
import ar.edu.unq.spring.service.InventarioService
import org.springframework.data.jpa.repository.Query
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam


/* IMPORTANTE: En este service estamos manejando tanto personajes como items solo a modo de ejemplo.
* Recuerden que es importante dividir responsabilidades bien en nuestros objetos.
* Este controller deberia estar dividido en un personaje controller, y en un inventario controller */
@RestController
@CrossOrigin
@RequestMapping("/inventario")
class InventarioControllerREST(private val inventarioService: InventarioService) {

    @GetMapping("/{personajeId}")
    fun recuperarPersonaje(@PathVariable personajeId: Long): PersonajeDTO {
        return PersonajeDTO.desdeModelo(inventarioService.recuperarPersonaje(personajeId)!!)
    }

    @GetMapping("/personaje/nombre/{nombre}")
    fun recuperarPersonajePorNombre(@PathVariable nombre: String):PersonajeDTO  {
        val personaje = inventarioService.recuperarPersonajePorNombre(nombre)!!
        return PersonajeDTO.desdeModelo(personaje)
    }

    @GetMapping("/allItems")
    fun allItems() = inventarioService.allItems().map { item -> ItemDTO.desdeModelo(item)  }

    @GetMapping("/allPersonajes")
    fun allPersonajes() = inventarioService.allPersonajes().map { personaje -> PersonajeDTO.desdeModelo(personaje)  }

    @GetMapping("/personajes")
    fun personajesPaginados(@RequestParam("size", defaultValue = "10") size:Int,
                            @RequestParam("page", defaultValue = "0") page:Int): List<PersonajeDTO> {
        return inventarioService.personajesPaginados(size, page)
            .map { personaje -> PersonajeDTO.desdeModelo(personaje)  }
    }


    @GetMapping("/heaviestItem")
    fun heaviestItem() = ItemDTO.desdeModelo(inventarioService.heaviestItem())

    @GetMapping("/masPesados/{peso}")
    fun getMasPesados(@PathVariable peso: Int) = inventarioService.getMasPesados(peso).map { item -> ItemDTO.desdeModelo(item)  }

    @GetMapping("/itemsPersonajesDebiles/{vida}")
    fun getItemsPersonajesDebiles(@PathVariable vida: Int) = inventarioService.getItemsPersonajesDebiles(vida).map { item -> ItemDTO.desdeModelo(item)  }

    @PostMapping
    fun guardarItem(@RequestBody item: ItemDTO) = inventarioService.guardarItem(item.aModelo())

    @PostMapping("/personaje")
    fun guardarPersonaje(@RequestBody personaje: PersonajeDTO) = inventarioService.guardarPersonaje(personaje.aModelo())

    @PutMapping("/recoger/{personajeId}/{itemId}")
    fun recoger(@PathVariable personajeId: Long, @PathVariable itemId: Long) = inventarioService.recoger(personajeId, itemId)

}