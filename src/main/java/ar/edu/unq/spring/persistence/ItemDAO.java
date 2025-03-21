package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Item;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;


public interface ItemDAO {
    Item save(Item item);
    Optional<Item> findById(Long id);
    Collection<Item> findAll();
    void deleteAll();
    void delete(Item item);
    Item findTopByOrderByPesoDesc();
    Set<Item> getMasPesados(int peso);
    Set<Item> getItemsDePersonajesDebiles(int vida);
}