package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.helper.MockMVCPersonajeController;
import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonajeControllerTest {
    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private MockMVCPersonajeController mockMVCPersonajeController;

    private Personaje maguin;
    private Personaje fortachulo;

    private Long maguinId;

    @BeforeEach
    public void prepare() throws Throwable {
        maguin = new Personaje("Maguin", 70, 100);
        fortachulo = new Personaje("Fortachulo", 40, 100);

        maguinId = mockMVCPersonajeController.guardarPersonaje(maguin);
        mockMVCPersonajeController.guardarPersonaje(fortachulo);
    }

    @Test
    public void testGetPersonajeById() throws Throwable {
        var maguitoRecuperado = mockMVCPersonajeController.recuperarPersonaje(maguinId);

        Assertions.assertEquals(maguin.getNombre(), maguitoRecuperado.getNombre());
    }

    @AfterEach
    public void cleanUp() {
        personajeService.clearAll();
    }
}
