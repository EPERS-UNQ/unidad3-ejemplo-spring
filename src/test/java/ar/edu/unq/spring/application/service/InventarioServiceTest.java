package ar.edu.unq.spring.application.service;

import ar.edu.unq.spring.application.port.input.InventarioUseCase;
import ar.edu.unq.spring.application.port.input.PersonajeUseCase;
import ar.edu.unq.spring.domain.model.Item;
import ar.edu.unq.spring.domain.model.exception.MuchoPesoException;
import ar.edu.unq.spring.domain.model.exception.NombreDePersonajeRepetidoException;
import ar.edu.unq.spring.domain.model.Personaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class InventarioServiceTest {

    @Autowired
    private InventarioUseCase inventarioUseCase;

    @Autowired
    private PersonajeUseCase personajeUseCase;

    private Personaje maguin;
    private Personaje debilucho;
    private Item baculo;
    private Item tunica;

    @BeforeEach
    public void prepararTest() {
        tunica = inventarioUseCase.crearItem(new Item("Tunica", 100));
        baculo = inventarioUseCase.crearItem(new Item("Baculo", 50));
        
        maguin = personajeUseCase.crearPersonaje(new Personaje("Maguin", 10, 70));
        debilucho = personajeUseCase.crearPersonaje(new Personaje("Debilucho", 1, 1000));
    }

    @Test
    public void testRecogerItem() {
        inventarioUseCase.agregarItemAPersonaje(maguin.getId(), baculo);

        Personaje maguinActualizado = personajeUseCase.obtenerPersonajePorId(maguin.getId());
        Assertions.assertEquals("Maguin", maguinActualizado.getNombre());
        Assertions.assertEquals(1, maguinActualizado.getInventario().size());

        Item baculoRecuperado = maguinActualizado.getInventario().iterator().next();
        Assertions.assertEquals("Baculo", baculoRecuperado.getNombre());
        Assertions.assertSame(baculoRecuperado.getDueño(), maguinActualizado);
    }

    @Test
    public void testObtenerTodosLosItems() {
        // Primero agregamos los items a algún personaje para que estén en el sistema
        inventarioUseCase.agregarItemAPersonaje(maguin.getId(), baculo);
        inventarioUseCase.agregarItemAPersonaje(debilucho.getId(), tunica);
        
        // Verificar obtención de inventario
        List<Item> itemsMaguin = inventarioUseCase.obtenerInventarioDePersonaje(maguin.getId());
        List<Item> itemsDebilucho = inventarioUseCase.obtenerInventarioDePersonaje(debilucho.getId());
        
        Assertions.assertEquals(1, itemsMaguin.size());
        Assertions.assertEquals(1, itemsDebilucho.size());
    }

    @Test
    public void testExcepcionPorMuchoPeso() {
        // Añadir el báculo a maguin
        inventarioUseCase.agregarItemAPersonaje(maguin.getId(), baculo);
        
        // Intentar añadir la túnica (que pesa 100) debería fallar porque excede el límite
        MuchoPesoException exception = Assertions.assertThrows(
            MuchoPesoException.class, 
            () -> inventarioUseCase.agregarItemAPersonaje(maguin.getId(), tunica)
        );
        
        String mensajeError = exception.getMessage();
        Assertions.assertTrue(mensajeError.contains("Maguin"));
        Assertions.assertTrue(mensajeError.contains("Tunica"));
    }

    @Test
    public void testNombreDePersonajeTieneQueSerUnico() {
        // Intentar crear otro personaje con el mismo nombre
        Personaje otroMaguin = new Personaje("Maguin", 10, 70);

        NombreDePersonajeRepetidoException exception = Assertions.assertThrows(
            NombreDePersonajeRepetidoException.class, 
            () -> personajeUseCase.crearPersonaje(otroMaguin)
        );
        
        Assertions.assertTrue(exception.getMessage().contains("Maguin"));
    }

    @AfterEach
    public void limpiarDatos() {
        // Esto no deberia estar en el useCase, no? Lo dejo aca por comodidad
        personajeUseCase.eliminarTodosLosPersonajes();
    }
} 