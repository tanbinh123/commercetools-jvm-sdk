package io.sphere.sdk.models;

/**
 * Represents an object itself or a {@link io.sphere.sdk.models.Reference} to it.
 * It is not necessarily the case that the reference is filled.
 *
 * @param <T> the type of the referenced object.
 */
public interface Referenceable<T> {
    Reference<T> toReference();
}
