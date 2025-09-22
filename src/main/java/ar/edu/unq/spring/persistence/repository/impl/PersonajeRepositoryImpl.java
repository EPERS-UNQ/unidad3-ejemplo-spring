package ar.edu.unq.spring.persistence.repository.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.repository.jpa.PersonajeDAO;
import ar.edu.unq.spring.persistence.dto.PersonajeJPADTO;
import ar.edu.unq.spring.persistence.repository.PersonajeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonajeRepositoryImpl implements PersonajeRepository {

    private final PersonajeDAO personajeDAO;

    public PersonajeRepositoryImpl(PersonajeDAO personajeDAO) {
        this.personajeDAO = personajeDAO;
    }

    @Override
    public List<Personaje> findAll() {
        return personajeDAO.findAll().stream()
                .map(PersonajeJPADTO::aModelo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Personaje> findById(Long id) {
        return personajeDAO.findById(id)
                .map(PersonajeJPADTO::aModelo);
    }

    @Override
    public Personaje save(Personaje personaje) {
        PersonajeJPADTO dto = PersonajeJPADTO.desdeModelo(personaje);
        PersonajeJPADTO savedDTO = personajeDAO.save(dto);
        personaje.setId(savedDTO.getId());
        return personaje;
    }

    @Override
    public void deleteAll() {
        personajeDAO.deleteAll();
    }

}
