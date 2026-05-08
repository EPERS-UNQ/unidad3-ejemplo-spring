package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class CacheLevelsTest {

    @Autowired private InventarioService inventarioService;
    @Autowired private PersonajeService personajeService;

    Personaje personaje;

    @BeforeEach
    public void prepare() {
        personaje = new Personaje("Magatito", 90, 10);
        Set<Item> inventario = Set.of(
                new Item("Baculo Mágico", 30),
                new Item("Tunica Elfica", 2),
                new Item("Poción Curativa", 2)
        );
        personaje.setInventario(inventario);
        personajeService.guardarPersonaje(personaje);

        System.out.println("===================================================================");
        System.out.println("=--- Acá va arranca el test");
        System.out.println("===================================================================");
    }

    /*
        En cada test y con cada config de caché:
        a) ¿Cuantas transacciones van a ejecutarse?
        b) ¿Cuantas veces esperamos recuperar al personaje desde la DB?
        c) ¿Cuantas veces esperamos recuperar los items del personaje?
     */

    @Test
    public void readingWithMultipleTransactions() {
        for (int i = 0; i < 10; i++) {
            personajeService.recuperarPersonaje(personaje.getId());;
        }
    }

    @Test
    public void readingWithSimpleTransactions() {
        personajeService.recuperarPersonajeNVeces(personaje.getId(), 10);
    }

    @AfterEach
    public void tearDown() {
        personajeService.clearAll();
    }
}