package com.richcode.repository;

import com.richcode.domain.Document_;
import com.richcode.dto.DocumentDto;
import com.richcode.dto.DocumentStatus;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.criteria.MatchMode;

import java.util.LinkedList;
import java.util.List;

public class DocumentRevisionAuditCriteriaBuilder {

    private final List<AuditCriterion> auditCriteria = new LinkedList<>();

    public static DocumentRevisionAuditCriteriaBuilder builder() {
        return new DocumentRevisionAuditCriteriaBuilder();
    }

    public DocumentRevisionAuditCriteriaBuilder hasRevision(Long rev) {
        if (null != rev) {
            auditCriteria.add(AuditEntity.revisionNumber().eq(rev));
        }
        return this;
    }

    public DocumentRevisionAuditCriteriaBuilder hasRevisionType(RevisionType type) {
        if (null != type) {
            auditCriteria.add(AuditEntity.revisionType().eq(type));
        }
        return this;
    }

    public DocumentRevisionAuditCriteriaBuilder hasId(Long id) {
        if (null != id) {
            auditCriteria.add(AuditEntity.id().eq(id));
        }
        return this;
    }

    public DocumentRevisionAuditCriteriaBuilder hasName(String name) {
        if (StringUtils.isNotEmpty(name)) {
            auditCriteria.add(AuditEntity.property(Document_.NAME).eq(name));
        }
        return this;
    }

    public DocumentRevisionAuditCriteriaBuilder hasAuthorLike(String author) {
        if (StringUtils.isNotEmpty(author)) {
            auditCriteria.add(AuditEntity.property(Document_.AUTHOR).ilike(author, MatchMode.ANYWHERE));
        }
        return this;
    }

    public DocumentRevisionAuditCriteriaBuilder hasContentLike(String content) {
        if (StringUtils.isNotEmpty(content)) {
            auditCriteria.add(AuditEntity.property(Document_.CONTENT).ilike(content, MatchMode.ANYWHERE));
        }
        return this;
    }

    public DocumentRevisionAuditCriteriaBuilder hasStatus(DocumentStatus status) {
        if (null != status) {
            auditCriteria.add(AuditEntity.property(Document_.STATUS).eq(status));
        }
        return this;
    }

    public List<AuditCriterion> build() {
        return auditCriteria;
    }

    public List<AuditCriterion> build(final DocumentRevisionSearchCriteria searchCriteria) {
        final DocumentDto example = searchCriteria.example();
        return builder()
            .hasRevision(searchCriteria.revision())
            .hasRevisionType(searchCriteria.revisionType())
            .hasId(example.id())
            .hasName(example.name())
            .hasAuthorLike(example.author())
            .hasContentLike(example.content())
            .hasStatus(example.status())
            .build();
    }

}
