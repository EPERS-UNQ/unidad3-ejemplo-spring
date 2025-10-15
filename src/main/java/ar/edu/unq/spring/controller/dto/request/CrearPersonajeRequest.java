package ar.edu.unq.spring.controller.dto.request;

import ar.edu.unq.spring.modelo.Personaje;


public record CrearPersonajeRequest(String nombre, int vida, int pesoMaximo) {

    public Personaje aModelo() {
        return null; //TODO: new Personaje(this.nombre, this.vida, this.pesoMaximo);
    }
}
