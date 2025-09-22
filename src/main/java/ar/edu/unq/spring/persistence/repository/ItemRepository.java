package ar.edu.unq.spring.persistence.repository;

import ar.edu.unq.spring.modelo.Item;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemRepository {
    List<Item> findAll();
    Optional<Item> findById(Long id);
    Item save(Item item);
    void delete(Item item);
    void deleteAll();

    Item findTopByOrderByPesoDesc();
    Set<Item> getMasPesados(int peso);
    Set<Item> getItemsDePersonajesDebiles(int vida);
}
