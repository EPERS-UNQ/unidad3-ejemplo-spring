package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Item;

import java.util.Collection;

public interface ItemService {
    void guardarItem(Item item);
    Collection<Item> getMasPesados(int peso);
    Item heaviestItem();
    Collection<Item> getItemsPersonajesDebiles(int vida);
    Collection<Item> allItems();
    void clearAll();
}
