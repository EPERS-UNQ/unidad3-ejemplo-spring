package ar.edu.unq.spring.controller


import ar.edu.unq.spring.controller.dto.ItemJsonDTO
import ar.edu.unq.spring.controller.dto.PersonajeJsonDTO
import ar.edu.unq.spring.service.InventarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/* IMPORTANTE: En este controller estamos manejando tanto personajes como items solo a modo de ejemplo.
* Recuerden que es importante dividir responsabilidades bien en nuestros objetos.
* Este controller deberia estar dividido en un personaje controller, y en un inventario controller */
@RestController
@CrossOrigin
@RequestMapping("/inventario")
class InventarioControllerREST(
    @Autowired
    private val inventarioService: InventarioService
    ) {

    @GetMapping("/personaje/{personajeId}")
    fun recuperarPersonaje(@PathVariable personajeId: Long) = PersonajeJsonDTO.desdeModelo(inventarioService.recuperarPersonaje(personajeId)!!)

    @GetMapping("/{itemId}")
    fun recuperarItem(@PathVariable itemId: Long) = ItemJsonDTO.desdeModelo(inventarioService.recuperarItem(itemId)!!)

    @GetMapping("/allItems")
    fun allItems() = inventarioService.allItems().map { item -> ItemJsonDTO.desdeModelo(item)  }

    @GetMapping("/allPersonajes")
    fun allPersonajes() = inventarioService.allPersonajes().map { personaje -> PersonajeJsonDTO.desdeModelo(personaje)  }

    @GetMapping("/heaviestItem")
    fun heaviestItem() = ItemJsonDTO.desdeModelo(inventarioService.heaviestItem())

    @GetMapping("/masPesados/{peso}")
    fun getMasPesados(@PathVariable peso: Int) = inventarioService.getMasPesados(peso).map { item -> ItemJsonDTO.desdeModelo(item)  }

    @GetMapping("/itemsPersonajesDebiles/{vida}")
    fun getItemsPersonajesDebiles(@PathVariable vida: Int) = inventarioService.getItemsPersonajesDebiles(vida).map { item -> ItemJsonDTO.desdeModelo(item)  }

    @PostMapping
    fun guardarItem(@RequestBody item: ItemJsonDTO) = inventarioService.guardarItem(item.aModelo())

    @PostMapping("/personaje")
    fun guardarPersonaje(@RequestBody personaje: PersonajeJsonDTO) = inventarioService.guardarPersonaje(personaje.aModelo())

    @PutMapping("/recoger/{personajeId}/{itemId}")
    fun recoger(@PathVariable personajeId: Long, @PathVariable itemId: Long) = inventarioService.recoger(personajeId, itemId)

}