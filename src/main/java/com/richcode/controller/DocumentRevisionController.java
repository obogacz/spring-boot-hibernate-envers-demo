package com.richcode.controller;

import com.richcode.dto.DocumentRevision;
import com.richcode.dto.GetDocumentRevisionRequest;
import com.richcode.service.DocumentRevisionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Document Revisions")
@RestController
@RequestMapping("/history/documents")
@RequiredArgsConstructor
public class DocumentRevisionController {

    private final DocumentRevisionService service;

    @Operation(summary = "Search document revisions")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentRevision> search(@PathVariable("id") long id, @ParameterObject GetDocumentRevisionRequest request) {
        return service.find(id, request);
    }

}
