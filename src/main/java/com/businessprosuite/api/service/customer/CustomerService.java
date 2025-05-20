package com.businessprosuite.api.service.customer;

import com.businessprosuite.api.dto.customer.CustomerDTO;
import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAll();
    CustomerDTO findById(Integer id);
    CustomerDTO create(CustomerDTO dto);
    CustomerDTO update(Integer id, CustomerDTO dto);
    void delete(Integer id);
}