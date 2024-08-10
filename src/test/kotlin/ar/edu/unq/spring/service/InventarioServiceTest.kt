package ar.edu.unq.spring.service

import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import ar.edu.unq.spring.modelo.exception.MuchoPesoException
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido
import ar.edu.unq.spring.service.interfaces.InventarioService
import ar.edu.unq.spring.service.interfaces.PersonajeService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class InventarioServiceTest {

    @Autowired
    lateinit var inventarioService: InventarioService

    @Autowired
    lateinit var personajeService: PersonajeService

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

        inventarioService.guardarItem(tunica)
        inventarioService.guardarItem(baculo)
        personajeService.guardarPersonaje(maguin)
        personajeService.guardarPersonaje(debilucho)
    }

    @Test
    fun testRecoger() {
        inventarioService.recoger(maguin.id!!, baculo.id!!)

        val maguito = personajeService.recuperarPersonaje(maguin.id!!)
        Assertions.assertEquals("Maguin", maguito?.nombre)

        Assertions.assertEquals(1, maguito?.inventario?.size)

        val baculo = maguito?.inventario?.iterator()?.next()
        Assertions.assertEquals("Baculo", baculo?.nombre)

        Assertions.assertSame(baculo?.owner, maguito)
    }

    @Test
    fun testGetAll() {
        val items = inventarioService.allItems()

        Assertions.assertEquals(2, items.size.toLong())
        Assertions.assertTrue(items.contains(baculo))
    }

    @Test
    fun testGetMasPesados() {
        val items = inventarioService.getMasPesados(10)
        Assertions.assertEquals(2, items.size.toLong())

        val items2 = inventarioService.getMasPesados(80)
        Assertions.assertEquals(1, items2.size.toLong())
    }

    @Test
    fun testGetItemsDebiles() {
        var items = inventarioService.getItemsPersonajesDebiles(5)
        Assertions.assertEquals(0, items.size.toLong())

        inventarioService.recoger(maguin.id!!, baculo.id!!)
        inventarioService.recoger(debilucho.id!!, tunica.id!!)

        items = inventarioService.getItemsPersonajesDebiles(5)
        Assertions.assertEquals(1, items.size.toLong())
        Assertions.assertEquals("Tunica", items.iterator().next().nombre)
    }

    @Test
    fun testGetMasPesado() {
        val item = inventarioService.heaviestItem()
        Assertions.assertEquals("Tunica", item.nombre)
    }

    @Test
    fun testMuchoPesoException() {
        inventarioService.recoger(maguin.id!!, baculo.id!!)
        val exception = Assertions.assertThrows(MuchoPesoException::class.java) {
            inventarioService.recoger(maguin.id!!, tunica.id!!)
        }

         Assertions.assertEquals("El personaje [Maguin] no puede recoger [Tunica] porque cagar mucho peso ya", exception.message)
    }

    @Test
    fun testNombreDePersonajeTieneQueSerUnico() {
        val otroMaguin = Personaje("Maguin")
        otroMaguin.pesoMaximo = 70
        otroMaguin.vida = 10

        val exception = Assertions.assertThrows(NombreDePersonajeRepetido::class.java) {
            personajeService.guardarPersonaje(otroMaguin)
        }

        Assertions.assertEquals("El nombre de personaje [Maguin] ya esta siendo utilizado y no puede volver a crearse", exception.message)
    }

    @Test
    fun testPersistEnCascadeAUnaDetachedEntityLanzaDetachedEntityItemException() {
        val espada = Item("Espada", 100)
        inventarioService.guardarItem(espada)

        val otroMaguito = Personaje("Shierke")
        otroMaguito.pesoMaximo = 70
        otroMaguito.vida = 10
        otroMaguito.inventario.add(espada)
        espada.owner = otroMaguito

        val exception = Assertions.assertThrows(InvalidDataAccessApiUsageException::class.java) {
            personajeService.guardarPersonaje(otroMaguito)
        }

        Assertions.assertEquals("detached entity passed to persist: ar.edu.unq.spring.modelo.Item; nested exception is org.hibernate.PersistentObjectException: detached entity passed to persist: ar.edu.unq.spring.modelo.Item", exception.message)
    }

    @Test
    fun deleteTambienPasaADetachedUnaVezTerminadaUnaTransaccion() {
        val espada = Item("Espada", 100)
        inventarioService.guardarItem(espada)
        inventarioService.deleteItem(espada)

        val otroMaguito = Personaje("Shierke")
        otroMaguito.pesoMaximo = 70
        otroMaguito.vida = 10
        otroMaguito.inventario.add(espada)
        espada.owner = otroMaguito

        val exception = Assertions.assertThrows(InvalidDataAccessApiUsageException::class.java) {
            personajeService.guardarPersonaje(otroMaguito)
        }

        Assertions.assertEquals("detached entity passed to persist: ar.edu.unq.spring.modelo.Item; nested exception is org.hibernate.PersistentObjectException: detached entity passed to persist: ar.edu.unq.spring.modelo.Item", exception.message)
    }

    @Test
    fun testSinDetatchedEntityItemException() {
        val espada = Item("Espada", 100)
        inventarioService.guardarItem(espada)

        val otroMaguito = Personaje("Shierke")
        otroMaguito.pesoMaximo = 70
        otroMaguito.vida = 10

        personajeService.guardarPersonaje(otroMaguito)

        otroMaguito.inventario.add(espada)
        espada.owner = otroMaguito

        personajeService.guardarPersonaje(otroMaguito)
        val maguitoRecuperado = personajeService.recuperarPersonaje(otroMaguito.id!!)
        Assertions.assertTrue(maguitoRecuperado!!.inventario.isNotEmpty())
    }

    @Test
    fun testMergeTransientEnCascadaNoFalla() {
        val otroMaguito = Personaje("Shierke")
        otroMaguito.pesoMaximo = 70
        otroMaguito.vida = 10
        personajeService.guardarPersonaje(otroMaguito)

        val espada = Item("Espada", 100)
        otroMaguito.inventario.add(espada)
        espada.owner = otroMaguito


        personajeService.guardarPersonaje(otroMaguito)
    }

    @Transactional
    @Test
    fun testMergeSincronizaLosCambiosHechosAlObjeto() {
        val espada = Item("Espada", 100)
        inventarioService.guardarItem(espada)

        val mismaEspada = inventarioService.getItem(espada.id!!)
        mismaEspada.nombre = "Espada Gastada"
        inventarioService.guardarItem(mismaEspada)

        val otroMaguito = Personaje("Shierke")
        otroMaguito.pesoMaximo = 70
        otroMaguito.vida = 10

        personajeService.guardarPersonaje(otroMaguito)

        otroMaguito.inventario.add(espada)
        mismaEspada.owner = otroMaguito

        personajeService.guardarPersonaje(otroMaguito)

        val maguitoRecuperado = personajeService.recuperarPersonaje(otroMaguito.id!!)
        Assertions.assertEquals(maguitoRecuperado!!.inventario.first().nombre, "Espada Gastada")
    }

    @AfterEach
    fun tearDown() {
        inventarioService.clearAll()
        personajeService.clearAll()
    }
}