package ar.edu.unq.spring.persistence.repository.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.repository.jpa.PersonajeDAO;
import ar.edu.unq.spring.persistence.dto.PersonajeJPADTO;
import ar.edu.unq.spring.persistence.repository.PersonajeRepository;
import ar.edu.unq.spring.persistence.mapper.PersonajeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonajeRepositoryImpl implements PersonajeRepository {

    private final PersonajeDAO personajeDAO;
    private final PersonajeMapper personajeMapper;

    public PersonajeRepositoryImpl(PersonajeDAO personajeDAO,
                                   PersonajeMapper personajeMapper) {
        this.personajeDAO = personajeDAO;
        this.personajeMapper = personajeMapper;
    }

    @Override
    public List<Personaje> findAll() {
        return personajeDAO.findAll().stream()
                .map(personajeMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Personaje> findById(Long id) {
        return personajeDAO.findById(id)
                .map(personajeMapper::toEntity);
    }

    @Override
    public Personaje save(Personaje personaje) {
        PersonajeJPADTO dto = personajeMapper.toDTO(personaje);
        PersonajeJPADTO savedDTO = personajeDAO.save(dto);
        personaje.setId(savedDTO.getId());
        return personaje;
    }

    @Override
    public void deleteAll() {
        personajeDAO.deleteAll();
    }

}
