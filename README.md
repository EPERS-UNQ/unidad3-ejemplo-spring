# Ejemplo Spring - Arquitectura Hexagonal

## Estructura del proyecto

Este es un punto de discusion en como llevar adelante hexagonal. Muchos proyectos dividen por dominio, otros directamente por las distintas capas.
Mi preferencia personal es dividir por dominio, asi que fui con eso, donde cada dominio contiene su propia implementación de los elementos de la arquitectura hexagonal. 


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
## Cómo funciona

- Los **puertos** definen las interfaces que el dominio expone (puertos de entrada) o necesita (puertos de salida).
- La **aplicación** contiene la implementación de los casos de uso que orquestan las operaciones del dominio.
- Los **adaptadores** implementan los puertos para conectar con tecnologías externas:
  - **Adaptadores de entrada**: Controllers REST que convierten peticiones HTTP en llamadas a los casos de uso.
  - **Adaptadores de salida**: Implementaciones de repositorios para persistencia de datos.
- El **dominio** contiene las entidades y la lógica de negocio, y es independiente de cualquier infraestructura. 
