package com.richcode.dto;

public enum DocumentStatus {

    DRAFT, PUBLISHED;

    public static boolean isDraft(DocumentStatus status) {
        return null != status && status.isDraft();
    }

    public boolean isDraft() {
        return this == DRAFT;
    }

}
