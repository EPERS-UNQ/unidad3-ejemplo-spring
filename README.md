# Ejemplo Spring - Arquitectura Hexagonal orientada al dominio

Este proyecto es un ejemplo de aplicación Spring que utiliza una arquitectura hexagonal (ports & adapters) orientada al dominio.

## Estructura del proyecto

La estructura del proyecto está organizada por dominios, donde cada dominio contiene su propia implementación de los elementos de la arquitectura hexagonal:

```
src/main/java/ar/edu/unq/spring/
├── EjemploSpringApp.java
├── personaje/
│   ├── domain/             # Entidades y objetos de valor
│   │   └── model/          
│   ├── ports/              # Puertos (interfaces)
│   │   ├── input/          # Puertos de entrada (casos de uso)
│   │   └── output/         # Puertos de salida (repositorios)
│   ├── application/        # Implementación de casos de uso
│   │   └── service/     
│   └── adapters/           # Adaptadores
│       ├── persistence/    # Adaptadores de salida (persistencia)
│       │   ├── entity/     # Entidades JPA
│       │   ├── mapper/     # Mappers entidad <-> dominio
│       │   └── repository/ # Repositorios Spring Data JPA
│       └── rest/           # Adaptadores de entrada (controladores)
│           ├── dto/        # DTOs para la comunicación REST
│           └── PersonajeController.java
└── inventario/
    ├── domain/             # Entidades y objetos de valor
    │   └── model/           
    ├── ports/              # Puertos (interfaces)
    │   ├── input/          # Puertos de entrada (casos de uso)
    │   └── output/         # Puertos de salida (repositorios)
    ├── application/        # Implementación de casos de uso
    │   └── service/     
    └── adapters/           # Adaptadores
        ├── persistence/    # Adaptadores de salida (persistencia)
        │   ├── entity/     # Entidades JPA
        │   ├── mapper/     # Mappers entidad <-> dominio
        │   └── repository/ # Repositorios Spring Data JPA
        └── rest/           # Adaptadores de entrada (controladores)
            ├── dto/        # DTOs para la comunicación REST
            └── InventarioController.java
```

## Beneficios de esta estructura

1. **Organización centrada en el dominio**: Cada dominio de negocio (personaje, inventario, etc.) tiene su propia estructura completa.
2. **Cohesión**: Todo lo relacionado con un dominio específico está agrupado, lo que facilita su comprensión y mantenimiento.
3. **Aislamiento**: Los cambios en un dominio tienen menos probabilidades de afectar a otros dominios.
4. **Escalabilidad**: Es más fácil añadir nuevos dominios o expandir los existentes.

## Cómo funciona

- Los **puertos** definen las interfaces que el dominio expone (puertos de entrada) o necesita (puertos de salida).
- La **aplicación** contiene la implementación de los casos de uso que orquestan las operaciones del dominio.
- Los **adaptadores** implementan los puertos para conectar con tecnologías externas:
  - **Adaptadores de entrada**: Controllers REST que convierten peticiones HTTP en llamadas a los casos de uso.
  - **Adaptadores de salida**: Implementaciones de repositorios para persistencia de datos.
- El **dominio** contiene las entidades y la lógica de negocio, y es independiente de cualquier infraestructura. 