package io.sphere.sdk.customers.commands;

import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.*;
import io.sphere.sdk.customers.errors.CustomerInvalidCredentials;
import io.sphere.sdk.customers.errors.CustomerInvalidCurrentPassword;
import io.sphere.sdk.customers.errors.CustomerWeakPassword;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.customers.CustomerFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.randomEmail;
import static io.sphere.sdk.test.SphereTestUtils.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.fail;

public class CustomerChangePasswordCommandIntegrationTest extends CustomerIntegrationTest {

    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final String oldPassword = PASSWORD;
            final String newPassword = "newSecret";
            final Customer updatedCustomer = client().executeBlocking(CustomerChangePasswordCommand.of(customer, oldPassword, newPassword));
            assertThat(customer.getPassword()).isNotEqualTo(updatedCustomer.getPassword());
            final CustomerSignInResult signInResult =
                    client().executeBlocking(CustomerSignInCommand.of(customer.getEmail(), newPassword));
            assertThat(signInResult.getCustomer().hasSameIdAs(updatedCustomer))
                    .overridingErrorMessage("sign in works with new password")
                    .isTrue();
            final Throwable throwable = catchThrowable(() -> client().executeBlocking(CustomerSignInCommand.of(customer.getEmail(), oldPassword)));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException errorResponseException = (ErrorResponseException) throwable;
            assertThat(errorResponseException.hasErrorCode(CustomerInvalidCredentials.CODE)).isTrue();
        });
    }

    @Test
    public void invalidCurrentPassword() throws Exception {
        withCustomer(client(), customer -> {
            final String oldPassword = PASSWORD + randomInt();
            final String newPassword = "newSecret";
            final Throwable throwable = catchThrowable(() -> client().executeBlocking(CustomerChangePasswordCommand.of(customer, oldPassword, newPassword)));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException errorResponseException = (ErrorResponseException) throwable;
            assertThat(errorResponseException.hasErrorCode(CustomerInvalidCurrentPassword.CODE)).isTrue();
        });
    }

//    this method should work checking the password and asserting that is weak but there is no Exception throwed
    @Ignore
    @Test
    public void weakPassword() throws Exception {
        final String password = "newsecret";
        final CustomerDraft customerDraft = CustomerDraftDsl.of(CustomerName.ofFirstAndLastName("John", "Smith"), randomEmail(CustomerFixtures.class), password);
        final Throwable throwable = catchThrowable(() ->  client().executeBlocking(CustomerCreateCommand.of(customerDraft)));
        assertThat(throwable).isInstanceOf(ErrorResponseException.class);
        final ErrorResponseException errorResponseException = (ErrorResponseException) throwable;
        assertThat(errorResponseException.hasErrorCode(CustomerWeakPassword.CODE)).isTrue();
    }

    @Test
    public void executionDemo() throws Exception {
        withCustomer(client(), customer -> {
            final SphereClient client = client();
            demo(client, customer.getEmail());

            final String oldPassword = PASSWORD;
            final String newPassword = "newSecret";
            final Customer updatedCustomer = client().executeBlocking(CustomerChangePasswordCommand.of(customer, oldPassword, newPassword));
            assertThat(customer.getPassword()).isNotEqualTo(updatedCustomer.getPassword());
            final CustomerSignInResult signInResult =
                    client().executeBlocking(CustomerSignInCommand.of(customer.getEmail(), newPassword));
            assertThat(signInResult.getCustomer().hasSameIdAs(updatedCustomer))
                    .overridingErrorMessage("sign in works with new password")
                    .isTrue();
            try {
                client().executeBlocking(CustomerSignInCommand.of(customer.getEmail(), oldPassword));
                fail();
            } catch (final ErrorResponseException e) {
                assertThat(e.hasErrorCode(CustomerInvalidCredentials.CODE)).isTrue();
            }
        });
    }

    private void demo(final SphereClient client, final String email) {
        final String wrongPassword = "wrong password";
        final CustomerSignInCommand signInCommand = CustomerSignInCommand.of(email, wrongPassword);
        final CompletionStage<CustomerSignInResult> future = client.execute(signInCommand);
        future.whenCompleteAsync((signInResult, exception) -> {
            if (signInResult != null) {
                println("Signing worked");
            } else if (exception instanceof ErrorResponseException) {
                final ErrorResponseException errorResponseException = (ErrorResponseException) exception;
                final String code = CustomerInvalidCredentials.CODE;
                if (errorResponseException.hasErrorCode(code)) {
                    println("customer has invalid credentials");
                }
            }
        });
    }

    private void println(final String s) {
        //ignore, is for demo
    }
}