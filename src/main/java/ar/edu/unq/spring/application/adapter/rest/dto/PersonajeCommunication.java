package ar.edu.unq.spring.application.adapter.rest.dto;

import ar.edu.unq.spring.domain.model.Personaje;
import ar.edu.unq.spring.domain.model.PersonajeId;

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