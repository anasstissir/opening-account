package com.anass.account.opening.service.mapper;

import com.anass.account.opening.domain.BankAccount;
import com.anass.account.opening.service.dto.BankAccountPostDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BankAccountPostMapper extends EntityMapper<BankAccountPostDTO, BankAccount> {

    BankAccountPostDTO toDto(BankAccount s);

    BankAccount toEntity(BankAccountPostDTO bankAccountPostDTO);
}
