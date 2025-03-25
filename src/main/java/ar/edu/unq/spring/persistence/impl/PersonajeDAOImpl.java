package ar.edu.unq.spring.persistence.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.PersonajeDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonajeDAOImpl implements PersonajeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Personaje save(Personaje personaje) {
        if (personaje.getId() == null) {
            entityManager.persist(personaje);
            return personaje;
        } else {
            return entityManager.merge(personaje);
        }
    }

    @Override
    public Optional<Personaje> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Personaje.class, id));
    }

    @Override
    public List<Personaje> findAll() {
        return entityManager.createQuery("from Personaje", Personaje.class).getResultList();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Personaje").executeUpdate();
    }

    @Override
    public void delete(Personaje personaje) {
        entityManager.createQuery("delete from Personaje as p where p.id = :i")
                .setParameter("i", personaje.getId()).executeUpdate();
    }

}
