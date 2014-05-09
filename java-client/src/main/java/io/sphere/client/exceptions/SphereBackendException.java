package io.sphere.client.exceptions;

import io.sphere.client.SphereError;
import io.sphere.internal.errors.SphereErrorResponse;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Generic exception thrown when a Sphere web service responds with a status code other than HTTP 2xx.
 */
public class SphereBackendException extends SphereException {
    private final String requestUrl;
    private final SphereErrorResponse errorResponse;

    public SphereBackendException(String requestUrl, @Nonnull SphereErrorResponse errorResponse) {
        super(String.format("Error response from Sphere: %s\n%s", requestUrl, errorResponse));
        if (errorResponse == null) throw new NullPointerException("errorResponse");
        this.errorResponse = errorResponse;
        this.requestUrl = requestUrl;
    }

    /**
     * Sphere web service endpoint where the request was sent.
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * The HTTP status code of the response.
     */
    public int getStatusCode() {
        return errorResponse.getStatusCode();
    }

    /**
     * The message of the first error, for convenience.
     */
    public String getMessage() {
        return errorResponse.getMessage();
    }

    /**
     * The individual errors.
     */
    @Nonnull
    public List<SphereError> getErrors() {
        return errorResponse.getErrors();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SphereBackendException)) return false;

        SphereBackendException that = (SphereBackendException) o;

        if (!errorResponse.equals(that.errorResponse)) return false;
        if (!requestUrl.equals(that.requestUrl)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = requestUrl.hashCode();
        result = 31 * result + errorResponse.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SphereBackendException{" +
                "requestUrl='" + requestUrl + '\'' +
                ", errorResponse=" + errorResponse +
                '}';
    }
}
