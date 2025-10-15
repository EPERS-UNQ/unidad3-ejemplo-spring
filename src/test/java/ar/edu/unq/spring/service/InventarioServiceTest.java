package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Guerrero;
import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Mago;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.exception.MuchoPesoException;
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import ar.edu.unq.spring.service.interfaces.ResetService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class InventarioServiceTest {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private ResetService resetService;

    private Personaje maguin;
    private Personaje debilucho;
    private Item baculo;
    private Item tunica;

    @BeforeEach
    public void prepare() {
        tunica = new Item("Tunica", 10);
        baculo = new Item("Baculo", 50);

        maguin = new Mago("Maguin", 10, 70, 100);
        debilucho = new Guerrero("Debilucho", 1, 1000, 1);

        inventarioService.guardarItem(tunica);
        inventarioService.guardarItem(baculo);
        personajeService.guardarPersonaje(maguin);
        personajeService.guardarPersonaje(debilucho);
    }

    @Test
    public void testRecoger() {
        inventarioService.recoger(maguin.getId(), baculo.getId());

        Personaje maguito = personajeService.recuperarPersonaje(maguin.getId());
        Assertions.assertEquals("Maguin", maguito.getNombre());
        Assertions.assertEquals(1, maguito.getInventario().size());

        Item baculo = maguito.getInventario().iterator().next();
        Assertions.assertEquals("Baculo", baculo.getNombre());
        Assertions.assertTrue(baculo.getOwners().contains(maguito));
    }

    @Test
    public void testGetAll() {
        Collection<Item> items = inventarioService.allItems();
        Assertions.assertEquals(2, items.size());
        Assertions.assertTrue(items.contains(baculo));
    }

    @Test
    public void testGetMasPesados() {
        Collection<Item> items = inventarioService.getMasPesados(10);
        Assertions.assertEquals(2, items.size());

        Collection<Item> items2 = inventarioService.getMasPesados(80);
        Assertions.assertEquals(1, items2.size());
    }


    @Test
    public void testGetMasPesado() {
        Item item = inventarioService.heaviestItem();
        Assertions.assertEquals("Tunica", item.getNombre());
    }

    @Test
    public void testManyToManyRelationship() {
        inventarioService.recoger(maguin.getId(), baculo.getId());
        inventarioService.recoger(maguin.getId(), tunica.getId());
        inventarioService.recoger(debilucho.getId(), baculo.getId());
        inventarioService.recoger(debilucho.getId(), tunica.getId());

        Personaje maguinRecuperado = personajeService.recuperarPersonaje(maguin.getId());
        Assertions.assertEquals("Maguin", maguinRecuperado.getNombre());
        Assertions.assertEquals(2, maguinRecuperado.getInventario().size(), 
            "Maguin debería tener 2 items en su inventario");

        Item baculoRecuperado = inventarioService.getItem(baculo.getId());
        Assertions.assertEquals("Baculo", baculoRecuperado.getNombre());
        Assertions.assertEquals(2, baculoRecuperado.getOwners().size(), 
            "El Baculo debería tener 2 owners");
        
        Assertions.assertTrue(
            baculoRecuperado.getOwners().stream()
                .anyMatch(p -> p.getNombre().equals("Maguin")),
            "Maguin debería ser owner del Baculo"
        );
        Assertions.assertTrue(
            baculoRecuperado.getOwners().stream()
                .anyMatch(p -> p.getNombre().equals("Debilucho")),
            "Debilucho debería ser owner del Baculo"
        );
    }

    @Test
    public void testMuchoPesoException() {
        inventarioService.recoger(maguin.getId(), baculo.getId());
        MuchoPesoException exception = Assertions.assertThrows(MuchoPesoException.class, () -> {
            inventarioService.recoger(maguin.getId(), tunica.getId());
        });
        Assertions.assertEquals("El personaje [Maguin] no puede recoger [Tunica] porque cagar mucho peso ya", exception.getMessage());
    }

    @Test
    public void testNombreDePersonajeTieneQueSerUnico() {
        Personaje otroMaguin = new Mago("Maguin", 10, 70, 10);

        NombreDePersonajeRepetido exception = Assertions.assertThrows(NombreDePersonajeRepetido.class, () -> {
            personajeService.guardarPersonaje(otroMaguin);
        });
        Assertions.assertEquals("El nombre de personaje [Maguin] ya esta siendo utilizado y no puede volver a crearse", exception.getMessage());
    }

    @AfterEach
    public void tearDown() {
        resetService.resetAll();
    }
}