package ar.edu.unq.spring.inventario.adapters.infra.persistence.repository;

import ar.edu.unq.spring.inventario.adapters.infra.persistence.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByDueñoId(Long personajeId);
} 