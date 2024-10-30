package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.ItemDTO;
import ar.edu.unq.spring.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @CrossOrigin
@RequestMapping("/item")
public class ItemControllerREST {
    private final ItemService itemService;

    public ItemControllerREST(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public void guardarItem(@RequestBody ItemDTO item) {
        itemService.guardarItem(item.aModelo());
    }

    @GetMapping("/heaviestItem")
    public ItemDTO heaviestItem() {
        return ItemDTO.desdeModelo(itemService.heaviestItem());
    }

    @GetMapping("/masPesados/{peso}")
    public List<ItemDTO> getMasPesados(@PathVariable int peso) {
        return itemService.getMasPesados(peso).stream()
                .map(ItemDTO::desdeModelo)
                .toList();
    }

    @GetMapping("/itemsPersonajesDebiles/{vida}")
    public List<ItemDTO> getItemsPersonajesDebiles(@PathVariable int vida) {
        return itemService.getItemsPersonajesDebiles(vida).stream()
                .map(ItemDTO::desdeModelo)
                .toList();
    }

    @GetMapping("/all")
    public List<ItemDTO> allItems() {
        return itemService.allItems().stream()
                .map(ItemDTO::desdeModelo)
                .toList();
    }
}
