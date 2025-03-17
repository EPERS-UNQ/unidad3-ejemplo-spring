package ar.edu.unq.spring.domain.model;

/**
 * Value Object para el ID del Personaje
 */
public record PersonajeId(Long value) {

    public static PersonajeId of(Long value) {
        return new PersonajeId(value);
    }
} 