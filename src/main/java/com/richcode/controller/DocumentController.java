package com.richcode.controller;

import com.richcode.dto.DocumentDto;
import com.richcode.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Documents")
@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @Operation(summary = "Get a document")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public DocumentDto get(@PathVariable("id") Long id) {
        return service.find(id);
    }

    @Operation(summary = "Create a document")
    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentDto create(@RequestBody DocumentDto dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update a document")
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public DocumentDto update(@PathVariable("id") Long id, @RequestBody DocumentDto dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a document")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

}


