package ar.edu.unq.spring.persistence.mapper;

import ar.edu.unq.spring.modelo.Guerrero;
import ar.edu.unq.spring.modelo.Mago;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.dto.GuerreroJPADTO;
import ar.edu.unq.spring.persistence.dto.MagoJPADTO;
import ar.edu.unq.spring.persistence.dto.PersonajeJPADTO;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Component;

@Component
public class PersonajeMapper {

    private final Mapper dozerMapper;

    public PersonajeMapper(Mapper dozerMapper) {
        this.dozerMapper = dozerMapper;
    }

    public PersonajeJPADTO toDTO(Personaje personaje) {
        return dozerMapper.map(personaje, PersonajeJPADTO.class);
    }

    public Personaje toEntity(PersonajeJPADTO dto) {
        return dozerMapper.map(dto, Personaje.class);
    }
}

