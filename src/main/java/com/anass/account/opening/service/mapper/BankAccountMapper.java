package com.anass.account.opening.service.mapper;

import com.anass.account.opening.domain.BankAccount;
import com.anass.account.opening.service.dto.BankAccountDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BankAccountMapper extends EntityMapper<BankAccountDTO, BankAccount> {

    BankAccountDTO toDto(BankAccount s);

    BankAccount toEntity(BankAccountDTO bankAccountDTO);
}
