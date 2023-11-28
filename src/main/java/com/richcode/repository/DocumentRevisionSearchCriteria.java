package com.richcode.repository;

import com.richcode.dto.DocumentDto;
import lombok.Builder;
import org.hibernate.envers.RevisionType;

import java.util.Objects;

@Builder
public record DocumentRevisionSearchCriteria(Long revision, RevisionType revisionType, DocumentDto example) {

    public DocumentRevisionSearchCriteria {
        example = Objects.requireNonNullElseGet(example, DocumentDto::empty);
    }

}
