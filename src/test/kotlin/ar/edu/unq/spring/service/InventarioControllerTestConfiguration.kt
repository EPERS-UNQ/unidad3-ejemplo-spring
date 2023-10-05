package ar.edu.unq.spring.service

import ar.edu.unq.spring.controller.InventarioControllerREST
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@Configuration
@ComponentScan(basePackages = ["ar.edu.unq.spring"])
class InventarioControllerTestConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    fun mockMvc(inventarioController: InventarioControllerREST): MockMvc {
        return MockMvcBuilders.standaloneSetup(inventarioController).build()!!
    }
}
