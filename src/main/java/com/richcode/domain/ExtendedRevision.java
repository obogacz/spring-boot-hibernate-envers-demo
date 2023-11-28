package com.richcode.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Getter
@Setter
@Entity
@Table(name = "revision_info")
@RevisionEntity(ExtendedRevisionListener.class)
public class ExtendedRevision extends DefaultRevisionEntity {

    private String userId;

}
