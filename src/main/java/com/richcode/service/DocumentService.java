package com.richcode.service;

import com.richcode.domain.Document;
import com.richcode.dto.DocumentDto;
import com.richcode.repository.DocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentDto find(final long id) {
        return documentRepository
            .findById(id)
            .map(this::convertToDto)
            .orElseThrow(EntityNotFoundException::new);
    }

    public DocumentDto create(final DocumentDto dto) {
        Document saved = documentRepository.save(convertToEntity(dto));
        return convertToDto(saved);
    }

    public DocumentDto update(final long id, final DocumentDto dto) {
        return documentRepository
            .findById(id)
            .map(entity -> {
                entity.setName(dto.name());
                entity.setAuthor(dto.author());
                entity.setContent(dto.content());
                entity.setStatus(dto.status());
                return documentRepository.save(entity);
            })
            .map(this::convertToDto)
            .orElseThrow(EntityNotFoundException::new);
    }

    public void delete(final long id) {
        documentRepository
            .findById(id)
            .ifPresentOrElse(
                documentRepository::delete,
                () -> { throw new EntityNotFoundException(); });
    }

    private Document convertToEntity(final DocumentDto dto) {
        return Document.builder()
            .id(dto.id())
            .name(dto.name())
            .author(dto.author())
            .content(dto.content())
            .status(dto.status())
            .build();
    }

    private DocumentDto convertToDto(final Document entity) {
        return DocumentDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .author(entity.getAuthor())
            .content(entity.getContent())
            .status(entity.getStatus())
            .build();
    }

}
