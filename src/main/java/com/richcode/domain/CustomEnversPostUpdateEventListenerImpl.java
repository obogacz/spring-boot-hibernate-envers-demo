package com.richcode.domain;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPostUpdateEventListenerImpl;
import org.hibernate.event.spi.PostUpdateEvent;

@Slf4j
public class CustomEnversPostUpdateEventListenerImpl extends EnversPostUpdateEventListenerImpl {
    public CustomEnversPostUpdateEventListenerImpl(EnversService enversService) {
        super(enversService);
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof Document document && document.isDraft()) {
            log.info("Creation of a revision of Document was skipped. Document in DRAFT status.");
            return;
        }
        super.onPostUpdate(event);
    }
}
