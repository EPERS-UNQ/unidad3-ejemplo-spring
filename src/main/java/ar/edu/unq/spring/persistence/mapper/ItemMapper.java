package ar.edu.unq.spring.persistence.mapper;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.persistence.dto.ItemJPADTO;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    private final Mapper dozerMapper;

    public ItemMapper(Mapper dozerMapper) {
        this.dozerMapper = dozerMapper;
    }

    public ItemJPADTO toDTO(Item item) {
        return dozerMapper.map(item, ItemJPADTO.class);
    }

    public Item toEntity(ItemJPADTO dto) {
        return dozerMapper.map(dto, Item.class);
    }
}

