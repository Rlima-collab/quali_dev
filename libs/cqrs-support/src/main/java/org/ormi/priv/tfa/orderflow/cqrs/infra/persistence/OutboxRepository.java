package org.ormi.priv.tfa.orderflow.cqrs.infra.persistence;

import java.util.List;

import org.ormi.priv.tfa.orderflow.cqrs.infra.jpa.OutboxEntity;

/**
 * Repository interface for managing outbox entries.
 * <p>
 * Provides methods to publish, fetch, delete, and mark failed outbox entries
 * for reliable event delivery using the Transactional Outbox pattern.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public interface OutboxRepository {
    void publish(OutboxEntity entity);
    List<OutboxEntity> fetchReadyByAggregateTypeOrderByAggregateVersion(String aggregateType, int limit, int maxRetries);
    void delete(OutboxEntity entity);
    void markFailed(OutboxEntity entity, String err);
    void markFailed(OutboxEntity entity, String err, int retryAfter);
}
