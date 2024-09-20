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
import java.util.Set;

// Con @Component le decimos a Spring que inicialice este objeto y lo agregue a su contexto
// Por lo que queda disponible para ser Autowired en otros lugares
@Component
public class MockMVCPersonajeController {

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

    public Personaje recuperarPersonaje(Long personajeId) throws Exception {
        var json = mockMvc.perform(MockMvcRequestBuilders.get("/personaje/" + personajeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var dto = objectMapper.readValue(json, PersonajeDTO.class);
        return dto.aModelo();
    }

    public Set<PersonajeDTO> recuperarTodos() throws Exception {
        var json = mockMvc.perform(MockMvcRequestBuilders.get("/personaje/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var dto = objectMapper.readValue(json, PersonajeDTO[].class);
        return Set.of(dto);
    }
}
