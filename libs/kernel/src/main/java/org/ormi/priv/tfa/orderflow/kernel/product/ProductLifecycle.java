package org.ormi.priv.tfa.orderflow.kernel.product;

/**
 * Enumeration representing the lifecycle states of a Product.
 * <p>
 * A product can be in one of the following states:
 * <ul>
 *   <li>{@link #ACTIVE} - The product is available and can be modified</li>
 *   <li>{@link #RETIRED} - The product is no longer available and cannot be modified</li>
 * </ul>
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public enum ProductLifecycle {
    ACTIVE,
    RETIRED
}
