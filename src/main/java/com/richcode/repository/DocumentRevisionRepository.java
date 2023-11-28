package com.richcode.repository;

import com.richcode.domain.Document;
import com.richcode.domain.Document_;
import com.richcode.domain.ExtendedRevision_;
import com.richcode.dto.DocumentDto;
import com.richcode.dto.DocumentRevision;
import com.richcode.dto.DocumentStatus;
import com.richcode.dto.RevisionInfo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DocumentRevisionRepository {

    private final EntityManager em;

    public List<DocumentRevision> findAllByDocumentId(final long id) {
        return find(DocumentRevisionSearchCriteria.builder()
            .example(DocumentDto.builder()
                .id(id)
                .build())
            .build());
    }

    @SuppressWarnings("unchecked")
    public List<DocumentRevision> find(final DocumentRevisionSearchCriteria criteria) {
        final AuditQuery query = query();
        DocumentRevisionAuditCriteriaBuilder.builder()
            .build(criteria)
            .forEach(query::add);

        query.addProjection(AuditEntity.revisionNumber());                              // 0
        query.addProjection(AuditEntity.revisionType());                                // 1
        query.addProjection(AuditEntity.revisionProperty(ExtendedRevision_.USER_ID));   // 2
        query.addProjection(AuditEntity.revisionProperty(ExtendedRevision_.TIMESTAMP)); // 3
        query.addProjection(AuditEntity.property(Document_.ID));                        // 4
        query.addProjection(AuditEntity.property(Document_.NAME));                      // 5
        query.addProjection(AuditEntity.property(Document_.AUTHOR));                    // 6
        query.addProjection(AuditEntity.property(Document_.CONTENT));                   // 7
        query.addProjection(AuditEntity.property(Document_.STATUS));                    // 8

        query.addOrder(AuditEntity.revisionNumber().desc());

        return query.getResultList().stream()
            .map(projection -> convert((Object[]) projection))
            .toList();
    }

    private AuditReader auditReader() {
        return AuditReaderFactory.get(em);
    }

    private AuditQuery query() {
        return auditReader().createQuery().forRevisionsOfEntity(Document.class, false, true);
    }

    private static DocumentRevision convert(final Object[] projections) {
        return DocumentRevision.builder()
            .revisionInfo(RevisionInfo.builder()
                .revision((Integer) projections[0])
                .type((RevisionType) projections[1])
                .userId((String) projections[2])
                .timestamp(OffsetDateTime.ofInstant(Instant.ofEpochMilli((Long) projections[3]), ZoneId.systemDefault()))
                .build())
            .element(DocumentDto.builder()
                .id((Long) projections[4])
                .name((String) projections[5])
                .author((String) projections[6])
                .content((String) projections[7])
                .status((DocumentStatus) projections[8])
                .build())
            .build();
    }

}
