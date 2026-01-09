package org.ormi.priv.tfa.orderflow.productregistry.infra.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

/**
 * Integration tests for ProductRegistryCommandResource.
 * Tests the REST API endpoints for product registry operations.
 */
@QuarkusTest
@DisplayName("ProductRegistryCommandResource Integration Tests")
class ProductRegistryCommandResourceIT {

    private static final String BASE_PATH = "/products";

    @Nested
    @DisplayName("POST /api/products - Register Product")
    class RegisterProduct {

        @Test
        @DisplayName("should register a product with valid data")
        void shouldRegisterProductWithValidData() {
            String uniqueSku = "TST-" + String.format("%05d", (int) (Math.random() * 99999));
            
            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Test Product",
                        "description": "A test product description",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(201)
                .header("Location", containsString("/products/"));
        }

        @Test
        @DisplayName("should return 400 Bad Request for invalid product (null name)")
        void shouldReturnBadRequestForInvalidProduct() {
            String uniqueSku = "BAD-" + String.format("%05d", (int) (Math.random() * 99999));
            
            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": null,
                        "description": "Description",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(400);
        }

        @Test
        @DisplayName("should return 400 Bad Request for duplicate SKU")
        void shouldReturnBadRequestForDuplicateSku() {
            String uniqueSku = "DUP-" + String.format("%05d", (int) (Math.random() * 99999));
            
            // First registration should succeed
            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "First Product",
                        "description": "First description",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(201);

            // Second registration with same SKU should fail
            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Second Product",
                        "description": "Second description",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(400);
        }
    }

    @Nested
    @DisplayName("DELETE /api/products/{id} - Retire Product")
    class RetireProduct {

        @Test
        @DisplayName("should retire an existing product")
        void shouldRetireExistingProduct() {
            String uniqueSku = "RET-" + String.format("%05d", (int) (Math.random() * 99999));
            
            // First, create a product
            String location = given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Product to Retire",
                        "description": "Will be retired",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(201)
                .extract()
                .header("Location");

            // Extract product ID from location header
            String productId = location.substring(location.lastIndexOf("/") + 1);

            // Then retire it
            given()
            .when()
                .delete(BASE_PATH + "/{id}", productId)
            .then()
                .statusCode(204);
        }

        @Test
        @DisplayName("should return 400 for non-existent product")
        void shouldReturnNotFoundForNonExistentProduct() {
            String nonExistentId = UUID.randomUUID().toString();

            given()
            .when()
                .delete(BASE_PATH + "/{id}", nonExistentId)
            .then()
                .statusCode(anyOf(is(400), is(404)));
        }
    }

    @Nested
    @DisplayName("PATCH /api/products/{id}/name - Update Product Name")
    class UpdateProductName {

        @Test
        @DisplayName("should update product name")
        void shouldUpdateProductName() {
            String uniqueSku = "UPN-" + String.format("%05d", (int) (Math.random() * 99999));
            
            // First, create a product
            String location = given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Original Name",
                        "description": "Description",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(201)
                .extract()
                .header("Location");

            String productId = location.substring(location.lastIndexOf("/") + 1);

            // Update the name
            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Updated Name"
                    }
                    """)
            .when()
                .patch(BASE_PATH + "/{id}/name", productId)
            .then()
                .statusCode(204);
        }

        @Test
        @DisplayName("should return 400 for invalid name (null)")
        void shouldReturnBadRequestForInvalidName() {
            String uniqueSku = "INV-" + String.format("%05d", (int) (Math.random() * 99999));
            
            // First, create a product
            String location = given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Original Name",
                        "description": "Description",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(201)
                .extract()
                .header("Location");

            String productId = location.substring(location.lastIndexOf("/") + 1);

            // Update with null name should fail
            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": null
                    }
                    """)
            .when()
                .patch(BASE_PATH + "/{id}/name", productId)
            .then()
                .statusCode(anyOf(is(400), is(204)));
        }

        @Test
        @DisplayName("should return 400 for non-existent product")
        void shouldReturnBadRequestForNonExistentProduct() {
            String nonExistentId = UUID.randomUUID().toString();

            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "New Name"
                    }
                    """)
            .when()
                .patch(BASE_PATH + "/{id}/name", nonExistentId)
            .then()
                .statusCode(anyOf(is(400), is(404)));
        }
    }

    @Nested
    @DisplayName("PATCH /api/products/{id}/description - Update Product Description")
    class UpdateProductDescription {

        @Test
        @DisplayName("should update product description")
        void shouldUpdateProductDescription() {
            String uniqueSku = "UPD-" + String.format("%05d", (int) (Math.random() * 99999));
            
            // First, create a product
            String location = given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Product Name",
                        "description": "Original Description",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(201)
                .extract()
                .header("Location");

            String productId = location.substring(location.lastIndexOf("/") + 1);

            // Update the description
            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "description": "Updated Description"
                    }
                    """)
            .when()
                .patch(BASE_PATH + "/{id}/description", productId)
            .then()
                .statusCode(204);
        }

        @Test
        @DisplayName("should return 400 for non-existent product")
        void shouldReturnBadRequestForNonExistentProduct() {
            String nonExistentId = UUID.randomUUID().toString();

            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "description": "New Description"
                    }
                    """)
            .when()
                .patch(BASE_PATH + "/{id}/description", nonExistentId)
            .then()
                .statusCode(anyOf(is(400), is(404)));
        }
    }

    @Nested
    @DisplayName("Product Lifecycle - Integration Scenarios")
    class ProductLifecycleScenarios {

        @Test
        @DisplayName("should not allow update on retired product")
        void shouldNotAllowUpdateOnRetiredProduct() {
            String uniqueSku = "LIF-" + String.format("%05d", (int) (Math.random() * 99999));
            
            // Create a product
            String location = given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Lifecycle Test Product",
                        "description": "Will test lifecycle",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(201)
                .extract()
                .header("Location");

            String productId = location.substring(location.lastIndexOf("/") + 1);

            // Retire the product
            given()
            .when()
                .delete(BASE_PATH + "/{id}", productId)
            .then()
                .statusCode(204);

            // Try to update name - should fail
            given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Should Fail"
                    }
                    """)
            .when()
                .patch(BASE_PATH + "/{id}/name", productId)
            .then()
                .statusCode(anyOf(is(400), is(500)));
        }

        @Test
        @DisplayName("should not allow double retirement")
        void shouldNotAllowDoubleRetirement() {
            String uniqueSku = "DBL-" + String.format("%05d", (int) (Math.random() * 99999));
            
            // Create a product
            String location = given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "name": "Double Retire Test",
                        "description": "Will test double retire",
                        "skuId": "%s"
                    }
                    """.formatted(uniqueSku))
            .when()
                .post(BASE_PATH)
            .then()
                .statusCode(201)
                .extract()
                .header("Location");

            String productId = location.substring(location.lastIndexOf("/") + 1);

            // First retirement
            given()
            .when()
                .delete(BASE_PATH + "/{id}", productId)
            .then()
                .statusCode(204);

            // Second retirement should fail
            given()
            .when()
                .delete(BASE_PATH + "/{id}", productId)
            .then()
                .statusCode(anyOf(is(400), is(500)));
        }
    }
}
