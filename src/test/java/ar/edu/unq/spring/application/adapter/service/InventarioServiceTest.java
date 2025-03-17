package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.application.port.input.*;
import ar.edu.unq.spring.application.port.input.ObtenerPersonajePorIdUseCase;
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
    private CrearItemUseCase crearItemUseCase;
    
    @Autowired
    private ObtenerInventarioDePersonajeUseCase obtenerInventarioDePersonajeUseCase;
    
    @Autowired
    private AgregarItemAPersonajeUseCase agregarItemAPersonajeUseCase;
    
    @Autowired
    private CrearPersonajeUseCase crearPersonajeUseCase;
    
    @Autowired
    private ObtenerPersonajePorIdUseCase obtenerPersonajePorIdUseCase;
    
    @Autowired
    private EliminarTodosLosPersonajesUseCase eliminarTodosLosPersonajesUseCase;

    private Personaje maguin;
    private Personaje debilucho;
    private Item baculo;
    private Item tunica;

    @BeforeEach
    public void prepararTest() {
        tunica = crearItemUseCase.ejecutar(new Item("Tunica", 100));
        baculo = crearItemUseCase.ejecutar(new Item("Baculo", 50));
        
        maguin = crearPersonajeUseCase.ejecutar(new Personaje("Maguin", 10, 70));
        debilucho = crearPersonajeUseCase.ejecutar(new Personaje("Debilucho", 1, 1000));
    }

    @Test
    public void testRecogerItem() {
        agregarItemAPersonajeUseCase.ejecutar(maguin.getId(), baculo);

        Personaje maguinActualizado = obtenerPersonajePorIdUseCase.ejecutar(maguin.getId());
        Assertions.assertEquals("Maguin", maguinActualizado.getNombre());
        Assertions.assertEquals(1, maguinActualizado.getInventario().size());

        Item baculoRecuperado = maguinActualizado.getInventario().iterator().next();
        Assertions.assertEquals("Baculo", baculoRecuperado.getNombre());
        Assertions.assertSame(baculoRecuperado.getDueño(), maguinActualizado);
    }

    @Test
    public void testObtenerTodosLosItems() {
        // Primero agregamos los items a algún personaje para que estén en el sistema
        agregarItemAPersonajeUseCase.ejecutar(maguin.getId(), baculo);
        agregarItemAPersonajeUseCase.ejecutar(debilucho.getId(), tunica);
        
        // Verificar obtención de inventario
        List<Item> itemsMaguin = obtenerInventarioDePersonajeUseCase.ejecutar(maguin.getId());
        List<Item> itemsDebilucho = obtenerInventarioDePersonajeUseCase.ejecutar(debilucho.getId());
        
        Assertions.assertEquals(1, itemsMaguin.size());
        Assertions.assertEquals(1, itemsDebilucho.size());
    }

    @Test
    public void testExcepcionPorMuchoPeso() {
        // Añadir el báculo a maguin
        agregarItemAPersonajeUseCase.ejecutar(maguin.getId(), baculo);
        
        // Intentar añadir la túnica (que pesa 100) debería fallar porque excede el límite
        MuchoPesoException exception = Assertions.assertThrows(
            MuchoPesoException.class, 
            () -> agregarItemAPersonajeUseCase.ejecutar(maguin.getId(), tunica)
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
            () -> crearPersonajeUseCase.ejecutar(otroMaguin)
        );
        
        Assertions.assertTrue(exception.getMessage().contains("Maguin"));
    }

    @AfterEach
    public void limpiarDatos() {
        eliminarTodosLosPersonajesUseCase.ejecutar();
    }
} 