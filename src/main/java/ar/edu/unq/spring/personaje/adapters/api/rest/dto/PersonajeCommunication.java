package ar.edu.unq.spring.personaje.adapters.api.rest.dto;

import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.domain.model.PersonajeId;
import ar.edu.unq.spring.inventario.adapters.api.rest.dto.ItemCommunication;

import java.util.Set;

public record PersonajeCommunication(Long id, String nombre, int vida, int pesoMaximo, Set<ItemCommunication> inventario) {

    public Personaje toDomain() {
        Personaje personaje = new Personaje(this.nombre, this.vida, this.pesoMaximo);
        
        if (this.id != null) {
            personaje.setId(PersonajeId.of(this.id));
        }
        
        return personaje;
    }
} 