package com.anass.account.opening.service;

import com.anass.account.opening.repository.BankAccountRepository;
import com.anass.account.opening.repository.CustomerRepository;
import com.anass.account.opening.service.impl.BankAccountServiceImpl;
import com.anass.account.opening.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Random;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BankAccountServiceTest {

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void should_not_find_an_account_when_dosent_exists(){
        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> bankAccountService.findOne(anyLong()));
    }

    private long getRandomLong() {
        return new Random().longs(1, 10).findFirst().getAsLong();
    }
}
