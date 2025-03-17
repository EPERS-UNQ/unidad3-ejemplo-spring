package ar.edu.unq.spring.infrastructure.adapter.rest;

import ar.edu.unq.spring.application.port.input.InventarioUseCase;
import ar.edu.unq.spring.domain.model.ItemId;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.infrastructure.adapter.rest.dto.ItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioUseCase inventarioUseCase;

    public InventarioController(InventarioUseCase inventarioUseCase) {
        this.inventarioUseCase = inventarioUseCase;
    }

    @GetMapping("/personaje/{personajeId}")
    public List<ItemDTO> getInventarioByPersonajeId(@PathVariable Long personajeId) {
        return inventarioUseCase.obtenerInventarioDePersonaje(PersonajeId.of(personajeId)).stream()
                .map(ItemDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @PostMapping("/personaje/{personajeId}/item")
    public ResponseEntity<Void> addItemToPersonaje(
            @PathVariable Long personajeId,
            @RequestBody ItemDTO itemDTO) {
        inventarioUseCase.agregarItemAPersonaje(
                PersonajeId.of(personajeId),
                itemDTO.toDomain()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long itemId) {
        return ResponseEntity.ok(
                ItemDTO.fromDomain(
                        inventarioUseCase.obtenerItemPorId(ItemId.of(itemId))
                )
        );
    }
} 