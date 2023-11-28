package com.richcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDocumentRevisionRequest {
    private Long revision;
    private String documentName;
    private String documentAuthorLike;
    private String documentContentLike;
    private DocumentStatus documentStatus;
}
