package com.anass.account.opening.rest;

import com.anass.account.opening.repository.BankAccountRepository;
import com.anass.account.opening.rest.errors.BadRequestAlertException;
import com.anass.account.opening.service.BankAccountService;
import com.anass.account.opening.service.dto.BankAccountDTO;
import com.anass.account.opening.service.dto.BankAccountPostDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BankAccountResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountResource.class);

    private static final String ENTITY_NAME = "bankAccount";

    private final BankAccountService bankAccountService;

    private final BankAccountRepository bankAccountRepository;

    public BankAccountResource(BankAccountService bankAccountService, BankAccountRepository bankAccountRepository) {
        this.bankAccountService = bankAccountService;
        this.bankAccountRepository = bankAccountRepository;
    }


    @PostMapping("/bank-accounts")
    public ResponseEntity<BankAccountDTO> createBankAccount(@Valid @RequestBody BankAccountPostDTO bankAccountDTO) throws URISyntaxException {
        log.debug("REST request to save BankAccount : {}", bankAccountDTO);
        BankAccountDTO result = bankAccountService.saveExisting(bankAccountDTO);
        if (result != null)
            return ResponseEntity
                .created(new URI("/api/bank-accounts/" + result.getId()))
                .body(result);
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/bank-accounts/{id}")
    public ResponseEntity<BankAccountDTO> updateBankAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankAccountDTO bankAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankAccount : {}, {}", id, bankAccountDTO);
        if (bankAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankAccountDTO result = bankAccountService.update(bankAccountDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("/bank-accounts")
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts(Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of BankAccounts");
        Page<BankAccountDTO> page;
        if (eagerload) {
            page = bankAccountService.findAllWithEagerRelationships(pageable);
        } else {
            page = bankAccountService.findAll(pageable);
        }
        return ResponseEntity.ok().body(page.getContent());
    }


    @GetMapping("/bank-accounts/{id}")
    public ResponseEntity<BankAccountDTO> getBankAccount(@PathVariable Long id) {
        log.debug("REST request to get BankAccount : {}", id);
        Optional<BankAccountDTO> bankAccountDTO = bankAccountService.findOne(id);
        return ResponseEntity.ok().body(bankAccountDTO.get());
    }


    @DeleteMapping("/bank-accounts/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long id) {
        log.debug("REST request to delete BankAccount : {}", id);
        bankAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
