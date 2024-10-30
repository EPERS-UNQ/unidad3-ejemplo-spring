package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.ItemDAO;
import ar.edu.unq.spring.persistence.PersonajeDAO;
import ar.edu.unq.spring.service.PersonajeService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service @Transactional
public class PersonajeServiceImpl implements PersonajeService {
    private PersonajeDAO personajeDAO;
    private ItemDAO itemDAO;

    public PersonajeServiceImpl(PersonajeDAO personajeDAO, ItemDAO itemDAO) {
        this.personajeDAO = personajeDAO;
        this.itemDAO = itemDAO;
    }

    @Override
    public void guardarPersonaje(Personaje personaje) {
        personajeDAO.save(personaje);

    }

    @Override
    public Personaje recuperarPersonaje(Long personajeId) {
        return personajeDAO.findById(personajeId).get();
    }

    @Override
    public Personaje recuperarPersonajePorNombre(String nombre) {
        return personajeDAO.findByNombre(nombre);
    }

    @Override
    public void recoger(Long personajeId, Long itemId) {
        Personaje personaje = personajeDAO.findById(personajeId).get();
        Item item = itemDAO.findById(itemId).get();
        personaje.recoger(item);
        personajeDAO.save(personaje);
    }

    @Override
    public Collection<Personaje> allPersonajes() {
        return personajeDAO.findAll();
    }

    @Override
    public Collection<Personaje> personajesPaginados(int size, int page) {
        return personajeDAO.findAll(PageRequest.of(page, size, Sort.by("id")))
                .stream().toList();
    }

    @Override
    public void clearAll() {
        personajeDAO.deleteAll();
    }
}
