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

    public Long guardarItem(Item item) throws Exception {
        return guardarItem(item, HttpStatus.CREATED);
    }

    private Long guardarItem(Item item, HttpStatus expectedStatus) throws Exception {
        var dto = ItemDTO.desdeModelo(item);
        var json = objectMapper.writeValueAsString(dto);

        return Long.parseLong(
                mockMvc.perform(MockMvcRequestBuilders.post("/inventario")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(MockMvcResultMatchers.status().is(expectedStatus.value()))
                        .andReturn().getResponse().getContentAsString()
        );
    }

    public Collection<Item> allItems() throws Throwable {
        var json = performRequest(MockMvcRequestBuilders.get("/inventario/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Collection<ItemDTO> dtos = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class)
        );

        return dtos.stream().map(ItemDTO::aModelo).toList();
    }

    public void recoger(Long personajeId, Long itemId, HttpStatus expectedStatus) throws Throwable {
        performRequest(MockMvcRequestBuilders.put("/inventario/personaje/" + personajeId + "/recoger/item/" + itemId))
                .andExpect(MockMvcResultMatchers.status().is(expectedStatus.value()));
    }

    public Item heaviestItem() throws Throwable {
        var json = performRequest(MockMvcRequestBuilders.get("/inventario/itemMasPesado"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var dto = objectMapper.readValue(json, ItemDTO.class);
        return dto.aModelo();
    }

    public Collection<Item> getMasPesados(int peso) throws Throwable {
        var json = performRequest(MockMvcRequestBuilders.get("/inventario/masPesados/" + peso))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Collection<ItemDTO> dtos = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class)
        );

        return dtos.stream().map(ItemDTO::aModelo).toList();
    }

    public Collection<Item> getItemsPersonajesDebiles(int vida) throws Throwable {
        var json = performRequest(MockMvcRequestBuilders.get("/inventario/itemsPersonajesDebiles/" + vida))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Collection<ItemDTO> dtos = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class)
        );

        return dtos.stream().map(ItemDTO::aModelo).toList();
    }
}
