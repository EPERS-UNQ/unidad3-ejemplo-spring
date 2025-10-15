package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.service.interfaces.ResetService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
public class ResetServiceImpl implements ResetService {

    @PersistenceContext
    private EntityManager entityManager;


    public ResetServiceImpl() {

    }

    @Override
    public void resetAll() {
        limpiarPostgreSQL();
    }

    private void limpiarPostgreSQL() {
        Set<String> tablasExistentes = obtenerTablasExistentes();

        if (tablasExistentes.isEmpty()) {
            return;
        }

        for (String tableName : tablasExistentes) {
            limpiarTabla(tableName);
        }
    }



    private Set<String> obtenerTablasExistentes() {
        Set<String> tablas = new java.util.HashSet<>();

        try {
            String query = """
                    SELECT table_name 
                    FROM information_schema.tables 
                    WHERE table_schema = 'public' 
                    AND table_type = 'BASE TABLE'
                    """;

            @SuppressWarnings("unchecked")
            java.util.List<String> resultados = entityManager.createNativeQuery(query).getResultList();

            for (String tabla : resultados) {
                tablas.add(tabla);
            }

        } catch (Exception e) {
            // Fallback: usar las entidades JPA
            Set<EntityType<?>> entityTypes = entityManager.getMetamodel().getEntities();
            for (EntityType<?> entityType : entityTypes) {
                tablas.add(getTableName(entityType));
            }
        }

        return tablas;
    }

    private void limpiarTabla(String tableName) {

        try {
            String truncateQuery = "TRUNCATE TABLE " + tableName + " CASCADE";
            entityManager.createNativeQuery(truncateQuery).executeUpdate();
        } catch (Exception truncateException) {
            // Si TRUNCATE falla, intentar DELETE
            String deleteQuery = "DELETE FROM " + tableName;
            entityManager.createNativeQuery(deleteQuery).executeUpdate();
        }
    }

    private String getTableName(EntityType<?> entityType) {
        Class<?> entityClass = entityType.getJavaType();

        // Verificar si tiene anotación @Table personalizada
        jakarta.persistence.Table tableAnnotation = entityClass.getAnnotation(jakarta.persistence.Table.class);

        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }

        // Si no tiene @Table, usar el nombre de la clase pero con convención de nombres
        String className = entityClass.getSimpleName();

        // PostgreSQL generalmente usa snake_case, pero primero intentar tal como está
        // Si falla, Hibernate podría estar usando una convención diferente
        return className;
    }
}