package org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.read;

import java.time.Instant;

/**
 * Data Transfer Object for product event stream elements.
 * <p>
 * Used for Server-Sent Events (SSE) streaming of product changes.
 * Contains event type, product ID, and occurrence timestamp.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public record ProductStreamElementDto(
    String type,
    String productId,
    Instant occuredAt
) {
}
