package com.anass.account.opening.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "transactions", "customer", "customer" }, allowSetters = true)
    private Set<BankAccount> accounts = new HashSet<>();

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public Customer customerId(String customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Customer firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Customer lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<BankAccount> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Set<BankAccount> bankAccounts) {
        if (this.accounts != null) {
            this.accounts.forEach(i -> i.setCustomer(null));
        }
        if (bankAccounts != null) {
            bankAccounts.forEach(i -> i.setCustomer(this));
        }
        this.accounts = bankAccounts;
    }

    public Customer accounts(Set<BankAccount> bankAccounts) {
        this.setAccounts(bankAccounts);
        return this;
    }

    public Customer addAccounts(BankAccount bankAccount) {
        this.accounts.add(bankAccount);
        bankAccount.setCustomer(this);
        return this;
    }

    public Customer removeAccounts(BankAccount bankAccount) {
        this.accounts.remove(bankAccount);
        bankAccount.setCustomer(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerId='" + getCustomerId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
