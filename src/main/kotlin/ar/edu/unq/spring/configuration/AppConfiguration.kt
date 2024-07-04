package ar.edu.unq.spring.configuration

import ar.edu.unq.spring.service.transaction.SpringRelationalTransaction
import ar.edu.unq.spring.service.transaction.TransactionRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// los Beans se crean aca. Como Para este proyecto chico, solo tenemos componentes de Spring
// Ya spring se encarga de hacer el wiring, sin necesidad que declaremos los objetos aca
@Configuration
class AppConfiguration @Autowired constructor(
    private val springRelationalTransaction: SpringRelationalTransaction
) {
    @Bean
    fun configureTransactionRunner() {
        TransactionRunner.addTransaction(springRelationalTransaction)
    }

}