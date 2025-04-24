package ar.edu.unq.spring.application.adapter.service;

import ar.edu.unq.spring.inventario.domain.model.Item;
import ar.edu.unq.spring.inventario.ports.api.AgregarItemAPersonajeUseCase;
import ar.edu.unq.spring.inventario.ports.api.CrearItemUseCase;
import ar.edu.unq.spring.inventario.ports.api.ObtenerInventarioDePersonajeUseCase;
import ar.edu.unq.spring.personaje.domain.model.Personaje;
import ar.edu.unq.spring.personaje.ports.api.CrearPersonajeUseCase;
import ar.edu.unq.spring.personaje.ports.api.EliminarTodosLosPersonajesUseCase;
import ar.edu.unq.spring.personaje.ports.api.ObtenerPersonajePorIdUseCase;
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
        agregarItemAPersonajeUseCase.ejecutar(baculo.getId(), maguin.getId());

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
        agregarItemAPersonajeUseCase.ejecutar(baculo.getId(), maguin.getId());
        agregarItemAPersonajeUseCase.ejecutar(tunica.getId(), debilucho.getId());
        
        // Verificar obtención de inventario
        List<Item> itemsMaguin = obtenerInventarioDePersonajeUseCase.ejecutar(maguin.getId());
        List<Item> itemsDebilucho = obtenerInventarioDePersonajeUseCase.ejecutar(debilucho.getId());
        
        Assertions.assertEquals(1, itemsMaguin.size());
        Assertions.assertEquals(1, itemsDebilucho.size());
    }

    @AfterEach
    public void limpiarDatos() {
        eliminarTodosLosPersonajesUseCase.ejecutar();
    }
} 