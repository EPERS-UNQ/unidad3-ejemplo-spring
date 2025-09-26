package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.request.CrearItemRequest;
import ar.edu.unq.spring.controller.dto.response.ItemResponse;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/inventario")
final public class InventarioControllerREST {
    private final InventarioService inventarioService;
    public InventarioControllerREST(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public Set<ItemResponse> obtenerTodosLosItems() {
        return inventarioService.allItems().stream()
                .map(ItemResponse::desdeModelo)
                .collect(Collectors.toSet());
    }

    @GetMapping("/mas-pesado")
    public ItemResponse getItemMasPesado() {
        return ItemResponse.desdeModelo(inventarioService.heaviestItem());
    }

    @GetMapping("/mas-pesados")
    public Set<ItemResponse> getItemsMasPesados(@RequestParam int pesoMinimo) {
        return inventarioService.getMasPesados(pesoMinimo).stream()
                .map(ItemResponse::desdeModelo)
                .collect(Collectors.toSet());
    }

    @GetMapping("/personajes-debiles")
    public Set<ItemResponse> getItemsDePersonajesDebiles(@RequestParam int vidaMaxima) {
        return inventarioService.getItemsPersonajesDebiles(vidaMaxima).stream()
                .map(ItemResponse::desdeModelo)
                .collect(Collectors.toSet());
    }

    @PostMapping
    public void crearItem(@RequestBody CrearItemRequest request) {
        inventarioService.guardarItem(request.aModelo());
    }

    @PostMapping("/{itemId}/recoger/personaje/{personajeId}")
    public void recogerItem(@PathVariable Long itemId, @PathVariable Long personajeId) {
        inventarioService.recoger(personajeId, itemId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId) {
        inventarioService.deleteById(itemId);
    }
}