package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public final class LineItemBuilder extends Base implements Builder<LineItem> {
    private final String id;
    private final String productId;
    private final LocalizedStrings name;
    private final LocalizedStrings productSlug;
    private final ProductVariant variant;
    private final Price price;
    private final long quantity;
    private Set<ItemState> state = Collections.emptySet();
    private Optional<TaxRate> taxRate = Optional.empty();
    private Optional<Reference<Channel>> supplyChannel = Optional.empty();
    private Optional<DiscountedLineItemPrice> discountedPrice = Optional.empty();

    private LineItemBuilder(final String id, final String productId, final LocalizedStrings name, final ProductVariant variant, final Price price, final long quantity, final LocalizedStrings productSlug) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.variant = variant;
        this.price = price;
        this.quantity = quantity;
        this.productSlug = productSlug;
    }

    public LineItemBuilder state(final Set<ItemState> state) {
        this.state = state;
        return this;
    }

    public LineItemBuilder supplyChannel(final Referenceable<Channel> supplyChannel) {
        return supplyChannel(Optional.of(supplyChannel.toReference()));
    }

    public LineItemBuilder supplyChannel(final Optional<Reference<Channel>> supplyChannel) {
        this.supplyChannel = supplyChannel;
        return this;
    }

    public LineItemBuilder taxRate(final TaxRate taxRate) {
        return taxRate(Optional.of(taxRate));
    }

    public LineItemBuilder taxRate(final Optional<TaxRate> taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public LineItemBuilder discountedPrice(final Optional<DiscountedLineItemPrice> discountedPrice) {
        this.discountedPrice = discountedPrice;
        return this;
    }

    @Deprecated
    /**
     *  Use {@link #of(String, String, LocalizedStrings, ProductVariant, Price, long)} and {@link #taxRate(TaxRate)} instead.
     */
    public static LineItemBuilder of(final String id, final String productId, final LocalizedStrings name, final ProductVariant variant, final Price price, final long quantity, final LocalizedStrings productSlug, final TaxRate taxRate) {
        return of(id, productId, name, variant, price, quantity, productSlug).taxRate(taxRate);
    }

    public static LineItemBuilder of(final String id, final String productId, final LocalizedStrings name, final ProductVariant variant, final Price price, final long quantity, final LocalizedStrings productSlug) {
        return new LineItemBuilder(id, productId, name, variant, price, quantity, productSlug);
    }

    @Override
    public LineItem build() {
        return new LineItemImpl(id, productId, name, variant, price, quantity, state, taxRate, supplyChannel, discountedPrice, productSlug, Optional.empty());
    }
}