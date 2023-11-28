package com.richcode.domain;

import com.richcode.dto.DocumentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditTable("document_revision")
@Entity
public class Document {

    @Id
    @GeneratedValue
    private Long id;

    @Audited
    @Column(nullable = false)
    private String name;

    @Audited
    @Column(nullable = false)
    private String author;

    @Audited
    @Column
    private String content;

    @Audited
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status;

    public boolean isDraft() {
        return DocumentStatus.isDraft(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (id == null) return false;
        if (!(o instanceof Document document)) return false;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return 13;
    }

    @Override
    public String toString() {
        return "Document{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", author='" + author + '\'' +
            ", content='" + content + '\'' +
            ", status=" + status +
            '}';
    }
}
