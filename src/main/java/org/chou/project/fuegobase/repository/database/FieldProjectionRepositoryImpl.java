package org.chou.project.fuegobase.repository.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.repository.query.Param;

import java.util.List;

public class FieldProjectionRepositoryImpl implements FieldProjectionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FieldProjection> fetchAllFieldsByDocumentId(@Param("document_id") long documentId) {
        String query = """
                SELECT k.id AS id, k.name, t.type_name AS keyType, v.key_name AS keyName, 
                    v.value_name AS valueName, ft.type_name AS valueType 
                FROM field_key k 
                JOIN field_value v ON k.id = v.field_key_id 
                JOIN field_type t ON k.type_id = t.id 
                JOIN field_type ft ON v.value_type_id = ft.id 
                WHERE k.document_id = %d
                """;
        return entityManager.createNativeQuery(query.formatted(documentId), FieldProjection.class).getResultList();
    }

    @Override
    public List<Long> getDocumentIdsByFilter(String keyName, String valueName, String type) {
        String query = """
                SELECT d.id
                FROM document d
                JOIN field_key k ON d.id = k.document_id
                JOIN field_value v ON k.id = v.field_key_id
                JOIN field_type ft ON v.value_type_id = ft.id
                WHERE k.name = ?1 AND v.value_name = ?2 AND ft.type_name = ?3
                 """;

        return entityManager.createNativeQuery(query, Long.class)
                .setParameter(1, keyName)
                .setParameter(2, valueName)
                .setParameter(3, type)
                .getResultList();
    }
}
