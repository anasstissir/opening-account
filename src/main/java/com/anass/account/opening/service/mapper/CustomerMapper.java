package com.anass.account.opening.service.mapper;

import com.anass.account.opening.domain.Customer;
import com.anass.account.opening.service.dto.CustomerDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {}
