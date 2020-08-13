package io.sphere.sdk.products.search;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public final class LocaleSelectionDsl extends Base implements LocaleSelection {
    @Nullable
    private final List<String> localeProjection;

    LocaleSelectionDsl(@Nullable List<String> localeProjection) {
        this.localeProjection = localeProjection;
    }

    public LocaleSelectionDsl withLocaleProjection(@Nullable final List<String> localeProjection) {
        return LocaleSelectionBuilder.of(localeProjection.toString()).build();
    }

    public LocaleSelectionDsl withLocaleProjection(@Nullable final String localeProjection) {
        return LocaleSelectionBuilder.of(localeProjection).build();
    }

    public LocaleSelectionDsl plusLocaleProjection(@Nullable final String localeProjection) {
        return LocaleSelectionBuilder.of(localeProjection).build();
    }

    public LocaleSelectionDsl plusLocaleProjection(@Nullable final List<String> localeProjection) {
        return LocaleSelectionBuilder.of(localeProjection).build();
    }

    @Nullable
    @Override
    public List<String> getLocaleProjection() {
        return localeProjection;
    }
}
