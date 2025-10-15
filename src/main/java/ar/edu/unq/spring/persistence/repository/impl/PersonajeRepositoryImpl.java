package ar.edu.unq.spring.persistence.repository.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.repository.jpa.PersonajeDAO;
import ar.edu.unq.spring.persistence.repository.PersonajeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonajeRepositoryImpl implements PersonajeRepository {

    private final PersonajeDAO personajeDAO;

    public PersonajeRepositoryImpl(PersonajeDAO personajeDAO) {
        this.personajeDAO = personajeDAO;
    }

    @Override
    public List<Personaje> findAll() {
        return personajeDAO.findAll();
    }

    @Override
    public Optional<Personaje> findById(Long id) {
        return personajeDAO.findById(id);
    }

    @Override
    public Personaje save(Personaje personaje) {
        return personajeDAO.save(personaje);
    }

    @Override
    public void deleteAll() {
        personajeDAO.deleteAll();
    }

}
