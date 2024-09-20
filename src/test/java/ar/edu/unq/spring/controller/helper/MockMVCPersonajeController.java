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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private Long guardarPersonaje(Personaje personaje, HttpStatus expectedStatus) throws Throwable {
        var dto = PersonajeDTO.desdeModelo(personaje);
        var json = objectMapper.writeValueAsString(dto);

        return Long.parseLong(
                performRequest(MockMvcRequestBuilders.post("/personaje")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(MockMvcResultMatchers.status().is(expectedStatus.value()))
                        .andReturn().getResponse().getContentAsString()
        );
    }

    public Long guardarPersonaje(Personaje personaje) throws Throwable {
        return guardarPersonaje(personaje, HttpStatus.CREATED);
    }

    public Personaje recuperarPersonaje(Long personajeId) throws Throwable {
        var json = performRequest(MockMvcRequestBuilders.get("/personaje/" + personajeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var dto = objectMapper.readValue(json, PersonajeDTO.class);
        return dto.aModelo();
    }

    public Collection<Personaje> recuperarTodos() throws Throwable {
        var json = performRequest(MockMvcRequestBuilders.get("/personaje/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Collection<PersonajeDTO> dtos = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, PersonajeDTO.class)
        );

        return dtos.stream().map(PersonajeDTO::aModelo).collect(Collectors.toList());
    }
}
