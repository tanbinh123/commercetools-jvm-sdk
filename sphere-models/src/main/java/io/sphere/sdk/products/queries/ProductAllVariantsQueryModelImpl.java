package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

final class ProductAllVariantsQueryModelImpl<T> extends QueryModelImpl<T> implements ProductAllVariantsQueryModel<T> {

    ProductAllVariantsQueryModelImpl(@Nullable final QueryModel<T> parent) {
        super(parent, null);
    }

    private QueryPredicate<T> where(final QueryPredicate<PartialProductVariantQueryModel> embeddedPredicate) {
        final ProductDataQueryModelBase<T> parent = Optional.ofNullable((ProductDataQueryModelBase<T>) getParent())
                .orElseThrow(() -> new UnsupportedOperationException("A proper parent model is required."));
        return parent.where(m -> m.masterVariant().where(embeddedPredicate).or(m.variants().where(embeddedPredicate)));
    }

    @Override
    public QueryPredicate<T> where(final Function<PartialProductVariantQueryModel, QueryPredicate<PartialProductVariantQueryModel>> embeddedPredicate) {
        final PartialProductVariantQueryModel m = PartialProductVariantQueryModel.of();
        return where(embeddedPredicate.apply(m));
    }
}
