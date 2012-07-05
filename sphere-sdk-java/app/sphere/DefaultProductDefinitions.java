package sphere;

import play.libs.F;
import de.commercetools.sphere.client.model.QueryResult;
import sphere.util.ReadJson;
import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.model.products.ProductDefinition;

/** Package private implementation. */
class DefaultProductDefinitions extends ProjectScopedAPI implements ProductDefinitions {

    public DefaultProductDefinitions(ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(credentials, endpoints);
    }

    public F.Promise<QueryResult<ProductDefinition>> getAll() {
        return url(endpoints.productDefinitions()).get().map(
            new ReadJson<QueryResult<ProductDefinition>>(new TypeReference<QueryResult<ProductDefinition>>() {})
        );
    }
}
