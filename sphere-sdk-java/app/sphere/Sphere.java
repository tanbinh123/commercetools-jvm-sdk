package sphere;

import sphere.util.ClientCredentials;

/** Provides access to Sphere HTTP APIs. */
public class Sphere {

    private Sphere() {}
    private static final Sphere instance = new Sphere();
    /** Returns singleton instance of the Sphere class. */
    public static Sphere getInstance() { return instance; }

    public void initialize() {
        projectCredentials = ClientCredentials.getFromAuthorizationServer(
            Endpoints.tokenEndpoint(),
            Config.projectID(),
            Config.clientID(),
            Config.clientSecret()
        );
        project = Config.projectID();
        products = new DefaultProducts(project, projectCredentials);
        productDefinitions = new DefaultProductDefinitions(project, projectCredentials);
        categories = new DefaultCategories(project, projectCredentials);
    }

    /** OAuth client credentials for the current project. */
    private ClientCredentials projectCredentials;

    /** Current project, configured in application.conf under the key ''. */
    private String project;

    /** Sphere backend HTTP APIs for Products. */
    public Products products;

    /** Sphere backend HTTP APIs for Product definitions. */
    public ProductDefinitions productDefinitions;

    /** Sphere backend HTTP APIs for categories. */
    public Categories categories;
}
