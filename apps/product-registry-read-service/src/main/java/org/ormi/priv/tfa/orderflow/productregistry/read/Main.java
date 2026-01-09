package org.ormi.priv.tfa.orderflow.productregistry.read;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Main entry point for the Product Registry Read Service.
 * <p>
 * Quarkus application that handles read-side queries and projections
 * for the product registry bounded context.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
@QuarkusMain
public class Main {

    public static void main(String... args) {
        Quarkus.run(
            ProductRegistryReadApplication.class,
            (exitCode, exception) -> {},
            args);
    }

    public static class ProductRegistryReadApplication implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            Quarkus.waitForExit();
            return 0;
        }
    }
}
