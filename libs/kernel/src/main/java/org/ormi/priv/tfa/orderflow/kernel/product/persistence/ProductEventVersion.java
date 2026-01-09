package org.ormi.priv.tfa.orderflow.kernel.product.persistence;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1;

/**
 * Enumeration of product event versions.
 * <p>
 * Used for event versioning and backward compatibility
 * when deserializing events from the event store.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public enum ProductEventVersion {
    V1(ProductEventV1.EVENT_VERSION);

    private final int value;

    ProductEventVersion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
