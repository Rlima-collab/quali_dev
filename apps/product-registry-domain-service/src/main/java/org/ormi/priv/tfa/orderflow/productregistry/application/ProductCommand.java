package org.ormi.priv.tfa.orderflow.productregistry.application;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;

/**
 * Sealed interface defining product command types.
 * <p>
 * Represents different write operations on products:
 * register, retire, update name, and update description.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public sealed interface ProductCommand {
    public record RegisterProductCommand(
            String name,
            String description,
            SkuId skuId) implements ProductCommand {
    }

    public record RetireProductCommand(ProductId productId) implements ProductCommand {
    }

    public record UpdateProductNameCommand(ProductId productId, String newName) implements ProductCommand {
    }

    public record UpdateProductDescriptionCommand(ProductId productId, String newDescription) implements ProductCommand {
    }
}
