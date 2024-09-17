package ar.edu.unq.spring.controller.helper;

import ar.edu.unq.spring.modelo.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

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

    public Long guardarItem(Item item) {

    }

}
