package ar.edu.unq.spring.service.transaction

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

@Component
class SpringRelationalTransaction @Autowired constructor(
    private val transactionManager: PlatformTransactionManager
) : Transaction {

    private var transactionStatus: TransactionStatus? = null

    override fun start() {
        val definition = DefaultTransactionDefinition()
        definition.propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRED
        transactionStatus = transactionManager.getTransaction(definition)
    }

    override fun commit() {
        transactionStatus?.let {
            transactionManager.commit(it)
        }
    }

    override fun rollback() {
        transactionStatus?.let {
            transactionManager.rollback(it)
        }
    }
}
