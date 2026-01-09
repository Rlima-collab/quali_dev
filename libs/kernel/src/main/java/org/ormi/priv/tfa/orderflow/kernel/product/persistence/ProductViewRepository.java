package org.ormi.priv.tfa.orderflow.kernel.product.persistence;

import java.util.List;
import java.util.Optional;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;
import org.ormi.priv.tfa.orderflow.kernel.product.views.ProductView;

/**
 * Repository interface for ProductView read model persistence.
 * <p>
 * Defines the contract for storing and querying ProductView projections.
 * Supports pagination and search operations for read-side queries.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public interface ProductViewRepository {
    void save(ProductView productView);
    Optional<ProductView> findById(ProductId id);
    Optional<ProductView> findBySkuId(SkuId skuId);
    long countPaginatedViewsBySkuIdPattern(String skuIdPattern);
    List<ProductView> searchPaginatedViewsOrderBySkuId(String skuIdPattern, int page, int size);
}
