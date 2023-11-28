package com.richcode.dto;

import lombok.Builder;

@Builder
public record DocumentDto(Long id,
                          String name,
                          String author,
                          String content,
                          DocumentStatus status) {

    public static DocumentDto empty() {
        return DocumentDto.builder().build();
    }

}
