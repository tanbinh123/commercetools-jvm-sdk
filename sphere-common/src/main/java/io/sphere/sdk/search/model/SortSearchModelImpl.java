package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.SearchSortDirection;
import io.sphere.sdk.search.SortExpression;

import static io.sphere.sdk.search.SearchSortDirection.ASC;
import static io.sphere.sdk.search.SearchSortDirection.DESC;

/**
 * A sort model to decide the direction of a model with single values.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 */
abstract class SortSearchModelImpl<T, S extends SortSearchModel<T>> extends Base implements SortSearchModel<T> {
    private final SearchModel<T> searchModel;

    SortSearchModelImpl(final SortableSearchModel<T, S> searchModel) {
        this.searchModel = searchModel;
    }

    public SortExpression<T> by(final SearchSortDirection direction) {
        return new SortExpressionImpl<>(searchModel, direction);
    }

    /**
     * @return the ascending sort direction
     */
    public SortExpression<T> byAsc() {
        return by(ASC);
    }

    /**
     * @return the descending sort direction
     */
    public SortExpression<T> byDesc() {
        return by(DESC);
    }
}
