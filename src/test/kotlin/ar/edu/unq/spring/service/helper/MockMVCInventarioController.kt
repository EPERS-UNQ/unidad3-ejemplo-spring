package ar.edu.unq.spring.service.helper

import ar.edu.unq.spring.controller.InventarioControllerREST
import ar.edu.unq.spring.controller.dto.ItemJsonDTO
import ar.edu.unq.spring.controller.dto.PersonajeJsonDTO
import ar.edu.unq.spring.modelo.Item
import ar.edu.unq.spring.modelo.Personaje
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@Component
class MockMVCInventarioController(
        @Autowired
        private val inventarioController: InventarioControllerREST
) {

    lateinit var mockMvc: MockMvc
    lateinit var objectMapper: ObjectMapper

    //En realidad es mejor armar y pasar estas cosas en un archivo de @Configuration de Beans de Spring.
    //Para simplificar el ejemplo, nos ahorramos ese archivo y lo hacemos aca.
    fun init() {
        mockMvc = MockMvcBuilders.standaloneSetup(inventarioController).build()
        objectMapper = ObjectMapper()
    }

    fun guardarPersonaje(personaje: Personaje): Long {
        val dto = PersonajeJsonDTO.desdeModelo(personaje)
        val json = objectMapper.writeValueAsString(dto)
        return mockMvc.perform(
                post("/inventario/personaje")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().toLong()
    }

    fun guardarItem(item: Item): Long {
        val dto = ItemJsonDTO.desdeModelo(item)
        val json = objectMapper.writeValueAsString(dto)

        return mockMvc.perform(
                post("/inventario")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().toLong()
    }

    fun recuperarPersonaje(personajeId: Long): Personaje {
        val json = mockMvc.perform(get("/inventario/personaje/$personajeId"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()

        val dto = objectMapper.readValue(json, PersonajeJsonDTO::class.java)
        return dto.aModelo()
    }

    fun recuperarItem(itemId: Long): Item {
        val json = mockMvc.perform(get("/inventario/$itemId"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()

        val dto = objectMapper.readValue(json, ItemJsonDTO::class.java)
        return dto.aModelo()
    }

    fun recoger(personajeId: Long, itemId: Long) {
        mockMvc.perform(put("/inventario/recoger/$personajeId/$itemId"))
                .andExpect(status().isOk())
    }

    fun allItems(): Collection<Item> {
        val json = mockMvc.perform(get("/inventario/allItems"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()

        val dtos: Collection<ItemJsonDTO> = objectMapper.readValue(json, objectMapper.typeFactory.constructCollectionType(MutableList::class.java, ItemJsonDTO::class.java))
        return dtos.map { dto -> dto.aModelo() }
    }

    fun getMasPesados(peso: Int): Collection<Item> {
        val json = mockMvc.perform(get("/inventario/masPesados/$peso"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()

        val dtos: Collection<ItemJsonDTO> = objectMapper.readValue(json, objectMapper.typeFactory.constructCollectionType(MutableList::class.java, ItemJsonDTO::class.java))
        return dtos.map { dto -> dto.aModelo() }
    }

    fun getItemsPersonajesDebiles(vida: Int): Collection<Item> {
        val json = mockMvc.perform(get("/inventario/itemsPersonajesDebiles/$vida"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()

        val dtos: Collection<ItemJsonDTO> = objectMapper.readValue(json, objectMapper.typeFactory.constructCollectionType(MutableList::class.java, ItemJsonDTO::class.java))
        return dtos.map { dto -> dto.aModelo() }
    }

    fun heaviestItem(): Item {
        val json = mockMvc.perform(get("/inventario/heaviestItem"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()

        val dto = objectMapper.readValue(json, ItemJsonDTO::class.java)
        return dto.aModelo()
    }

}