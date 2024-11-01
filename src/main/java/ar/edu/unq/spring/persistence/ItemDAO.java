package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ItemDAO extends JpaRepository<Item, Long> {

    Item findTopByOrderByPesoDesc();

    @Query("FROM Item i WHERE i.owner.vida < ?1 ORDER BY i.peso ASC")
    Collection<Item> getItemsDePersonajesDebiles(int unaVida);

    @Query("FROM Item i WHERE i.peso > ?1 ORDER BY i.peso ASC")
    Collection<Item> getMasPesados(int unValorDado);
}
