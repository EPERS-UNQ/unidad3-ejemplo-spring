package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido;
import ar.edu.unq.spring.persistence.repository.PersonajeRepository;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeRepository personajeRepository;
    public PersonajeServiceImpl(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public Set<Personaje> allPersonajes() {
        return Set.copyOf(personajeRepository.findAll());
    }

    @Override
    public void guardarPersonaje(Personaje personaje) {
        try {
            personajeRepository.save(personaje);
        } catch (DataIntegrityViolationException e) {
            throw new NombreDePersonajeRepetido(personaje.getNombre());
        }
    }

    @Override
    public Personaje recuperarPersonaje(Long personajeId) {
        return personajeRepository.findById(personajeId).orElseThrow(() -> new NoSuchElementException("Personaje not found with id: " + personajeId));
    }

    @Override
    public void clearAll() {
        personajeRepository.deleteAll();
    }
}