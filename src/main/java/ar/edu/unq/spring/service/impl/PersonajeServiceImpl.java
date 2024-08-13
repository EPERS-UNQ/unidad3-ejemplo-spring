package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido;
import ar.edu.unq.spring.persistence.PersonajeDAO;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    private PersonajeDAO personajeDAO;

    @Override
    public Iterable<Personaje> allPersonajes() {
        return personajeDAO.findAll();
    }

    @Override
    public void guardarPersonaje(Personaje personaje) {
        try {
            personajeDAO.save(personaje);
        } catch (DataIntegrityViolationException e) {
            throw new NombreDePersonajeRepetido(personaje.getNombre());
        }
    }

    @Override
    public Optional<Personaje> recuperarPersonaje(Long personajeId) {
        return personajeDAO.findById(personajeId);
    }

    @Override
    public void clearAll() {
        personajeDAO.deleteAll();
    }
}