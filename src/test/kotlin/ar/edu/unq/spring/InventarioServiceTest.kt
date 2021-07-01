package ar.edu.unq.spring

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.service.InventarioService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@ContextConfiguration(classes = [InventarioServiceTestConfig::class])
@DataJpaTest
@RunWith(SpringRunner::class)
class InventarioServiceTest {

    lateinit var maguin: Personaje
    lateinit var debilucho: Personaje
    lateinit var baculo: Item
    lateinit var tunica: Item

    @Autowired
    lateinit var service: InventarioService

    @Before
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
        Assert.assertEquals("Maguin", maguito?.nombre)

        Assert.assertEquals(1, maguito?.inventario?.size)

        val baculo = maguito?.inventario?.iterator()?.next()
        Assert.assertEquals("Baculo", baculo?.nombre)

        Assert.assertSame(baculo?.owner, maguito)
    }

    @Test
    fun testGetAll() {
        val items = service.allItems()

        Assert.assertEquals(2, items.size.toLong())
        Assert.assertTrue(items.contains(baculo))
    }

    @Test
    fun testGetMasPesados() {
        val items = service.getMasPesados(10)
        Assert.assertEquals(2, items.size.toLong())

        val items2 = service.getMasPesados(80)
        Assert.assertEquals(1, items2.size.toLong())
    }

    @Test
    fun testGetItemsDebiles() {
        var items = service.getItemsPersonajesDebiles(5)
        Assert.assertEquals(0, items.size.toLong())

        service.recoger(maguin.id, baculo.id)
        service.recoger(debilucho.id, tunica.id)

        items = service.getItemsPersonajesDebiles(5)
        Assert.assertEquals(1, items.size.toLong())
        Assert.assertEquals("Tunica", items.iterator().next().nombre)

    }

    @Test
    fun testGetMasPesado() {
        val item = service.heaviestItem()
        Assert.assertEquals("Tunica", item.nombre)
    }
}
