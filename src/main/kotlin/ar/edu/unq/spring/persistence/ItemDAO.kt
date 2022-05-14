package ar.edu.unq.spring.persistence

import ar.edu.unq.spring.modelo.Item
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ItemDAO : CrudRepository<Item, Long> {

    fun findTopByOrderByPesoDesc(): Item

    @Query(
        "from Item i "
                + "where i.owner.vida < ?1 "
                + "order by i.peso asc"
    )
    fun getItemsDePersonajesDebiles(unaVida: Int): Collection<Item>


    @Query(
        "FROM Item i where i.peso  > ?1 order by i.peso asc"
    )
    fun getMasPesados(unValorDado: Int): Collection<Item>

}
