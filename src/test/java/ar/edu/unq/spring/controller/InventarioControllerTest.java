package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.helper.MockMVCInventarioController;
import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventarioControllerTest {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private MockMVCInventarioController mockMVCController;

    private Long maguinId;
    private Long debiluchoId;
    private Long tunicaId;
    private Long baculoId;

    @BeforeEach
    public void prepare() throws Exception {
        var tunica = new Item("Tunica", 10);
        var baculo = new Item("Baculo", 50);

        var maguin = new Personaje("Maguin", 70, 100);
        var debilucho = new Personaje("Debilucho", 10, 100);

        maguinId = mockMVCController.guardarPersonaje(maguin, HttpStatus.CREATED);
        debiluchoId = mockMVCController.guardarPersonaje(debilucho, HttpStatus.CREATED);
        tunicaId = mockMVCController.guardarItem(tunica);
        baculoId = mockMVCController.guardarItem(baculo);
    }

    @Test
    public void testRecoger() throws Exception {
        mockMVCController.recoger(maguinId, baculoId);

        var maguito = mockMVCController.recuperarPersonaje(maguinId);
        Assertions.assertEquals("Maguin", maguito.getNombre());

        Assertions.assertEquals(1, maguito.getInventario().size());

        var baculo = maguito.getInventario().iterator().next();
        Assertions.assertEquals("Baculo", baculo.getNombre());

        Assertions.assertSame(baculo.getOwner(), maguito);
    }

    @AfterEach
    public void cleanUp() {
        inventarioService.clearAll();
    }
}
