package com.anass.account.opening.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransactionDTO implements Serializable {

    private Long id;

    private Instant transactionDate;

    private Double amount;

    private String transactionType;

    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private BankAccountDTO bankAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BankAccountDTO getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDTO bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", amount=" + getAmount() +
            ", bankAccount=" + getBankAccount() +
            "}";
    }
}
