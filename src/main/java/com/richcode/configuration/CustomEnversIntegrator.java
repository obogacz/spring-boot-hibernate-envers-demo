package com.richcode.configuration;

import com.google.auto.service.AutoService;
import com.richcode.domain.CustomEnversPostUpdateEventListenerImpl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.spi.BootstrapContext;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.*;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

@Slf4j
@AutoService(Integrator.class)
@SuppressWarnings("NullableProblems")
public class CustomEnversIntegrator implements Integrator {

    @Override
    public void integrate(Metadata metadata,
                          BootstrapContext bootstrapContext,
                          SessionFactoryImplementor sessionFactory) {

        final ServiceRegistry serviceRegistry = sessionFactory.getServiceRegistry();
        final EnversService enversService = serviceRegistry.getService(EnversService.class);
        if (!enversService.isInitialized()) {
            throw new HibernateException(
                "Expecting EnversService to have been initialized prior to call to EnversIntegrator#integrate"
            );
        }

        if (!enversService.getEntitiesConfigurations().hasAuditedEntities()) {
            log.debug("Skipping Envers listener registrations : No audited entities found");
            return;
        }

        final EventListenerRegistry listenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
        listenerRegistry.addDuplicationStrategy(EnversListenerDuplicationStrategy.INSTANCE);
        if (enversService.getEntitiesConfigurations().hasAuditedEntities()) {
            listenerRegistry.appendListeners(
                EventType.POST_DELETE,
                new EnversPostDeleteEventListenerImpl(enversService)
            );
            listenerRegistry.appendListeners(
                EventType.POST_INSERT,
                new EnversPostInsertEventListenerImpl(enversService)
            );
            listenerRegistry.appendListeners(
                EventType.PRE_UPDATE,
                new EnversPreUpdateEventListenerImpl(enversService)
            );
            listenerRegistry.appendListeners(
                EventType.POST_UPDATE,
                new CustomEnversPostUpdateEventListenerImpl(enversService)
            );
            listenerRegistry.appendListeners(
                EventType.POST_COLLECTION_RECREATE,
                new EnversPostCollectionRecreateEventListenerImpl(enversService)
            );
            listenerRegistry.appendListeners(
                EventType.PRE_COLLECTION_REMOVE,
                new EnversPreCollectionRemoveEventListenerImpl(enversService)
            );
            listenerRegistry.appendListeners(
                EventType.PRE_COLLECTION_UPDATE,
                new EnversPreCollectionUpdateEventListenerImpl(enversService)
            );
        }
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        // nothing to do
    }
}
