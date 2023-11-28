package com.richcode.dto;

import lombok.Builder;
import org.hibernate.envers.RevisionType;

import java.time.OffsetDateTime;

@Builder
public record RevisionInfo(Integer revision,
                           RevisionType type,
                           String userId,
                           OffsetDateTime timestamp) {
}
