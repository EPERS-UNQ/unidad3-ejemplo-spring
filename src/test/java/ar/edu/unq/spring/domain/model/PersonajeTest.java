package ar.edu.unq.spring.domain.model;

import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.domain.exception.MuchoPesoException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonajeTest {

    @Test
    void crearPersonajeConNombreVidaYPesoMaximo() {
        String nombre = "Gandalf";
        int vida = 100;
        int pesoMaximo = 50;
        
        Personaje personaje = new Personaje(nombre, vida, pesoMaximo);
        
        assertEquals(nombre, personaje.getNombre());
        assertEquals(vida, personaje.getVida());
        assertEquals(pesoMaximo, personaje.getPesoMaximo());
        assertTrue(personaje.getInventario().isEmpty());
    }
    
    @Test
    void obtenerElPesoActualDelInventario() {
        Personaje personaje = new Personaje("Frodo", 50, 30);
        Item anillo = new Item("Anillo Único", 1);
        Item espada = new Item("Dardo", 5);
        
        personaje.recoger(anillo);
        personaje.recoger(espada);
        int pesoActual = personaje.getPesoActual();
        
        assertEquals(6, pesoActual);
    }
    
    @Test
    void recogerItemSiNoPasaElPesoMaximo() {
        Personaje personaje = new Personaje("Aragorn", 120, 70);
        Item espada = new Item("Andúril", 15);
        
        personaje.recoger(espada);
        
        assertEquals(1, personaje.getInventario().size());
        assertTrue(personaje.getInventario().contains(espada));
        assertEquals(personaje, espada.getDueño());
    }
    
    @Test
    void deberiaLanzarExcepcionAlRecogerItemQueSuperaPesoMaximo() {
        Personaje hobbit = new Personaje("Sam", 40, 20);
        Item bolsa = new Item("Bolsa de papas", 10);
        Item olla = new Item("Olla para cocinar", 15);
        
        hobbit.recoger(bolsa);
        
        // La olla pesa 15, más los 10 de la bolsa = 25, que supera el máximo de 20
        MuchoPesoException exception = assertThrows(
            MuchoPesoException.class, 
            () -> hobbit.recoger(olla)
        );
        
        String mensajeError = exception.getMessage();
        assertTrue(mensajeError.contains("Sam"));
        assertTrue(mensajeError.contains("Olla para cocinar"));
    }
} 