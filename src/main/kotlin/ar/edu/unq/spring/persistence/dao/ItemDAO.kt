package ar.edu.unq.spring.persistence.dao

import ar.edu.unq.spring.persistence.dto.ItemJPADTO
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ItemDAO : CrudRepository<ItemJPADTO, Long> {

    fun findTopByOrderByPesoDesc(): ItemJPADTO

    @Query(
        "from Item i "
                + "where i.owner.vida < ?1 "
                + "order by i.peso asc"
    )
    fun getItemsDePersonajesDebiles(unaVida: Int): Collection<ItemJPADTO>


    @Query(
            "FROM Item i where i.peso  > ?1 order by i.peso asc"
    )
    fun getMasPesados(unValorDado: Int): Collection<ItemJPADTO>

}
