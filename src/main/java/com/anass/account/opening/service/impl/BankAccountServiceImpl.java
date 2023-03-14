package com.anass.account.opening.service.impl;

import com.anass.account.opening.domain.BankAccount;
import com.anass.account.opening.domain.Customer;
import com.anass.account.opening.domain.Transaction;
import com.anass.account.opening.domain.TransactionType;
import com.anass.account.opening.repository.BankAccountRepository;
import com.anass.account.opening.repository.CustomerRepository;
import com.anass.account.opening.repository.TransactionRepository;
import com.anass.account.opening.service.BankAccountService;
import com.anass.account.opening.service.dto.BankAccountDTO;
import com.anass.account.opening.service.dto.BankAccountPostDTO;
import com.anass.account.opening.service.mapper.BankAccountMapper;
import com.anass.account.opening.service.mapper.BankAccountPostMapper;
import com.anass.account.opening.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private final BankAccountRepository bankAccountRepository;

    private final BankAccountMapper bankAccountMapper;

    private final CustomerRepository customerRepository;

    private final TransactionRepository transactionRepository;

    private final BankAccountPostMapper bankAccountPostMapper;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper, CustomerRepository customerRepository, TransactionRepository transactionRepository, BankAccountPostMapper bankAccountPostMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.bankAccountPostMapper = bankAccountPostMapper;
    }

    @Override
    public BankAccountDTO save(BankAccountDTO bankAccountDTO) {
        log.debug("Request to save BankAccount : {}", bankAccountDTO);
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(bankAccount);
    }

    @Override
    public BankAccountDTO saveExisting(BankAccountPostDTO bankAccountDTO) {
        log.debug("Request to save BankAccount for exesiting customer: {}", bankAccountDTO);
        String customerId = bankAccountDTO.getCustomerId();
        //check if this customer exists
        Optional<Customer> customer = customerRepository.findCustomerByCustomerId(customerId);
        Boolean isPresent = customer.isPresent();
        if (isPresent){
            BankAccount newBankAccount = new BankAccount()
                .accountNumber(AccountUtils.generateAccountNumber(customerId))
                .customer(customer.get())
                .balance(0d);

            BankAccount savedAccount = bankAccountRepository.save(newBankAccount);
            if (bankAccountDTO.getInitialCredit() > 0){
                double initialCredit = bankAccountDTO.getInitialCredit();
                Set<BankAccount> accounts = customer.get().getAccounts();
                BankAccount validAccount = accounts.stream()
                    .filter(item -> item.getBalance() >= initialCredit).findAny().get();

                Double actualbalance = validAccount.getBalance() - initialCredit;
                validAccount.setBalance(actualbalance);
                bankAccountRepository.save(validAccount);
                savedAccount.setBalance(initialCredit);
                bankAccountRepository.save(savedAccount);


                Transaction debitTransaction = new Transaction()
                    .amount(initialCredit)
                    .bankAccount(validAccount)
                    .transactionType(TransactionType.DEBIT)
                    .transactionDate(Instant.now());
                transactionRepository.save(debitTransaction);
                Transaction creditTransaction = new Transaction()
                    .amount(initialCredit)
                    .bankAccount(savedAccount)
                    .transactionType(TransactionType.CREDIT)
                    .transactionDate(Instant.now());
                transactionRepository.save(creditTransaction);
            }
            return bankAccountMapper.toDto(newBankAccount);
        }
        return null;
    }

    @Override
    public BankAccountDTO update(BankAccountDTO bankAccountDTO) {
        log.debug("Request to update BankAccount : {}", bankAccountDTO);
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(bankAccount);
    }

    @Override
    public Optional<BankAccountDTO> partialUpdate(BankAccountDTO bankAccountDTO) {
        log.debug("Request to partially update BankAccount : {}", bankAccountDTO);

        return bankAccountRepository
            .findById(bankAccountDTO.getId())
            .map(existingBankAccount -> {
                bankAccountMapper.partialUpdate(existingBankAccount, bankAccountDTO);

                return existingBankAccount;
            })
            .map(bankAccountRepository::save)
            .map(bankAccountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankAccounts");
        return bankAccountRepository.findAll(pageable).map(bankAccountMapper::toDto);
    }

    public Page<BankAccountDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bankAccountRepository.findAllWithEagerRelationships(pageable).map(bankAccountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankAccountDTO> findOne(Long id) {
        log.debug("Request to get BankAccount : {}", id);
        BankAccount bankAccount = bankAccountRepository.findOneWithEagerRelationships(id).orElseThrow(EntityNotFoundException::new);
        return Optional.of(bankAccount).map(bankAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankAccount : {}", id);
        bankAccountRepository.deleteById(id);
    }
}
