package com.businessprosuite.api.mapper;

import com.businessprosuite.api.dto.CustomerDto;
import com.businessprosuite.api.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer entity);
    Customer toEntity(CustomerDto dto);
}