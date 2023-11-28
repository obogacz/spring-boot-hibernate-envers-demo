package com.richcode.domain;

import org.hibernate.envers.RevisionListener;

public class ExtendedRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        if (revisionEntity instanceof ExtendedRevision revision) {
            revision.setUserId(getUserId());
        }
    }

    private String getUserId() {
        return "username"; // implementation depends on a tech stack
    }

}
