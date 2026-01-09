package org.ormi.priv.tfa.orderflow.kernel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductDescriptionUpdated;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductNameUpdated;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductRetired;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductLifecycle;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;

import jakarta.validation.ConstraintViolationException;

/**
 * Unit tests for the Product aggregate.
 * Tests cover creation, updates, retirement, and validation scenarios.
 */
@DisplayName("Product")
class ProductTest {

    private static final String VALID_SKU = "ABC-12345";
    private static final String VALID_NAME = "Test Product";
    private static final String VALID_DESCRIPTION = "A test product description";

    @Nested
    @DisplayName("create - static factory method")
    class Create {

        @Test
        @DisplayName("should create a product with valid parameters")
        void shouldCreateProductWithValidParameters() {
            // Given
            SkuId skuId = new SkuId(VALID_SKU);

            // When
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, skuId);

            // Then
            assertNotNull(product);
            assertNotNull(product.getId());
            assertEquals(VALID_NAME, product.getName());
            assertEquals(VALID_DESCRIPTION, product.getDescription());
            assertEquals(skuId, product.getSkuId());
            assertEquals(ProductLifecycle.ACTIVE, product.getStatus());
            assertEquals(1L, product.getVersion());
        }

        @Test
        @DisplayName("should return a product with ACTIVE status")
        void shouldReturnProductWithActiveStatus() {
            // Given
            SkuId skuId = new SkuId(VALID_SKU);

            // When
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, skuId);

            // Then
            assertEquals(ProductLifecycle.ACTIVE, product.getStatus());
        }

        @Test
        @DisplayName("should throw exception for null name")
        void shouldThrowExceptionForNullName() {
            // Given
            SkuId skuId = new SkuId(VALID_SKU);

            // When & Then
            assertThrows(ConstraintViolationException.class, () -> {
                Product.create(null, VALID_DESCRIPTION, skuId);
            });
        }

        @Test
        @DisplayName("should throw exception for blank name")
        void shouldThrowExceptionForBlankName() {
            // Given
            SkuId skuId = new SkuId(VALID_SKU);

            // When & Then
            assertThrows(ConstraintViolationException.class, () -> {
                Product.create("", VALID_DESCRIPTION, skuId);
            });
        }

        @Test
        @DisplayName("should throw exception for null SKU")
        void shouldThrowExceptionForNullSku() {
            // When & Then
            assertThrows(ConstraintViolationException.class, () -> {
                Product.create(VALID_NAME, VALID_DESCRIPTION, null);
            });
        }

        @Test
        @DisplayName("should throw exception for invalid SKU format")
        void shouldThrowExceptionForInvalidSkuFormat() {
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> {
                new SkuId("invalid-sku");
            });
        }
    }

    @Nested
    @DisplayName("updateName")
    class UpdateName {

        @Test
        @DisplayName("should update name of an active product")
        void shouldUpdateNameOfActiveProduct() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));
            String newName = "Updated Product Name";

            // When
            EventEnvelope<ProductNameUpdated> event = product.updateName(newName);

            // Then
            assertEquals(newName, product.getName());
            assertEquals(2L, product.getVersion());
            assertNotNull(event);
            assertEquals(2L, event.sequence());
        }

        @Test
        @DisplayName("should increment version after update")
        void shouldIncrementVersionAfterUpdate() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));
            Long initialVersion = product.getVersion();

            // When
            product.updateName("New Name");

            // Then
            assertEquals(initialVersion + 1, product.getVersion());
        }

        @Test
        @DisplayName("should throw exception when updating retired product")
        void shouldThrowExceptionWhenUpdatingRetiredProduct() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));
            product.retire();

            // When & Then
            assertThrows(IllegalStateException.class, () -> {
                product.updateName("New Name");
            });
        }

        @Test
        @DisplayName("should throw exception for null name")
        void shouldThrowExceptionForNullName() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));

            // When & Then
            assertThrows(ConstraintViolationException.class, () -> {
                product.updateName(null);
            });
        }

        @Test
        @DisplayName("should throw exception for blank name")
        void shouldThrowExceptionForBlankName() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));

            // When & Then
            assertThrows(ConstraintViolationException.class, () -> {
                product.updateName("");
            });
        }
    }

    @Nested
    @DisplayName("updateDescription")
    class UpdateDescription {

        @Test
        @DisplayName("should update description of an active product")
        void shouldUpdateDescriptionOfActiveProduct() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));
            String newDescription = "Updated description";

            // When
            EventEnvelope<ProductDescriptionUpdated> event = product.updateDescription(newDescription);

            // Then
            assertEquals(newDescription, product.getDescription());
            assertEquals(2L, product.getVersion());
            assertNotNull(event);
        }

        @Test
        @DisplayName("should throw exception when updating retired product")
        void shouldThrowExceptionWhenUpdatingRetiredProduct() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));
            product.retire();

            // When & Then
            assertThrows(IllegalStateException.class, () -> {
                product.updateDescription("New Description");
            });
        }
    }

    @Nested
    @DisplayName("retire")
    class Retire {

        @Test
        @DisplayName("should retire an active product")
        void shouldRetireActiveProduct() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));

            // When
            EventEnvelope<ProductRetired> event = product.retire();

            // Then
            assertEquals(ProductLifecycle.RETIRED, product.getStatus());
            assertEquals(2L, product.getVersion());
            assertNotNull(event);
        }

        @Test
        @DisplayName("should throw exception when retiring already retired product")
        void shouldThrowExceptionWhenRetiringRetiredProduct() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));
            product.retire();

            // When & Then
            assertThrows(IllegalStateException.class, () -> {
                product.retire();
            });
        }

        @Test
        @DisplayName("should increment version after retirement")
        void shouldIncrementVersionAfterRetirement() {
            // Given
            Product product = Product.create(VALID_NAME, VALID_DESCRIPTION, new SkuId(VALID_SKU));
            Long initialVersion = product.getVersion();

            // When
            product.retire();

            // Then
            assertEquals(initialVersion + 1, product.getVersion());
        }
    }

    @Nested
    @DisplayName("Builder")
    class Builder {

        @Test
        @DisplayName("should build product with all fields")
        void shouldBuildProductWithAllFields() {
            // Given
            ProductId id = ProductId.newId();
            SkuId skuId = new SkuId(VALID_SKU);

            // When
            Product product = Product.Builder()
                    .id(id)
                    .name(VALID_NAME)
                    .description(VALID_DESCRIPTION)
                    .skuId(skuId)
                    .status(ProductLifecycle.ACTIVE)
                    .version(1L)
                    .build();

            // Then
            assertEquals(id, product.getId());
            assertEquals(VALID_NAME, product.getName());
            assertEquals(VALID_DESCRIPTION, product.getDescription());
            assertEquals(skuId, product.getSkuId());
            assertEquals(ProductLifecycle.ACTIVE, product.getStatus());
            assertEquals(1L, product.getVersion());
        }

        @Test
        @DisplayName("should throw exception for invalid product via builder")
        void shouldThrowExceptionForInvalidProductViaBuilder() {
            // When & Then
            assertThrows(ConstraintViolationException.class, () -> {
                Product.Builder()
                        .id(null) // Invalid: null ID
                        .name(VALID_NAME)
                        .description(VALID_DESCRIPTION)
                        .skuId(new SkuId(VALID_SKU))
                        .status(ProductLifecycle.ACTIVE)
                        .version(1L)
                        .build();
            });
        }
    }
}
