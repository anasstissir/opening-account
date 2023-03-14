package com.anass.account.opening.service.dto;

import java.io.Serializable;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankAccountPostDTO implements Serializable {

    private Double initialCredit;

    private String customerId;

    public Double getInitialCredit() {
        return initialCredit;
    }

    public void setInitialCredit(Double initialCredit) {
        this.initialCredit = initialCredit;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
