package org.ormi.priv.tfa.orderflow.productregistry.read.application;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;

/**
 * Sealed interface defining product query types.
 * <p>
 * Represents different query operations for the product read model:
 * get by ID, list all, and search by SKU pattern.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public sealed interface ProductQuery {
    public record GetProductByIdQuery(ProductId productId) implements ProductQuery {
    }

    public record ListProductQuery(int page, int size) implements ProductQuery {
    }

    public record ListProductBySkuIdPatternQuery(String skuIdPattern, int page, int size) implements ProductQuery {
    }
}
