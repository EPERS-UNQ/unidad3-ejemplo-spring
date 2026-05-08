package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Random;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InventarioServiceTest {

    private static final Logger log = LoggerFactory.getLogger(InventarioServiceTest.class);
    @Autowired private PersonajeService personajeService;
    @Autowired private ItemService itemService;

    private Personaje maguin;
    private Personaje debilucho;
    private Item baculo;
    private Item tunica;
    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void prepare() {
        tunica = new Item("Tunica", 100);
        baculo = new Item("Baculo", 50);

        maguin = new Personaje("Maguin");
        maguin.setPesoMaximo(70);
        maguin.setVida(10);

        debilucho = new Personaje("Debilucho");
        debilucho.setPesoMaximo(1000);
        debilucho.setVida(1);

        itemService.guardarItem(tunica);
        itemService.guardarItem(baculo);
        personajeService.guardarPersonaje(maguin);
        personajeService.guardarPersonaje(debilucho);
    }

    @Test
    void testRecoger() throws InterruptedException {
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
    void recuperarEnVariasTransaccionesLeeLaDBRepetidamente() {
        /*
            Con L2 apagada:
            >> Deberiamos ver la misma query (select Personaje join Item) las dos veces
            >> Si encapularamos esto en una sola transacción, esperariamos:
                - ver la query (select Personaje join Item) en el primer llamado, y
                - NADA en el segundo llamado, ya que trae al Personaje e Items de la caché
            Con L2 activa, deberiamos ver:la query con el join en el primer llamado,
            >> Primero la query (select Personaje join Item)
            >> Y luego la query (select Item where owner.id = ?id)
         */
        log.info("☝️ >>> Primer Lectura...");
        personajeService.recuperarPersonaje(maguin.getId());
        log.info("✌️ >>> Segunda Lectura...");
        personajeService.recuperarPersonaje(maguin.getId());
    }

    @Test
    void recuperarVariasVecesDesdeUnaMismaSesionEnService() {
        personajeService.recuperarPersonajeNVeces(maguin.getId(), 10);
    }

    @Test
    void testGenerarMilesDeDatos() {
        Random random = new Random();
        generarEPERs();
        for (int i = 1; i <= 200000; i++) {
            Personaje unMago = new Personaje("PJ_" + i, random.nextInt(200, 300), random.nextInt(50, 200));
            personajeService.guardarPersonaje(unMago);
        }
    }

    @Test
    void bootstrap() throws IOException {
        generarEPERs();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(applicationContext.getBean(DataSource.class));
        var resource = new ClassPathResource("bootstrap.sql");
        String sql = new String(resource.getInputStream().readAllBytes());
        jdbcTemplate.execute(sql);
    }

    private void generarEPERs() {
        Personaje mago = new Personaje("Tobi", 250, 100);
        personajeService.guardarPersonaje(mago);

        Personaje bardo = new Personaje("Pancho", 220, 150);
        personajeService.guardarPersonaje(bardo);

        Personaje malevo = new Personaje("Valen", 280, 120);
        personajeService.guardarPersonaje(malevo);

        Personaje druida = new Personaje("Tom", 80, 130);
        personajeService.guardarPersonaje(druida);

        Personaje picaro = new Personaje("Pablito", 70, 140);
        personajeService.guardarPersonaje(picaro);

        Personaje clerigo = new Personaje("Emi", 240, 180);
        personajeService.guardarPersonaje(clerigo);

        Personaje hechicera = new Personaje("Lu", 260, 90);
        personajeService.guardarPersonaje(hechicera);

        Personaje maga = new Personaje("Ale", 100, 145);
        personajeService.guardarPersonaje(maga);

        Personaje templario = new Personaje("Mate", 110, 135);
        personajeService.guardarPersonaje(templario);

        Personaje arquera = new Personaje("Jose", 90, 125);
        personajeService.guardarPersonaje(arquera);

        Personaje cazadorDeDragones = new Personaje("Lolo", 120, 150);
        personajeService.guardarPersonaje(cazadorDeDragones);

        Personaje paladin = new Personaje("Fabi", 115, 140);
        personajeService.guardarPersonaje(paladin);
    }

    @AfterEach
    void tearDown() {
        itemService.clearAll();
        personajeService.clearAll();
    }
}
