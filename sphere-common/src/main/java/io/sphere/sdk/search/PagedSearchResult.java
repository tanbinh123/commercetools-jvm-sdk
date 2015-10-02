package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.queries.PagedResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PagedSearchResult<T> extends PagedResult<T> {

    private final Map<String, FacetResult> facets;

    @JsonCreator
    PagedSearchResult(final Integer offset, final Integer total, final List<T> results, final Map<String, FacetResult> facets) {
        super(offset, total, results);
        this.facets = facets;
    }

    @Override
    public List<T> getResults() {
        return super.getResults();
    }

    public Map<String, FacetResult> getFacetsResults() {
        return facets;
    }

    public FacetResult getFacetResult(final String spherePath) {
        return facets.get(spherePath);
    }

    public TermFacetResult getTermFacetResult(final FacetExpression<T> facetExpression) {
        return Optional.ofNullable(getFacetResult(facetExpression.resultPath())).map(facetResult -> {
            if (facetResult instanceof TermFacetResult) {
                return (TermFacetResult) facetResult;
            } else {
                throw new ClassCastException("Facet result is not of type TermFacetResult: " + facetResult);
            }
        }).orElse(null);
    }

    public RangeFacetResult getRangeFacetResult(final FacetExpression<T> facetExpression) {
        return Optional.ofNullable(getFacetResult(facetExpression.resultPath())).map(facetResult -> {
            if (facetResult instanceof RangeFacetResult) {
                return (RangeFacetResult) facetResult;
            } else {
                throw new ClassCastException("Facet result is not of type RangeFacetResult: " + facetResult);
            }
        }).orElse(null);
    }

    public FilteredFacetResult getFilteredFacetResult(final FacetExpression<T> facetExpression) {
        return Optional.ofNullable(getFacetResult(facetExpression.resultPath())).map(facetResult -> {
            if (facetResult instanceof FilteredFacetResult) {
                return (FilteredFacetResult) facetResult;
            } else {
                throw new ClassCastException("Facet result is not of type FilteredFacetResult: " + facetResult);
            }
        }).orElse(null);
    }
}
