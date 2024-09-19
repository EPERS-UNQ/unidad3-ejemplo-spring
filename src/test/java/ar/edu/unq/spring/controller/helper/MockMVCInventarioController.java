package ar.edu.unq.spring.controller.helper;

import ar.edu.unq.spring.controller.dto.ItemDTO;
import ar.edu.unq.spring.controller.dto.PersonajeDTO;
import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.List;

// Con @Component le decimos a Spring que inicialice este objeto y lo agregue a su contexto
// Por lo que queda disponible para ser Autowired en otros lugares
@Component
public class MockMVCInventarioController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // MockMVC por default re-throwea "NestedServletException" cuando le llega un error.
    // Aca estamos esperando por esa excepcion, y re-throweamos la causa.
    // De esa forma, podemos testear tranquilos y esperar las excepciones de negocio y applicacion del otro lado.
    public ResultActions performRequest(MockHttpServletRequestBuilder requestBuilder) throws Throwable {
        try {
            return mockMvc.perform(requestBuilder);
        } catch (ServletException e) {
            throw e.getCause();
        }
    }

    public Long guardarPersonaje(Personaje personaje, HttpStatus expectedStatus) throws Exception {
        var dto = PersonajeDTO.desdeModelo(personaje);
        var json = objectMapper.writeValueAsString(dto);

        return Long.parseLong(
                mockMvc.perform(MockMvcRequestBuilders.post("/personaje")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(MockMvcResultMatchers.status().is(expectedStatus.value()))
                        .andReturn().getResponse().getContentAsString()
        );
    }

    public Long guardarItem(Item item) throws Exception {
        var dto = ItemDTO.desdeModelo(item);
        var json = objectMapper.writeValueAsString(dto);

        return Long.parseLong(
                mockMvc.perform(MockMvcRequestBuilders.post("/inventario")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn().getResponse().getContentAsString()
        );
    }

    public Personaje recuperarPersonaje(Long personajeId) throws Exception {
        var json = mockMvc.perform(MockMvcRequestBuilders.get("/personaje/" + personajeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var dto = objectMapper.readValue(json, PersonajeDTO.class);
        return dto.aModelo();
    }

    public Item recuperarItem(Long itemId) throws Exception {
        var json = mockMvc.perform(MockMvcRequestBuilders.get("/inventario/" + itemId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var dto = objectMapper.readValue(json, ItemDTO.class);
        return dto.aModelo();
    }

    public void recoger(Long personajeId, Long itemId) throws Exception {
        recoger(personajeId, itemId, HttpStatus.OK);
    }

    private void recoger(Long personajeId, Long itemId, HttpStatus expectedStatus) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/inventario/personaje/" + personajeId + "/recoger/item/" + itemId))
                .andExpect(MockMvcResultMatchers.status().is(expectedStatus.value()));
    }

    public Collection<Item> allItems() throws Exception {
        var json = mockMvc.perform(MockMvcRequestBuilders.get("/inventario/allItems"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Collection<ItemDTO> dtos = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class)
        );

        return dtos.stream().map(ItemDTO::aModelo).toList();
    }

    public Collection<Item> getMasPesados(int peso) throws Exception {
        var json = mockMvc.perform(MockMvcRequestBuilders.get("/inventario/masPesados/" + peso))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Collection<ItemDTO> dtos = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class)
        );

        return dtos.stream().map(ItemDTO::aModelo).toList();
    }

    public Collection<Item> getItemsPersonajesDebiles(int vida) throws Exception {
        var json = mockMvc.perform(MockMvcRequestBuilders.get("/inventario/itemsPersonajesDebiles/" + vida))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Collection<ItemDTO> dtos = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class)
        );

        return dtos.stream().map(ItemDTO::aModelo).toList();
    }

    public Item heaviestItem() throws Exception {
        var json = mockMvc.perform(MockMvcRequestBuilders.get("/inventario/heaviestItem"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var dto = objectMapper.readValue(json, ItemDTO.class);
        return dto.aModelo();
    }
}
