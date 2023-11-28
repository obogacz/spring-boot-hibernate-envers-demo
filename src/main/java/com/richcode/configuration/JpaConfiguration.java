package com.richcode.configuration;

import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

import static com.richcode.configuration.JpaConfiguration.DATE_TIME_PROVIDER_NAME;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.*;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = DATE_TIME_PROVIDER_NAME)
class JpaConfiguration {

    public static final String DATE_TIME_PROVIDER_NAME = "offsetDateTimeProvider";

    @Bean(DATE_TIME_PROVIDER_NAME)
    public DateTimeProvider offsetDateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }

}
