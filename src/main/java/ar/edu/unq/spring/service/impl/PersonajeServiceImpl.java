package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido;
import ar.edu.unq.spring.persistence.PersonajeDAO;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeDAO personajeDAO;
    public PersonajeServiceImpl(PersonajeDAO personajeDAO) {
        this.personajeDAO = personajeDAO;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Personaje> allPersonajes() {
        return Set.copyOf(personajeDAO.findAll());
    }

    @Override
    public void guardarPersonaje(Personaje personaje) {
        try {
            System.out.println(entityState("Pre guardado, dentro de la sesión", personaje));
            personajeDAO.save(personaje);
        } catch (DataIntegrityViolationException e) {
            throw new NombreDePersonajeRepetido(personaje.getNombre());
        }
    }

    @Override
    public Personaje recuperarPersonaje(Long personajeId) {
        return personajeDAO.findById(personajeId).orElseThrow(() -> new NoSuchElementException("Personaje not found with id: " + personajeId));
    }

    @Override
    public void clearAll() {
        personajeDAO.deleteAll();
    }

    @Override
    public String entityState(String moment, Personaje personaje) {
        Session session = entityManager.unwrap(Session.class);
        if (session.contains(personaje)) {
            return "[" + moment + "]" + " | <PERSISTED> | " + personaje.getNombre();
        } else if (personaje.getId() == null) {
            return "[" + moment + "]" + " | <TRANSIENT> | " + personaje.getNombre();
        } else {
            return "[" + moment + "]" + " | <DETACHED> | " + personaje.getNombre();
        }
    }
}