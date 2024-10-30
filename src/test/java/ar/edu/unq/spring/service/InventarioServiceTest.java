package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.clasesDePersonajes.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InventarioServiceTest {

    @Autowired private PersonajeService personajeService;
    @Autowired private ItemService itemService;

    private Personaje maguin;
    private Personaje debilucho;
    private Item baculo;
    private Item tunica;

    @BeforeEach
    void prepare() {
        tunica = new Item("Tunica", 100);
        baculo = new Item("Baculo", 50);

        maguin = new Mago("Maguin");
        maguin.setPesoMaximo(70);
        maguin.setVida(10);

        debilucho = new Guerrero("Debilucho");
        debilucho.setPesoMaximo(1000);
        debilucho.setVida(1);

        itemService.guardarItem(tunica);
        itemService.guardarItem(baculo);
        personajeService.guardarPersonaje(maguin);
        personajeService.guardarPersonaje(debilucho);
    }

    @Test
    void testRecoger() {
        personajeService.recoger(maguin.getId(), baculo.getId());

        Personaje maguito = personajeService.recuperarPersonaje(maguin.getId());
        Assertions.assertEquals("Maguin", maguito.getNombre());

        Assertions.assertEquals(1, maguito.getInventario().size());

        Item baculo = maguito.getInventario().iterator().next();
        Assertions.assertEquals("Baculo", baculo.getNombre());

        Assertions.assertSame(baculo.getOwner(), maguito);
    }

    @Test
    void testGetAll() {
        var items = itemService.allItems();

        Assertions.assertEquals(2, items.size());
        Assertions.assertTrue(items.contains(baculo));
    }

    @Test
    void testGetMasPesados() {
        var items = itemService.getMasPesados(10);
        Assertions.assertEquals(2, items.size());

        var items2 = itemService.getMasPesados(80);
        Assertions.assertEquals(1, items2.size());
    }

    @Test
    void testGetItemsDebiles() {
        var items = itemService.getItemsPersonajesDebiles(5);
        Assertions.assertEquals(0, items.size());

        personajeService.recoger(maguin.getId(), baculo.getId());
        personajeService.recoger(debilucho.getId(), tunica.getId());

        items = itemService.getItemsPersonajesDebiles(5);
        Assertions.assertEquals(1, items.size());
        Assertions.assertEquals("Tunica", items.iterator().next().getNombre());
    }

    @Test
    void testGetMasPesado() {
        Item item = itemService.heaviestItem();
        Assertions.assertEquals("Tunica", item.getNombre());
    }

    @Test
    void testGenerarMilesDeDatos() {
        Random random = new Random();

        for (int i = 1; i <= 60000; i++) {
            Mago unMago = new Mago("Mago-" + i);
            unMago.setPesoMaximo(random.nextInt(200, 300));
            unMago.setVida(random.nextInt(50, 200));
            unMago.setMana(random.nextInt(400, 1000));
            personajeService.guardarPersonaje(unMago);
        }

        for (int i = 1; i <= 60000; i++) {
            Guerrero unGuerrero = new Guerrero("Guerrero-" + i);
            unGuerrero.setPesoMaximo(random.nextInt(500, 900));
            unGuerrero.setVida(random.nextInt(300, 1200));
            unGuerrero.setFuerza(random.nextInt(350, 675));
            personajeService.guardarPersonaje(unGuerrero);
        }

        for (int i = 1; i <= 60000; i++) {
            Picaro unPicaro = new Picaro("Picaro-" + i);
            unPicaro.setPesoMaximo(random.nextInt(50, 125));
            unPicaro.setVida(random.nextInt(100, 150));
            unPicaro.setSigilo(random.nextInt(250, 500));
            personajeService.guardarPersonaje(unPicaro);
        }
    }

    @AfterEach
    void tearDown() {
        personajeService.clearAll();
        itemService.clearAll();
    }
}
