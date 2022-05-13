package ar.edu.unq.spring

import ar.edu.unq.spring.persistence.ItemDAO
import ar.edu.unq.spring.persistence.PersonajeDAO
import ar.edu.unq.spring.service.InventarioService
import ar.edu.unq.spring.service.InventarioServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
class InventarioServiceTestConfig {

    @Autowired
    lateinit var personajeDAO: PersonajeDAO

    @Autowired
    lateinit var itemDAO: ItemDAO

    @Bean
    fun service(opersonajeDAO: PersonajeDAO, itemDAO: ItemDAO): InventarioService {
        return InventarioServiceImp(personajeDAO, itemDAO)
    }

}

