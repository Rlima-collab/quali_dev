package org.ormi.priv.tfa.orderflow.kernel.product.persistence;

import java.util.Optional;

import org.ormi.priv.tfa.orderflow.kernel.Product;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;

/**
 * Repository interface for Product aggregate persistence.
 * <p>
 * Defines the contract for storing and retrieving Product aggregates.
 * Implementations handle the actual persistence mechanism (JPA, etc.).
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public interface ProductRepository {
    void save(Product product);
    Optional<Product> findById(ProductId id);
    boolean existsBySkuId(SkuId skuId);
}
