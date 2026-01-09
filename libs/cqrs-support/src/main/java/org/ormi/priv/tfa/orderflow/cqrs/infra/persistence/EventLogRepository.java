package org.ormi.priv.tfa.orderflow.cqrs.infra.persistence;

import org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope;
import org.ormi.priv.tfa.orderflow.cqrs.infra.jpa.EventLogEntity;

/**
 * Repository interface for persisting domain events to the event log.
 * <p>
 * Provides methods to append new events to the immutable event log.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public interface EventLogRepository {
    EventLogEntity append(EventEnvelope<?> eventLog);
}
