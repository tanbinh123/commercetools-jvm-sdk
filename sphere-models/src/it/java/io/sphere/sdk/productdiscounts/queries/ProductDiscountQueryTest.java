package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withUpdateableProductDiscount;
import static org.assertj.core.api.Assertions.*;

public class ProductDiscountQueryTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withUpdateableProductDiscount(client(), productDiscount -> {
            final ProductDiscountQuery discountQuery = ProductDiscountQuery.of()
                    .withPredicate(m -> m.active().is(false).and(m.id().is(productDiscount.getId())))
                    .withExpansionPaths(m -> m.references());
            final ProductDiscount loadedDiscount = execute(discountQuery).head().get();

            assertThat(loadedDiscount.getId()).isEqualTo(productDiscount.getId());
            assertThat(loadedDiscount.getReferences().size()).isGreaterThanOrEqualTo(1);
            assertThat(loadedDiscount.getReferences().stream()
                            .filter(ref -> ref.getTypeId().equals(Product.typeId()))
                            .findFirst()
                            .get()
            )
            .overridingErrorMessage("one product is expanded")
            .matches(ref -> ref.getObj() != null);

            return productDiscount;
        });
    }
}