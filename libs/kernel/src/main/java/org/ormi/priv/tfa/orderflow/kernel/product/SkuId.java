package org.ormi.priv.tfa.orderflow.kernel.product;

import jakarta.validation.constraints.NotNull;

/**
 * Value object representing a Stock Keeping Unit (SKU) identifier.
 * <p>
 * The SKU follows a strict format: 3 uppercase letters followed by a hyphen and 5 digits.
 * Example: "ABC-12345"
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
public record SkuId(@NotNull String value) {
    private static final java.util.regex.Pattern SKU_PATTERN =
        java.util.regex.Pattern.compile("^[A-Z]{3}-\\d{5}$");

    public SkuId {
        if (!SKU_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid SKU format, expected [Alpha]{3}-[Digit]{5}");
        }
    }
}
