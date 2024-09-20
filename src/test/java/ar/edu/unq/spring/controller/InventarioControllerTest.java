package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.helper.MockMVCInventarioController;
import ar.edu.unq.spring.controller.helper.MockMVCPersonajeController;
import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.exception.MuchoPesoException;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class InventarioControllerTest {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private MockMVCInventarioController mockMVCInventarioController;

    @Autowired
    private MockMVCPersonajeController mockMVCPersonajeController;

    private Long maguinId;
    private Long debiluchoId;
    private Long tunicaId;
    private Long baculoId;

    @BeforeEach
    public void prepare() throws Throwable {
        var tunica = new Item("Tunica", 20);
        var baculo = new Item("Baculo", 50);

        var maguin = new Personaje("Maguin", 70, 100);
        var debilucho = new Personaje("Debilucho", 10, 60);

        maguinId = mockMVCPersonajeController.guardarPersonaje(maguin);
        debiluchoId = mockMVCPersonajeController.guardarPersonaje(debilucho);
        tunicaId = mockMVCInventarioController.guardarItem(tunica);
        baculoId = mockMVCInventarioController.guardarItem(baculo);
    }

    @Test
    public void testGetAllItems() throws Throwable {
        var items = mockMVCInventarioController.allItems();

        Assertions.assertEquals(2, items.size());
    }

    @Test
    public void testRecoger() throws Throwable {
        mockMVCInventarioController.recoger(maguinId, baculoId, HttpStatus.OK);

        var maguito = mockMVCPersonajeController.recuperarPersonaje(maguinId);
        Assertions.assertEquals("Maguin", maguito.getNombre());

        Assertions.assertEquals(1, maguito.getInventario().size());

        var baculo = maguito.getInventario().iterator().next();
        Assertions.assertEquals("Baculo", baculo.getNombre());

        Assertions.assertSame(baculo.getOwner(), maguito);
    }

    @Test
    public void testMuchoPesoException() throws Throwable {
        mockMVCInventarioController.recoger(debiluchoId, baculoId, HttpStatus.OK);

        var exception = Assertions.assertThrows(MuchoPesoException.class, () -> {
            mockMVCInventarioController.recoger(debiluchoId, tunicaId, HttpStatus.BAD_REQUEST);
        });

        Assertions.assertEquals("El personaje [Debilucho] no puede recoger [Tunica] porque cagar mucho peso ya",
                exception.getMessage());
    }

    @AfterEach
    public void cleanUp() {
        inventarioService.clearAll();
        personajeService.clearAll();
    }
}
