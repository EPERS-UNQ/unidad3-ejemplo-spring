package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.ItemDTO;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/inventario")
public class InventarioControllerREST {

    private final InventarioService inventarioService;

    public InventarioControllerREST(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/allItems")
    public List<ItemDTO> allItems() {
        return inventarioService.allItems().stream()
                .map(ItemDTO::desdeModelo)
                .collect(Collectors.toList());
    }

    @GetMapping("/heaviestItem")
    public ItemDTO heaviestItem() {
        return ItemDTO.desdeModelo(inventarioService.heaviestItem());
    }

    @GetMapping("/masPesados/{peso}")
    public List<ItemDTO> getMasPesados(@PathVariable int peso) {
        return inventarioService.getMasPesados(peso).stream()
                .map(ItemDTO::desdeModelo)
                .collect(Collectors.toList());
    }

    @GetMapping("/itemsPersonajesDebiles/{vida}")
    public List<ItemDTO> getItemsPersonajesDebiles(@PathVariable int vida) {
        return inventarioService.getItemsPersonajesDebiles(vida).stream()
                .map(ItemDTO::desdeModelo)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void guardarItem(@RequestBody ItemDTO item) {
        inventarioService.guardarItem(item.aModelo());
    }

    @PutMapping("/recoger/{personajeId}/{itemId}")
    public void recoger(@PathVariable Long personajeId, @PathVariable Long itemId) {
        inventarioService.recoger(personajeId, itemId);
    }
}