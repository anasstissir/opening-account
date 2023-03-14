package com.anass.account.opening.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_account")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @OneToMany(mappedBy = "bankAccount")
    @JsonIgnoreProperties(value = { "bankAccount" }, allowSetters = true)
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "accounts" }, allowSetters = true)
    private Customer customer;

    public Long getId() {
        return this.id;
    }

    public BankAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public BankAccount accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return this.balance;
    }

    public BankAccount balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setBankAccount(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setBankAccount(this));
        }
        this.transactions = transactions;
    }

    public BankAccount transactions(Set<Transaction> transactions) {
        this.setTransactions(transactions);
        return this;
    }

    public BankAccount addTransactions(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setBankAccount(this);
        return this;
    }

    public BankAccount removeTransactions(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setBankAccount(null);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BankAccount customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankAccount)) {
            return false;
        }
        return id != null && id.equals(((BankAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
