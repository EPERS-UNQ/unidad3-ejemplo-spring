package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;

import jakarta.transaction.Transactional;
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
    void recuperarEnVariasTransaccionesLeeLaDBRepetidamente() {
        log.info("☝️ >>> Primer Lectura...");
        personajeService.recuperarPersonaje(maguin.getId());
        log.info("✌️ >>> Segunda Lectura...");
        personajeService.recuperarPersonaje(maguin.getId());
    }

    @Test
    void testGenerarMilesDeDatos() {
        Random random = new Random();
        generarEPERs();
        for (int i = 1; i <= 200000; i++) {
            Personaje unMago = new Personaje("NPC-" + i);
            unMago.setPesoMaximo(random.nextInt(200, 300));
            unMago.setVida(random.nextInt(50, 200));
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
        Random random = new Random();

        Personaje unNigromante = new Personaje("Iancho");
        unNigromante.setPesoMaximo(random.nextInt(200, 300));
        unNigromante.setVida(random.nextInt(50, 200));
        personajeService.guardarPersonaje(unNigromante);

        Personaje unBardo = new Personaje("Fran");
        unBardo.setPesoMaximo(random.nextInt(200, 300));
        unBardo.setVida(random.nextInt(50, 200));
        personajeService.guardarPersonaje(unBardo);

        Personaje unPaladin = new Personaje("Fabi");
        unPaladin.setPesoMaximo(random.nextInt(50, 125));
        unPaladin.setVida(random.nextInt(100, 150));
        personajeService.guardarPersonaje(unPaladin);

        Personaje unBarbarian = new Personaje("Luki");
        unBarbarian.setPesoMaximo(random.nextInt(50, 125));
        unBarbarian.setVida(random.nextInt(100, 150));
        personajeService.guardarPersonaje(unBarbarian);

        Personaje elReyDeLosBandidos = new Personaje("Valentin");
        elReyDeLosBandidos.setPesoMaximo(random.nextInt(50, 125));
        elReyDeLosBandidos.setVida(random.nextInt(100, 150));
        personajeService.guardarPersonaje(elReyDeLosBandidos);
    }

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(applicationContext.getBean(DataSource.class));
        String sql = "delete from personaje";
        jdbcTemplate.execute(sql);
    }
}
