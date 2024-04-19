package ar.edu.unq.spring.controller

import ar.edu.unq.spring.controller.dto.ItemDTO
import ar.edu.unq.spring.controller.dto.PersonajeDTO
import ar.edu.unq.spring.service.interfaces.InventarioService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/inventario")
class InventarioControllerREST(private val inventarioService: InventarioService) {

    @GetMapping("/allItems")
    fun allItems() = inventarioService.allItems().map { item -> ItemDTO.desdeModelo(item)  }

    @GetMapping("/heaviestItem")
    fun heaviestItem() = ItemDTO.desdeModelo(inventarioService.heaviestItem())

    @GetMapping("/masPesados/{peso}")
    fun getMasPesados(@PathVariable peso: Int) = inventarioService.getMasPesados(peso).map { item -> ItemDTO.desdeModelo(item)  }

    @GetMapping("/itemsPersonajesDebiles/{vida}")
    fun getItemsPersonajesDebiles(@PathVariable vida: Int) = inventarioService.getItemsPersonajesDebiles(vida).map { item -> ItemDTO.desdeModelo(item)  }

    @PostMapping
    fun guardarItem(@RequestBody item: ItemDTO) = inventarioService.guardarItem(item.aModelo())

    @PutMapping("/recoger/{personajeId}/{itemId}")
    fun recoger(@PathVariable personajeId: Long, @PathVariable itemId: Long) = inventarioService.recoger(personajeId, itemId)

}