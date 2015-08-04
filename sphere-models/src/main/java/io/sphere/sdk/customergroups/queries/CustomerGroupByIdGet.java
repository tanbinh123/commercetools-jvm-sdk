package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

public interface CustomerGroupByIdGet extends MetaModelGetDsl<CustomerGroup, CustomerGroup, CustomerGroupByIdGet, CustomerGroupExpansionModel<CustomerGroup>> {
    static CustomerGroupByIdGet of(final Identifiable<CustomerGroup> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CustomerGroupByIdGet of(final String id) {
        return new CustomerGroupByIdGetImpl(id);
    }

    @Override
    CustomerGroupByIdGet plusExpansionPaths(final Function<CustomerGroupExpansionModel<CustomerGroup>, ExpansionPath<CustomerGroup>> m);

    @Override
    CustomerGroupByIdGet withExpansionPaths(final Function<CustomerGroupExpansionModel<CustomerGroup>, ExpansionPath<CustomerGroup>> m);

    @Override
    List<ExpansionPath<CustomerGroup>> expansionPaths();

    @Override
    CustomerGroupByIdGet plusExpansionPaths(final ExpansionPath<CustomerGroup> expansionPath);

    @Override
    CustomerGroupByIdGet withExpansionPaths(final ExpansionPath<CustomerGroup> expansionPath);

    @Override
    CustomerGroupByIdGet withExpansionPaths(final List<ExpansionPath<CustomerGroup>> expansionPaths);
}
