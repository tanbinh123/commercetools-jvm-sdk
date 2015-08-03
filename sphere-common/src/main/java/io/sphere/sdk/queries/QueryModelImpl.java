package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.SphereEnumeration;

import javax.annotation.Nullable;

import static io.sphere.sdk.queries.StringQuerySortingModel.normalize;

public class QueryModelImpl<T> extends Base implements QueryModel<T> {
    @Nullable
    private final QueryModel<T> parent;
    @Nullable
    private final String pathSegment;

    protected QueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        this.parent = parent;
        this.pathSegment = pathSegment;
    }

    @Override
    @Nullable
    public String getPathSegment() {
        return pathSegment;
    }

    @Nullable
    @Override
    public QueryModel<T> getParent() {
        return parent;
    }

    protected CurrencyCodeQueryModel<T> currencyCodeModel(final String pathSegment) {
        return new CurrencyCodeQueryModelImpl<>(this, pathSegment);
    }

    protected MoneyQueryModel<T> moneyModel(final String pathSegment) {
        return new MoneyQueryModelImpl<>(this, pathSegment);
    }

    protected AnyReferenceQueryModel<T> anyReferenceModel(final String pathSegment) {
        return new AnyReferenceQueryModelImpl <>(this, pathSegment);
    }

    protected <R> ReferenceQueryModel<T, R> referenceModel(final String pathSegment) {
        return new ReferenceQueryModelImpl<>(this, pathSegment);
    }

    protected <R> ReferenceOptionalQueryModel<T, R> referenceOptionalModel(final String pathSegment) {
        return new ReferenceOptionalQueryModel<>(this, pathSegment);
    }

    protected <E extends SphereEnumeration> SphereEnumerationQueryModel<T, E> enumerationQueryModel(final String pathSegment) {
        return new SphereEnumerationQueryModel<>(this, pathSegment);
    }

    protected StringQuerySortingModel<T> stringModel(final String pathSegment) {
        return new StringQuerySortingModel<>(this, pathSegment);
    }

    protected BooleanQueryModel<T> booleanModel(final String pathSegment) {
        return new BooleanQueryModel<>(this, pathSegment);
    }

    protected LongQuerySortingModel<T> longModel(final String pathSegment) {
        return new LongQuerySortingModelImpl<>(this, pathSegment);
    }

    protected IntegerQuerySortingModel<T> integerModel(final String pathSegment) {
        return new IntegerQuerySortingModelImpl<>(this, pathSegment);
    }

    protected <V> QueryPredicate<T> isPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsEqualTo(this, value);
    }

    protected <V> QueryPredicate<T> isNotPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsNotEqualTo(this, value);
    }

    protected QueryPredicate<T> isPredicate(final String value) {
        return ComparisonQueryPredicate.ofIsEqualTo(this, normalize(value));
    }

    protected QueryPredicate<T> isNotPredicate(final String value) {
        return ComparisonQueryPredicate.ofIsNotEqualTo(this, normalize(value));
    }

    protected <V> QueryPredicate<T> isInPredicate(final Iterable<V> args) {
        return new IsInQueryPredicate<>(this, args);
    }

    protected <V> QueryPredicate<T> isNotInPredicate(final Iterable<V> args) {
        return new IsNotInQueryPredicate<>(this, args);
    }

    protected <V> QueryPredicate<T> isGreaterThanPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsGreaterThan(this, value);
    }

    protected <V> QueryPredicate<T> isLessThanPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsLessThan(this, value);
    }

    protected <V> QueryPredicate<T> isLessThanOrEqualToPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsLessThanOrEqualTo(this, value);
    }

    protected <V> QueryPredicate<T> isGreaterThanOrEqualToPredicate(final V value) {
        return ComparisonQueryPredicate.ofGreaterThanOrEqualTo(this, value);
    }

    protected QueryPredicate<T> isPresentPredicate() {
        return new OptionalQueryPredicate<>(this, true);
    }

    protected QueryPredicate<T> isNotPresentPredicate() {
        return new OptionalQueryPredicate<>(this, false);
    }

    protected QueryPredicate<T> isEmptyCollectionQueryPredicate() {
        return new QueryModelQueryPredicate<T>(this){
            @Override
            protected String render() {
                return " is empty";
            }
        };
    }

    protected QueryPredicate<T> isNotEmptyCollectionQueryPredicate() {
        return new QueryModelQueryPredicate<T>(this){
            @Override
            protected String render() {
                return " is not empty";
            }
        };
    }
}