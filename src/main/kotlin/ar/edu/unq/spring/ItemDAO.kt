package ar.edu.unq.spring

import ar.edu.unq.spring.modelo.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ItemDAO : JpaRepository<Item, Long> {

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
