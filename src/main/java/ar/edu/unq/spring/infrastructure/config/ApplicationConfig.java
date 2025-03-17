package ar.edu.unq.spring.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "ar.edu.unq.spring.domain",
        "ar.edu.unq.spring.application",
        "ar.edu.unq.spring.infrastructure"
})
@EntityScan("ar.edu.unq.spring.infrastructure.adapter.persistence.entity")
@EnableJpaRepositories("ar.edu.unq.spring.infrastructure.adapter.persistence.repository")
public class ApplicationConfig {
}
 