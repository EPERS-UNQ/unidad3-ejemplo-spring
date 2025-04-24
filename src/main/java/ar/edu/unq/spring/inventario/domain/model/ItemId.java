package ar.edu.unq.spring.inventario.domain.model;

/**
 * Value Object para el ID del Item
 */
public record ItemId(Long value) {

    public static ItemId of(Long value) {
        return new ItemId(value);
    }
} 