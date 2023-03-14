package com.anass.account.opening.service.mapper;

import com.anass.account.opening.domain.Transaction;
import com.anass.account.opening.service.dto.TransactionDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    TransactionDTO toDto(Transaction transaction);

    Transaction toEntity(TransactionDTO transactionDto);
}
