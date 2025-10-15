package ar.edu.unq.spring.persistence.mapper;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.persistence.dto.ItemJPADTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    // Ignorar owner para evitar circularidad con PersonajeMapper
    @Mapping(target = "owner", ignore = true)
    ItemJPADTO toDTO(Item item);

    @Mapping(target = "owner", ignore = true)
    Item toEntity(ItemJPADTO dto);
}

