package ar.edu.unq.spring.persistence.mapper;

import ar.edu.unq.spring.modelo.Guerrero;
import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Mago;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.dto.GuerreroJPADTO;
import ar.edu.unq.spring.persistence.dto.ItemJPADTO;
import ar.edu.unq.spring.persistence.dto.MagoJPADTO;
import ar.edu.unq.spring.persistence.dto.PersonajeJPADTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class PersonajeMapper {


    @Mapping(target = "inventario", ignore = true)
    public abstract GuerreroJPADTO guerreroToDTO(Guerrero guerrero);

    @Mapping(target = "inventario", ignore = true)
    public abstract Guerrero guerreroToEntity(GuerreroJPADTO dto);

    @Mapping(target = "inventario", ignore = true)
    public abstract MagoJPADTO magoToDTO(Mago mago);

    @Mapping(target = "inventario", ignore = true)
    public abstract Mago magoToEntity(MagoJPADTO dto);


    public PersonajeJPADTO toDTO(Personaje personaje) {
        return switch (personaje) {
            case Guerrero g -> guerreroToDTO(g);
            case Mago m -> magoToDTO(m);
            default -> null;
        };
    }


    public Personaje toEntity(PersonajeJPADTO dto) {
        return switch (dto) {
            case GuerreroJPADTO g -> guerreroToEntity(g);
            case MagoJPADTO m -> magoToEntity(m);
            default -> null;
        };
    }


    @AfterMapping
    protected void mapInventarioToDTO(@MappingTarget PersonajeJPADTO target, Personaje source) {

        Set<ItemJPADTO> inventarioDTO = new HashSet<>();
        for (Item item : source.getInventario()) {
            ItemJPADTO itemDTO = new ItemJPADTO();
            itemDTO.setId(item.getId());
            itemDTO.setNombre(item.getNombre());
            itemDTO.setPeso(item.getPeso());
            itemDTO.setOwner(target);
            inventarioDTO.add(itemDTO);
        }
        target.setInventario(inventarioDTO);

    }


    @AfterMapping
    protected void mapInventarioToEntity(@MappingTarget Personaje target, PersonajeJPADTO source) {
        Set<Item> inventario = new HashSet<>();
        for (ItemJPADTO itemDTO : source.getInventario()) {
            Item item = new Item(itemDTO.getNombre(), itemDTO.getPeso());
            item.setId(itemDTO.getId());
            item.setOwner(target);
            inventario.add(item);
        }
        target.setInventario(inventario);
    }

}

