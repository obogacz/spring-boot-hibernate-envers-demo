package com.richcode.service;

import com.richcode.dto.DocumentDto;
import com.richcode.dto.DocumentRevision;
import com.richcode.dto.GetDocumentRevisionRequest;
import com.richcode.repository.DocumentRevisionRepository;
import com.richcode.repository.DocumentRevisionSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRevisionService {

    private final DocumentRevisionRepository revisionRepository;

    public List<DocumentRevision> find(final long id, final GetDocumentRevisionRequest request) {
        DocumentRevisionSearchCriteria criteria = DocumentRevisionSearchCriteria.builder()
            .revision(request.getRevision())
            .example(DocumentDto.builder()
                .id(id)
                .name(request.getDocumentName())
                .author(request.getDocumentAuthorLike())
                .content(request.getDocumentContentLike())
                .status(request.getDocumentStatus())
                .build())
            .build();
        return revisionRepository.find(criteria);
    }

}
