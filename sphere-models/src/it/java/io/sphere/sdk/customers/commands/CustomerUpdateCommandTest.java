package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerName;
import io.sphere.sdk.customers.commands.updateactions.*;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.LocalDate;
import java.util.function.Predicate;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withB2cCustomerGroup;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomerWithOneAddress;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeName() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerName newName = CustomerName.ofTitleFirstAndLastName("Mister", "John", "Smith");
            assertThat(customer.getName()).isNotEqualTo(newName);
            final Customer updatedCustomer = execute(CustomerUpdateCommand.of(customer, ChangeName.of(newName)));
            assertThat(updatedCustomer.getName()).isEqualTo(newName);
        });
    }

    @Test
    public void changeEmail() throws Exception {
        withCustomer(client(), customer -> {
            final String newEmail = randomEmail(CustomerUpdateCommandTest.class);
            assertThat(customer.getEmail()).isNotEqualTo(newEmail);
            final Customer updatedCustomer = execute(CustomerUpdateCommand.of(customer, ChangeEmail.of(newEmail)));
            assertThat(updatedCustomer.getEmail()).isEqualTo(newEmail);
        });
    }

    @Test
    public void addAddress() throws Exception {
        withCustomer(client(), customer -> {
            final String city = "addAddress";
            final Address newAddress = AddressBuilder.of(DE).city(city).build();
            final Predicate<Address> containsNewAddressPredicate = a -> a.getCity().equals(city);
            assertThat(customer.getAddresses().stream()
                    .anyMatch(containsNewAddressPredicate))
                    .overridingErrorMessage("address is not present, yet")
                    .isFalse();
            final Customer updatedCustomer = execute(CustomerUpdateCommand.of(customer, AddAddress.of(newAddress)));
            assertThat(updatedCustomer.getAddresses().stream()
            .anyMatch(containsNewAddressPredicate)).isTrue();
        });
    }

    @Test
    public void changeAddress() throws Exception {
        withCustomerWithOneAddress(client(), customer -> {
            final String city = "new city";
            assertThat(customer.getAddresses()).hasSize(1);
            assertThat(customer.getAddresses().get(0).getCity()).isNotEqualTo(city);

            final Address oldAddress = customer.getAddresses().get(0);
            assertThat(oldAddress.getId())
                    .overridingErrorMessage("only fetched address contains an ID")
                    .isNotNull();

            final Address newAddress = oldAddress.withCity(city);
            final ChangeAddress updateAction = ChangeAddress.ofOldAddressToNewAddress(oldAddress, newAddress);
            final Customer customerWithReplacedAddress = execute(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(customerWithReplacedAddress.getAddresses()).hasSize(1);
            assertThat(customerWithReplacedAddress.getAddresses().get(0).getCity()).contains(city);
        });
    }

    @Test
    public void removeAddress() throws Exception {
        withCustomerWithOneAddress(client(), customer -> {
            final Address oldAddress = customer.getAddresses().get(0);
            assertThat(oldAddress.getId())
                    .overridingErrorMessage("only fetched address contains an ID")
                    .isNotNull();

            final RemoveAddress action = RemoveAddress.of(oldAddress);
            final Customer customerWithoutAddresses =
                    execute(CustomerUpdateCommand.of(customer, action));

            assertThat(customerWithoutAddresses.getAddresses()).isEmpty();
        });
    }

    @Test
    public void setDefaultShippingAddress() throws Exception {
        withCustomerWithOneAddress(client(), customer -> {
            final Address address = customer.getAddresses().get(0);
            assertThat(address.getId())
                    .overridingErrorMessage("only fetched address contains an ID")
                    .isNotNull();
            assertThat(customer.getDefaultShippingAddressId()).isNull();
            assertThat(customer.findDefaultShippingAddress()).isEmpty();

            final Customer updatedCustomer =
                    execute(CustomerUpdateCommand.of(customer, SetDefaultShippingAddress.ofAddress(address)));

            assertThat(updatedCustomer.getDefaultShippingAddressId()).contains(address.getId());
            assertThat(updatedCustomer.findDefaultShippingAddress()).contains(address);
        });
    }

    @Test
    public void setDefaultBillingAddress() throws Exception {
        withCustomerWithOneAddress(client(), customer -> {
            final Address address = customer.getAddresses().get(0);
            assertThat(address.getId())
                    .overridingErrorMessage("only fetched address contains an ID")
                    .isNotNull();
            assertThat(customer.getDefaultBillingAddressId()).isNull();
            assertThat(customer.getDefaultBillingAddress()).isNull();

            final Customer updatedCustomer =
                    execute(CustomerUpdateCommand.of(customer, SetDefaultBillingAddress.ofAddress(address)));

            assertThat(updatedCustomer.getDefaultBillingAddressId()).contains(address.getId());
            assertThat(updatedCustomer.getDefaultBillingAddress()).isEqualTo(address);
        });
    }

    @Test
    public void setCustomerNumber() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getCustomerNumber()).isNull();

            final String customerNumber = randomString();
            final Customer updatedCustomer =
                    execute(CustomerUpdateCommand.of(customer, SetCustomerNumber.of(customerNumber)));

            assertThat(updatedCustomer.getCustomerNumber()).contains(customerNumber);
        });
    }

    @Test
    public void setExternalId() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getExternalId()).isNull();

            final String externalId = randomString();
            final Customer updatedCustomer =
                    execute(CustomerUpdateCommand.of(customer, SetExternalId.of(externalId)));

            assertThat(updatedCustomer.getExternalId()).isEqualTo(externalId);
        });
    }

    @Test
    public void setCompanyName() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getCompanyName()).isNull();

            final String companyName = "Big coorp";
            final Customer updatedCustomer =
                    execute(CustomerUpdateCommand.of(customer, SetCompanyName.of(companyName)));

            assertThat(updatedCustomer.getCompanyName()).isEqualTo(companyName);
        });
    }

    @Test
    public void setVatId() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getVatId()).isNull();

            final String vatId = randomString();
            final Customer updatedCustomer =
                    execute(CustomerUpdateCommand.of(customer, SetVatId.of(vatId)));

            assertThat(updatedCustomer.getVatId()).isEqualTo(vatId);
        });
    }

    @Test
    public void setDateOfBirth() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getDateOfBirth()).isNull();

            final LocalDate dateOfBirth = LocalDate.now();
            final Customer updatedCustomer =
                    execute(CustomerUpdateCommand.of(customer, SetDateOfBirth.of(dateOfBirth)));

            assertThat(updatedCustomer.getDateOfBirth()).isEqualTo(dateOfBirth);
        });
    }

    @Test
    public void setCustomerGroup() throws Exception {
        withB2cCustomerGroup(client(), customerGroup -> {
            withCustomer(client(), customer -> {
                assertThat(customer.getCustomerGroup()).isNull();
                final Customer updateCustomer = execute(CustomerUpdateCommand.of(customer, SetCustomerGroup.of(customerGroup)));
                assertThat(updateCustomer.getCustomerGroup()).isEqualTo(customerGroup.toReference());
            });
        });
    }
}