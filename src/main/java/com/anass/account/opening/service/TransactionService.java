package com.anass.account.opening.service;

import com.anass.account.opening.service.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface TransactionService {
    /**
     * Save a transaction.
     *
     * @param transactionDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionDTO save(TransactionDTO transactionDTO);

    /**
     * Updates a transaction.
     *
     * @param transactionDTO the entity to update.
     * @return the persisted entity.
     */
    TransactionDTO update(TransactionDTO transactionDTO);

    /**
     * Partially updates a transaction.
     *
     * @param transactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransactionDTO> partialUpdate(TransactionDTO transactionDTO);

    /**
     * Get all the transactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" transaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionDTO> findOne(Long id);

    /**
     * Delete the "id" transaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
