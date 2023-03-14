package com.anass.account.opening.utils;

import java.util.UUID;

public class AccountUtils {
    public static String generateAccountNumber(String custumerId){
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID + custumerId;
    }
}
