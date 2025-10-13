package ar.edu.unq.spring.persistence.repository.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.repository.jpa.PersonajeDAO;
import ar.edu.unq.spring.persistence.dto.PersonajeJPADTO;
import ar.edu.unq.spring.persistence.repository.PersonajeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonajeRepositoryImpl implements PersonajeRepository {

    private final PersonajeDAO personajeDAO;
    private final ModelMapper modelMapper;

    public PersonajeRepositoryImpl(PersonajeDAO personajeDAO,
                                   ModelMapper modelMapper) {
        this.personajeDAO = personajeDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Personaje> findAll() {
        return personajeDAO.findAll().stream().map((element) -> modelMapper.map(element, Personaje.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<Personaje> findById(Long id) {
        return personajeDAO.findById(id).map((element) -> modelMapper.map(element, Personaje.class));
    }

    @Override
    public Personaje save(Personaje personaje) {
        PersonajeJPADTO dto = modelMapper.map(personaje, PersonajeJPADTO.class);
        PersonajeJPADTO savedDTO = personajeDAO.save(dto);
        personaje.setId(savedDTO.getId());
        return personaje;
    }

    @Override
    public void deleteAll() {
        personajeDAO.deleteAll();
    }

}
