package com.richcode.dto;

import lombok.Builder;

@Builder
public record DocumentRevision(RevisionInfo revisionInfo, DocumentDto element) {
}
