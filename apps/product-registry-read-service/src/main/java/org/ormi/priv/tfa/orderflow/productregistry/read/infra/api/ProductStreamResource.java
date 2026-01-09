package org.ormi.priv.tfa.orderflow.productregistry.read.infra.api;

import jakarta.ws.rs.Path;

/**
 * REST resource for product event streaming via SSE.
 * <p>
 * Provides Server-Sent Events endpoints for real-time
 * product change notifications. To be implemented in Exercise 5.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
@Path("/products")
public class ProductStreamResource {

    // TODO: implement [Exercice 5]
    // private final ReadProductService readProductService;
    // private final ProductIdMapper productIdMapper;

    // @Inject
    // public ProductStreamResource(
    //         ReadProductService readProductService,
    //         ProductIdMapper productIdMapper) {
    //     this.readProductService = readProductService;
    //     this.productIdMapper = productIdMapper;
    // }

    // TODO: implement [Exercice 5]
    // @GET
    // @Path("/{id}/pending/stream")
    // @RestStreamElementType(MediaType.APPLICATION_JSON)
    // public Multi<ProductStreamElementDto> streamPendingOutboxMessagesByProdutId(
    //         @PathParam("id") String id) {
    //     throw new UnsupportedOperationException("TODO: implement [Exercice 5]");
    // }
}
