package io.sphere.sdk.categories;

import java.time.ZonedDateTime;
import java.util.Optional;

import io.sphere.sdk.models.*;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Creates a category for unit tests.
 *
 */
public final class CategoryBuilder extends Base implements Builder<Category> {
    private static final Random RANDOM = new Random();
    protected String id = "id" + CategoryBuilder.RANDOM.nextInt();
    protected long version = 1;
    protected ZonedDateTime createdAt = ZonedDateTime.now();
    protected ZonedDateTime lastModifiedAt = ZonedDateTime.now();
    private LocalizedStrings name;
    private LocalizedStrings slug;
    @Nullable
    private LocalizedStrings description;
    private List<Reference<Category>> ancestors = Collections.emptyList();
    @Nullable
    private Reference<Category> parent;
    @Nullable
    private String orderHint;
    @Nullable
    private String externalId;
    @Nullable
    private LocalizedStrings metaTitle;
    @Nullable
    private LocalizedStrings metaDescription;
    @Nullable
    private LocalizedStrings metaKeywords;

    public static CategoryBuilder of(final String id, final LocalizedStrings name, final LocalizedStrings slug) {
        return new CategoryBuilder(id, name, slug);
    }

    public static CategoryBuilder of(final Category category) {
        return new CategoryBuilder(category.getId(), category.getName(), category.getSlug()).
                version(category.getVersion()).createdAt(category.getCreatedAt()).
                lastModifiedAt(category.getLastModifiedAt()).
                name(category.getName()).slug(category.getSlug()).description(category.getDescription()).
                ancestors(category.getAncestors()).parent(category.getParent()).
                orderHint(category.getOrderHint()).externalId(category.getExternalId());
    }

    private CategoryBuilder(final String id, final LocalizedStrings name, final LocalizedStrings slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public CategoryBuilder name(final LocalizedStrings name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder slug(final LocalizedStrings slug) {
        this.slug = slug;
        return this;
    }

    public CategoryBuilder description(@Nullable final LocalizedStrings description) {
        this.description = description;
        return this;
    }

    public CategoryBuilder ancestors(final List<Reference<Category>> ancestors) {
        this.ancestors = ancestors;
        return this;
    }

    public CategoryBuilder parent(@Nullable final Referenceable<Category> parent) {
        this.parent = Optional.ofNullable(parent).map(x -> x.toReference()).orElse(null);
        return this;
    }

    public CategoryBuilder orderHint(@Nullable final String orderHint) {
        this.orderHint = orderHint;
        return this;
    }

    public CategoryBuilder externalId(@Nullable final String externalId) {
        this.externalId = externalId;
        return this;
    }



    public CategoryBuilder metaTitle(@Nullable final LocalizedStrings metaTitle) {
        this.metaTitle = metaTitle;
        return getThis();
    }

    public CategoryBuilder metaDescription(@Nullable final LocalizedStrings metaDescription) {
        this.metaDescription = metaDescription;
        return getThis();
    }


    public CategoryBuilder metaKeywords(@Nullable final LocalizedStrings metaKeywords) {
        this.metaKeywords = metaKeywords;
        return getThis();
    }

    @Override
    public Category build() {
        return new CategoryImpl(id, version, createdAt, lastModifiedAt, name, slug, description, ancestors, parent, orderHint, externalId, metaTitle, metaDescription, metaKeywords);
    }

    protected CategoryBuilder getThis() {
        return this;
    }

    public CategoryBuilder id(final String id) {
        setId(id);
        return getThis();
    }

    public CategoryBuilder version(final long version) {
        setVersion(version);
        return getThis();
    }

    public CategoryBuilder createdAt(final ZonedDateTime createdAt) {
        setCreatedAt(createdAt);
        return getThis();
    }

    public CategoryBuilder lastModifiedAt(final ZonedDateTime lastModifiedAt) {
       setLastModifiedAt(lastModifiedAt);
       return getThis();
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setVersion(final long version) {
        this.version = version;
    }

    public void setCreatedAt(final ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModifiedAt(final ZonedDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
