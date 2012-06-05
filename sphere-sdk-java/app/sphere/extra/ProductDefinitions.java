package sphere.extra;

import play.libs.F;
import play.libs.WS;
import sphere.model.QueryResult;
import sphere.util.ReadJson;
import org.codehaus.jackson.type.TypeReference;
import sphere.model.products.ProductDefinition;

/** Provides access to Sphere APIs for working with ProductDefinitions. */
public class ProductDefinitions {
    public static F.Promise<QueryResult<ProductDefinition>> getAll(String project) {
        return WS.url(Project.endpoint(project) + "/product-definitions").get().map(
            new ReadJson<QueryResult<ProductDefinition>>(new TypeReference<QueryResult<ProductDefinition>>() { })
        );
    }
}
