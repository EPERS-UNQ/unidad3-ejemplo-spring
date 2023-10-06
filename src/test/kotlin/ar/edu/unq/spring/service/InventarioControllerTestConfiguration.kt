package ar.edu.unq.spring.service

import ar.edu.unq.spring.controller.InventarioControllerREST
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@Configuration
// Le indicamos a la configuracion a partir de que paquete tiene que "escanear"
// por componentes (@Service, @Controller, @Component y DAOs) para levantar y agregar al contexto.
// Decirle que haga un escaneo desde el root es poco eficiente, pero nos sirve en un ejemplo chico

// Ahora, [or que en los tests hace falta agregar un component scan y en la configuracion del mainApp (EjemploSpringApp.kt) no?
// Por que el main app cuando lo corres fuerza un scan desde la carpeta donde esta corriendo para abajo.
// Y no casualmente la pusimos en el root del proyecto por esa misma razon.
// El test hace lo mismo, fuerza un scan del test para abajo,
// pero como no encuentra a los DAOs, service y controllers (No estan en las carpetas del test para abajo)
// Hay que agregarle el @ComponentScan a la configuracion para que vaya a escanear por los objetos que nos faltan.
@ComponentScan(basePackages = ["ar.edu.unq.spring"])
class InventarioControllerTestConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    fun mockMvc(inventarioController: InventarioControllerREST): MockMvc {
        return MockMvcBuilders.standaloneSetup(inventarioController).build()
    }
}
