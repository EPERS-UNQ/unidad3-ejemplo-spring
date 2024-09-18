package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido;
import ar.edu.unq.spring.persistence.dao.PersonajeDAO;
import ar.edu.unq.spring.persistence.dto.PersonajeJPADTO;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeDAO personajeDAO;
    public PersonajeServiceImpl(PersonajeDAO personajeDAO) {
        this.personajeDAO = personajeDAO;
    }

    @Override
    public Set<Personaje> allPersonajes() {
            return Set.copyOf(personajeDAO.findAll())
                    .stream()
                    .map(PersonajeJPADTO::aModelo)
                    .collect(Collectors.toSet());
    }

    @Override
    public Long guardarPersonaje(Personaje personaje) {
        try {
            PersonajeJPADTO personajeJPADTO = PersonajeJPADTO.desdeModelo(personaje);
            personajeDAO.save(personajeJPADTO);
            personaje.setId(personajeJPADTO.getId());
            return personaje.getId();
        } catch (DataIntegrityViolationException e) {
            throw new NombreDePersonajeRepetido(personaje.getNombre());
        }
    }


    @Override
    public Personaje recuperarPersonaje(Long personajeId) {
        return personajeDAO.findById(personajeId)
                .orElseThrow(() -> new NoSuchElementException("Personaje not found with id: " + personajeId))
                .aModelo();
    }

    @Override
    public void clearAll() {
        personajeDAO.deleteAll();
    }
}