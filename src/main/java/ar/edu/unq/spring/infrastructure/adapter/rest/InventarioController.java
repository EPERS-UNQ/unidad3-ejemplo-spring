package ar.edu.unq.spring.infrastructure.adapter.rest;

import ar.edu.unq.spring.application.port.input.AgregarItemAPersonajeUseCase;
import ar.edu.unq.spring.application.port.input.ObtenerInventarioDePersonajeUseCase;
import ar.edu.unq.spring.application.port.input.ObtenerItemPorIdUseCase;
import ar.edu.unq.spring.domain.model.ItemId;
import ar.edu.unq.spring.domain.model.PersonajeId;
import ar.edu.unq.spring.infrastructure.adapter.rest.dto.ItemCommunication;
import ar.edu.unq.spring.infrastructure.adapter.rest.dto.ItemPresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final ObtenerInventarioDePersonajeUseCase obtenerInventarioDePersonajeUseCase;
    private final AgregarItemAPersonajeUseCase agregarItemAPersonajeUseCase;
    private final ObtenerItemPorIdUseCase obtenerItemPorIdUseCase;

    public InventarioController(
            ObtenerInventarioDePersonajeUseCase obtenerInventarioDePersonajeUseCase,
            AgregarItemAPersonajeUseCase agregarItemAPersonajeUseCase,
            ObtenerItemPorIdUseCase obtenerItemPorIdUseCase) {
        this.obtenerInventarioDePersonajeUseCase = obtenerInventarioDePersonajeUseCase;
        this.agregarItemAPersonajeUseCase = agregarItemAPersonajeUseCase;
        this.obtenerItemPorIdUseCase = obtenerItemPorIdUseCase;
    }

    @GetMapping("/personaje/{personajeId}")
    public List<ItemPresentation> getInventarioByPersonajeId(@PathVariable Long personajeId) {
        return obtenerInventarioDePersonajeUseCase.ejecutar(PersonajeId.of(personajeId)).stream()
                .map(ItemPresentation::fromDomain)
                .collect(Collectors.toList());
    }

    @PostMapping("/personaje/{personajeId}/item")
    public ResponseEntity<Void> addItemToPersonaje(
            @PathVariable Long personajeId,
            @RequestBody ItemCommunication itemCommunication) {
        agregarItemAPersonajeUseCase.ejecutar(
                PersonajeId.of(personajeId),
                itemCommunication.toDomain()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ItemPresentation> getItemById(@PathVariable Long itemId) {
        return ResponseEntity.ok(
                ItemPresentation.fromDomain(
                        obtenerItemPorIdUseCase.ejecutar(ItemId.of(itemId))
                )
        );
    }
} 