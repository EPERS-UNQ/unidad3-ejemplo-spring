package ar.edu.unq.spring.persistence.dao;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.persistence.dto.ItemJPADTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemDAO extends JpaRepository<ItemJPADTO, Long> {

    ItemJPADTO findTopByOrderByPesoDesc();

    @Query(
            "FROM Item i where i.peso  > :peso order by i.peso asc"
    )
    Set<ItemJPADTO> getMasPesados(@Param("peso") int peso);

    @Query(
            "from Item i "
                    + "where i.owner.vida < ?1 "
                    + "order by i.peso asc"
    )
    Set<ItemJPADTO> getItemsDePersonajesDebiles(int vida);
}