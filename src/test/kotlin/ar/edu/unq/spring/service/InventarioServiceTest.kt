package ar.edu.unq.spring.service

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.persistence.ItemDAO
import ar.edu.unq.spring.persistence.PersonajeDAO
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class InventarioServiceTest {

    @Autowired
    lateinit var personajeDAO: PersonajeDAO
    @Autowired
    lateinit var itemDAO: ItemDAO
    @Autowired
    lateinit var service: InventarioService

    lateinit var maguin: Personaje
    lateinit var debilucho: Personaje
    lateinit var baculo: Item
    lateinit var tunica: Item

    @BeforeEach
    fun prepare() {
        tunica = Item("Tunica", 100)
        baculo = Item("Baculo", 50)

        maguin = Personaje("Maguin")
        maguin.pesoMaximo = 70
        maguin.vida = 10

        debilucho = Personaje("Debilucho")
        debilucho.pesoMaximo = 1000
        debilucho.vida = 1

        service.guardarItem(tunica)
        service.guardarItem(baculo)
        service.guardarPersonaje(maguin)
        service.guardarPersonaje(debilucho)
    }

    @Test
    fun testRecoger() {
        service.recoger(maguin.id!!, baculo.id!!)

        val maguito = service.recuperarPersonaje(maguin.id!!)
        Assertions.assertEquals("Maguin", maguito?.nombre)

        Assertions.assertEquals(1, maguito?.inventario?.size)

        val baculo = maguito?.inventario?.iterator()?.next()
        Assertions.assertEquals("Baculo", baculo?.nombre)

        Assertions.assertSame(baculo?.owner, maguito)
    }

    @Test
    fun testGetAll() {
        val items = service.allItems()

        Assertions.assertEquals(2, items.size.toLong())
        Assertions.assertTrue(items.contains(baculo))
    }

    @Test
    fun testGetMasPesados() {
        val items = service.getMasPesados(10)
        Assertions.assertEquals(2, items.size.toLong())

        val items2 = service.getMasPesados(80)
        Assertions.assertEquals(1, items2.size.toLong())
    }

    @Test
    fun testGetItemsDebiles() {
        var items = service.getItemsPersonajesDebiles(5)
        Assertions.assertEquals(0, items.size.toLong())

        service.recoger(maguin.id!!, baculo.id!!)
        service.recoger(debilucho.id!!, tunica.id!!)

        items = service.getItemsPersonajesDebiles(5)
        Assertions.assertEquals(1, items.size.toLong())
        Assertions.assertEquals("Tunica", items.iterator().next().nombre)

    }

    @Test
    fun testGetMasPesado() {
        val item = service.heaviestItem()
        Assertions.assertEquals("Tunica", item.nombre)
    }

    @AfterEach
    fun tearDown() {
       service.clearAll()
    }
}