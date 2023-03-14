package com.anass.account.opening.service;

import com.anass.account.opening.service.dto.BankAccountDTO;
import com.anass.account.opening.service.dto.BankAccountPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface BankAccountService {
    /**
     * Save a bankAccount.
     *
     * @param bankAccountDTO the entity to save.
     * @return the persisted entity.
     */
    BankAccountDTO save(BankAccountDTO bankAccountDTO);


    BankAccountDTO saveExisting(BankAccountPostDTO bankAccountDTO);

    /**
     * Updates a bankAccount.
     *
     * @param bankAccountDTO the entity to update.
     * @return the persisted entity.
     */
    BankAccountDTO update(BankAccountDTO bankAccountDTO);

    /**
     * Partially updates a bankAccount.
     *
     * @param bankAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankAccountDTO> partialUpdate(BankAccountDTO bankAccountDTO);

    /**
     * Get all the bankAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankAccountDTO> findAll(Pageable pageable);

    /**
     * Get all the bankAccounts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankAccountDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" bankAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankAccountDTO> findOne(Long id);

    /**
     * Delete the "id" bankAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
