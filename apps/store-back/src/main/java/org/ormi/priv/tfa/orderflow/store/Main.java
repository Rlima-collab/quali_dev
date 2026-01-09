package org.ormi.priv.tfa.orderflow.store;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Main entry point for the Store Backend Service.
 * <p>
 * Quarkus application that serves as a facade/BFF for the store,
 * orchestrating calls to product registry services.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
@QuarkusMain
public class Main {

    public static void main(String... args) {
        Quarkus.run(
            ProductRegistryDomainApplication.class,
            (exitCode, exception) -> {},
            args);
    }

    public static class ProductRegistryDomainApplication implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            Quarkus.waitForExit();
            return 0;
        }
    }
}
