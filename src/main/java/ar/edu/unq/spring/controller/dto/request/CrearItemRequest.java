package ar.edu.unq.spring.controller.dto.request;

import ar.edu.unq.spring.modelo.Item;


public record CrearItemRequest(String nombre, int peso) {

    public Item aModelo() {
        return new Item(this.nombre, this.peso);
    }
}
