package ar.edu.unq.spring.service.helper

import ar.edu.unq.spring.controller.InventarioControllerREST
import ar.edu.unq.spring.controller.dto.ItemJsonDTO
import ar.edu.unq.spring.controller.dto.PersonajeJsonDTO
import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.NestedServletException
import java.lang.reflect.Proxy

// Con @Component le decimos a Spring que inicialise este objeto y lo agregue a su contexto
// Por lo que queda disponible para ser Autowired en otro u otros lugares
@Component
class MockMVCInventarioController {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var objectMapper: ObjectMapper

    // MockMVC por default re-throwea "NestedServletException" cuando le llega un error.
    // Aca estamos esperando por esa excepcion, y re-throweamos la causa.
    // De esa forma, podemos testear tranquilos y esperar las excepciones de negocio y applicacion del otro lado.
    private fun performRequest(requestBuilder: MockHttpServletRequestBuilder): ResultActions {
        return try {
            mockMvc.perform(requestBuilder)
        } catch (e: NestedServletException) {
            throw e.cause ?: e
        }
    }

    fun guardarPersonaje(personaje: Personaje, expectedStatus: HttpStatus = HttpStatus.OK): Long {
        val dto = PersonajeJsonDTO.desdeModelo(personaje)
        val json = objectMapper.writeValueAsString(dto)
        return performRequest(
            post("/inventario/personaje")
                .contentType(MediaType.APPLICATION_JSON).content(json)
        )
            .andExpect(status().`is`(expectedStatus.value()))
            .andReturn().getResponse().getContentAsString().toLong()
    }

    fun guardarItem(item: Item): Long {
        val dto = ItemJsonDTO.desdeModelo(item)
        val json = objectMapper.writeValueAsString(dto)

        return performRequest(
            post("/inventario")
                .contentType(MediaType.APPLICATION_JSON).content(json)
        )
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString().toLong()
    }

    fun recuperarPersonaje(personajeId: Long): Personaje {
        val json = performRequest(get("/inventario/personaje/$personajeId"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString()

        val dto = objectMapper.readValue(json, PersonajeJsonDTO::class.java)
        return dto.aModelo()
    }

    fun recuperarItem(itemId: Long): Item {
        val json = performRequest(get("/inventario/$itemId"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString()

        val dto = objectMapper.readValue(json, ItemJsonDTO::class.java)
        return dto.aModelo()
    }

    fun recoger(personajeId: Long, itemId: Long, expectedStatus: HttpStatus = HttpStatus.OK) {
        performRequest(put("/inventario/recoger/$personajeId/$itemId"))
            .andExpect(status().`is`(expectedStatus.value()))
    }

    fun allItems(): Collection<Item> {
        val json = performRequest(get("/inventario/allItems"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString()

        val dtos: Collection<ItemJsonDTO> = objectMapper.readValue(
            json,
            objectMapper.typeFactory.constructCollectionType(MutableList::class.java, ItemJsonDTO::class.java)
        )
        return dtos.map { dto -> dto.aModelo() }
    }

    fun getMasPesados(peso: Int): Collection<Item> {
        val json = performRequest(get("/inventario/masPesados/$peso"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString()

        val dtos: Collection<ItemJsonDTO> = objectMapper.readValue(
            json,
            objectMapper.typeFactory.constructCollectionType(MutableList::class.java, ItemJsonDTO::class.java)
        )
        return dtos.map { dto -> dto.aModelo() }
    }

    fun getItemsPersonajesDebiles(vida: Int): Collection<Item> {
        val json = performRequest(get("/inventario/itemsPersonajesDebiles/$vida"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString()

        val dtos: Collection<ItemJsonDTO> = objectMapper.readValue(
            json,
            objectMapper.typeFactory.constructCollectionType(MutableList::class.java, ItemJsonDTO::class.java)
        )
        return dtos.map { dto -> dto.aModelo() }
    }

    fun heaviestItem(): Item {
        val json = performRequest(get("/inventario/heaviestItem"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString()

        val dto = objectMapper.readValue(json, ItemJsonDTO::class.java)
        return dto.aModelo()
    }

}