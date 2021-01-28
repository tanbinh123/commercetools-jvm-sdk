package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemShippingDetails;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.models.*;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFields;
import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.Set;

public final class CustomLineItemImportDraftBuilder extends Base implements Builder<CustomLineItemImportDraft> {
    private String id = RandomStringUtils.randomAlphanumeric(20);
    private final LocalizedString name;
    private final MonetaryAmount money;
    private String slug = RandomStringUtils.randomAlphanumeric(20);
    private final Long quantity;
    @Nullable
    private Set<ItemState> state;
    private final Reference<TaxCategory> taxCategory;
    private TaxRate taxRate;

    @Nullable
    private final CustomFields custom;

    @Nullable private final ItemShippingDetails shippingDetails;

    private CustomLineItemImportDraftBuilder(final LocalizedString name, final MonetaryAmount money, final Long quantity, final Reference<TaxCategory> taxCategory, final CustomFields custom, final ItemShippingDetails shippingDetails) {
        this.name = name;
        this.money = money;
        this.quantity = quantity;
        this.taxCategory = taxCategory;
        this.custom = custom;
        this.shippingDetails = shippingDetails;
    }

    public static CustomLineItemImportDraftBuilder of(final LocalizedString name, final long quantity, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory) {
        return of(name, quantity, money, taxCategory.toReference(), null, null);
    }

    public static CustomLineItemImportDraftBuilder of(final LocalizedString name, final long quantity, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final CustomFields custom, final ItemShippingDetails shippingDetails) {
        return new CustomLineItemImportDraftBuilder(name, money, quantity, taxCategory.toReference(), custom, shippingDetails);
    }

    public CustomLineItemImportDraftBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public CustomLineItemImportDraftBuilder slug(final String slug) {
        this.slug = slug;
        return this;
    }

    public CustomLineItemImportDraftBuilder state(@Nullable final Set<ItemState> state) {
        this.state = state;
        return this;
    }

    public CustomLineItemImportDraftBuilder taxRate(@Nullable final TaxRate taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    @Override
    public CustomLineItemImportDraft build() {
        return new CustomLineItemImportDraftImpl(id, name, money, slug, quantity, state, taxCategory, taxRate, custom, shippingDetails);
    }
}
