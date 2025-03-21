package ar.edu.unq.spring.persistence.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.persistence.ItemDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ItemDAOImpl implements ItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            entityManager.persist(item);
            return item;
        } else {
            return entityManager.merge(item);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Item.class, id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Item> findAll() {
        return entityManager.createQuery("from Item", Item.class).getResultList();
    }

    @Transactional
    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Item").executeUpdate();
    }

    @Transactional
    @Override
    public void delete(Item item) {
        entityManager.createQuery("delete from Item as i where i.id = :i").setParameter("i", item.getId()).executeUpdate();
    }

    @Override
    public Item findTopByOrderByPesoDesc() {
        return entityManager.createQuery("FROM Item i order by i.peso desc limit 1", Item.class).getSingleResult();
    }

    @Override
    public Set<Item> getMasPesados(int peso) {
        TypedQuery<Item> query = entityManager.createQuery("FROM Item i where i.peso > :p order by i.peso asc", Item.class);
        return query.setParameter("p", peso).getResultStream().collect(Collectors.toSet());
    }

    @Override
    public Set<Item> getItemsDePersonajesDebiles(int vida) {
        TypedQuery<Item> query = entityManager.createQuery( "from Item i where i.owner.vida < :v order by i.peso asc", Item.class);
        return query.setParameter("v", vida).getResultStream().collect(Collectors.toSet());
    }
}
