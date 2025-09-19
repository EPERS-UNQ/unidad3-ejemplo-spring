package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Item;

import java.util.Collection;
import java.util.Optional;

public interface InventarioService {
    Optional<Item> getItem(Long itemId);
    Collection<Item> allItems();
    Item heaviestItem();
    Item guardarItem(Item item);
    void recoger(Long personajeId, Long itemId);
    Collection<Item> getMasPesados(int peso);
    Collection<Item> getItemsPersonajesDebiles(int vida);
    void clearAll();
    void deleteItem(Item item);
}