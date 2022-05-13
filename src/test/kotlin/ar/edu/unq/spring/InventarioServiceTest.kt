package ar.edu.unq.spring

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.service.InventarioService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ContextConfiguration(classes = [InventarioServiceTestConfig::class])
@ExtendWith(SpringExtension::class)
@DataJpaTest
@TestInstance(PER_CLASS)
class InventarioServiceTest {

    lateinit var maguin: Personaje
    lateinit var debilucho: Personaje
    lateinit var baculo: Item
    lateinit var tunica: Item

    @Autowired
    lateinit var service: InventarioService

    @BeforeAll
    fun prepare() {

        tunica = Item("Tunica", 100)
        baculo = Item("Baculo", 50)

        service.guardarItem(tunica)
        service.guardarItem(baculo)

        maguin = Personaje("Maguin")
        maguin.pesoMaximo = 70
        maguin.vida = 10
        service.guardarPersonaje(maguin)


        debilucho = Personaje("Debilucho")
        debilucho.pesoMaximo = 1000
        debilucho.vida = 1
        service.guardarPersonaje(debilucho)
    }

    @Test
    fun testRecoger() {
        service.recoger(maguin.id, baculo.id)

        val maguito = service.recuperarPersonaje(maguin.id)
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

        service.recoger(maguin.id, baculo.id)
        service.recoger(debilucho.id, tunica.id)

        items = service.getItemsPersonajesDebiles(5)
        Assertions.assertEquals(1, items.size.toLong())
        Assertions.assertEquals("Tunica", items.iterator().next().nombre)

    }

    @Test
    fun testGetMasPesado() {
        val item = service.heaviestItem()
        Assertions.assertEquals("Tunica", item.nombre)
    }
}