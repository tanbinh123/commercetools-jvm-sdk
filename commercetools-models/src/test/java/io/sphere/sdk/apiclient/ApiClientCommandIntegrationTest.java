package io.sphere.sdk.apiclient;

import io.sphere.sdk.apiclient.commands.ApiClientCreateCommand;

import io.sphere.sdk.apiclient.commands.ApiClientDeleteCommand;
import io.sphere.sdk.apiclient.queries.ApiClientQuery;
import io.sphere.sdk.client.SphereScope;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.ZonedDateTime;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.client.SphereProjectScope.*;
import static java.util.Arrays.*;
import static io.sphere.sdk.apiclient.ApiClientFixtures.*;

public class ApiClientCommandIntegrationTest extends IntegrationTest {


    @Test
    public void createApiClient(){
        final String projectKey = getSphereClientConfig().getProjectKey();
        final ApiClientCreateCommand createCommand  =ApiClientCreateCommand.of(ApiClientDraftBuilder.of("name", projectKey, MANAGE_MY_ORDERS, MANAGE_API_CLIENTS).build());
        final ApiClient res = client().executeBlocking(createCommand);
        assertThat(res).isNotNull();
        final String expectedScope = asList(MANAGE_MY_ORDERS, MANAGE_API_CLIENTS).stream().map(SphereScope::toScopeString).map(s -> s+":"+projectKey).collect(Collectors.joining(" "));
        assertThat(res.getScope()).isEqualTo(expectedScope);
        final ApiClient deletedRes =client().executeBlocking(ApiClientDeleteCommand.of(res));
        assertThat(deletedRes).isEqualToIgnoringGivenFields(res,"secret");
    }

    @Test
    public void testQueryModel(){
        final String projectKey = getSphereClientConfig().getProjectKey();
        final ApiClientCreateCommand createCommand  =ApiClientCreateCommand.of(ApiClientDraftBuilder.of("name", projectKey, MANAGE_MY_ORDERS, MANAGE_API_CLIENTS).build());
        final ApiClient res = client().executeBlocking(createCommand);assertThat(res).isNotNull();
        final PagedQueryResult<ApiClient> result = client().executeBlocking(ApiClientQuery.of()
                .plusPredicates(m -> m.id().is(res.getId()))
        );
        assertThat(result.getResults()).hasSize(1);

        assertThat(result.getResults().get(0)).isEqualToIgnoringGivenFields(res,"secret");
        final ApiClient deletedRes =client().executeBlocking(ApiClientDeleteCommand.of(res));
        assertThat(deletedRes).isEqualToIgnoringGivenFields(res,"secret");
    }
}
