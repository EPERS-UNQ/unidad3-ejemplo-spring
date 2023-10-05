package ar.edu.unq.spring.service

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.modelo.exception.MuchoPesoException
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido
import ar.edu.unq.spring.service.helper.MockMVCInventarioController
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ContextConfiguration(classes = [InventarioControllerTestConfiguration::class])
@TestInstance(PER_CLASS)
class InventarioControllerTest {

    @Autowired
    lateinit var service: InventarioService

    @Autowired
    lateinit var mockMVCController: MockMVCInventarioController

    var maguinId: Long? = null
    var debiluchoId: Long? = null
    var baculoId: Long? = null
    var tunicaId: Long? = null

    @BeforeEach
    fun prepare() {
        val tunica = Item("Tunica", 100)
        val baculo = Item("Baculo", 50)

        val maguin = Personaje("Maguin")
        maguin.pesoMaximo = 70
        maguin.vida = 10

        val debilucho = Personaje("Debilucho")
        debilucho.pesoMaximo = 1000
        debilucho.vida = 1

        maguinId = mockMVCController.guardarPersonaje(maguin)
        debiluchoId = mockMVCController.guardarPersonaje(debilucho)
        tunicaId = mockMVCController.guardarItem(tunica)
        baculoId = mockMVCController.guardarItem(baculo)
    }

    @Test
    fun testRecoger() {
        mockMVCController.recoger(maguinId!!, baculoId!!)
        val maguito = mockMVCController.recuperarPersonaje(maguinId!!)
        Assertions.assertEquals("Maguin", maguito.nombre)

        Assertions.assertEquals(1, maguito.inventario.size)

        val baculo = maguito.inventario.iterator().next()
        Assertions.assertEquals("Baculo", baculo.nombre)

        Assertions.assertSame(baculo.owner, maguito)
    }

    @Test
    fun testGetAll() {
        val items = mockMVCController.allItems()
        val baculo = mockMVCController.recuperarItem(baculoId!!)

        Assertions.assertEquals(2, items.size.toLong())
        Assertions.assertTrue(items.contains(baculo))
    }

    @Test
    fun testGetMasPesados() {
        val items = mockMVCController.getMasPesados(10)
        Assertions.assertEquals(2, items.size.toLong())

        val items2 = mockMVCController.getMasPesados(80)
        Assertions.assertEquals(1, items2.size.toLong())
    }

    @Test
    fun testGetItemsDebiles() {
        var items = mockMVCController.getItemsPersonajesDebiles(5)
        Assertions.assertEquals(0, items.size.toLong())

        mockMVCController.recoger(maguinId!!, baculoId!!)
        mockMVCController.recoger(debiluchoId!!, tunicaId!!)

        items = mockMVCController.getItemsPersonajesDebiles(5)
        Assertions.assertEquals(1, items.size.toLong())
        Assertions.assertEquals("Tunica", items.iterator().next().nombre)

    }

    @Test
    fun testGetMasPesado() {
        val item = mockMVCController.heaviestItem()
        Assertions.assertEquals("Tunica", item.nombre)
    }


    @Test
    fun testMuchoPesoException() {
        mockMVCController.recoger(maguinId!!, baculoId!!)
        val exception = Assertions.assertThrows(MuchoPesoException::class.java) {
            mockMVCController.recoger(maguinId!!, tunicaId!!, expectedStatus = HttpStatus.BAD_REQUEST)
        }

        Assertions.assertEquals(
            "El personaje [Maguin] no puede recoger [Tunica] porque cagar mucho peso ya",
            exception.message
        )
    }

    @Test
    fun testNombreDePersonajeTieneQueSerUnico() {
        val otroMaguin = Personaje("Maguin")
        otroMaguin.pesoMaximo = 70
        otroMaguin.vida = 10

        val exception = Assertions.assertThrows(NombreDePersonajeRepetido::class.java) {
            mockMVCController.guardarPersonaje(otroMaguin, expectedStatus = HttpStatus.BAD_REQUEST)
        }

        Assertions.assertEquals(
            "El nombre de personaje [Maguin] ya esta siendo utilizado y no puede volver a crearse",
            exception.message
        )
    }

    @AfterEach
    fun tearDown() {
        service.clearAll()
    }
}