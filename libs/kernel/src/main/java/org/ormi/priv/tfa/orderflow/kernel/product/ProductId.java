package org.ormi.priv.tfa.orderflow.kernel.product;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Value object representing a unique identifier for a Product.
 * <p>
 * Uses UUID as the underlying identifier type to ensure global uniqueness.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public record ProductId(@NotNull UUID value) {
    public static ProductId newId() {
        return new ProductId(UUID.randomUUID());
    }
}
