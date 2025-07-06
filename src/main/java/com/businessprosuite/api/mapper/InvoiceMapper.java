package com.businessprosuite.api.mapper;

import com.businessprosuite.api.dto.finance.InvoiceDTO;
import com.businessprosuite.api.model.finance.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "configCompanyId", source = "configCompany.id")
    @Mapping(target = "date", source = "finInvDate")
    @Mapping(target = "customerId", source = "finInvCus.id")
    @Mapping(target = "total", source = "finInvTotal")
    @Mapping(target = "tax", source = "finInvTax")
    @Mapping(target = "discount", source = "finInvDiscount")
    @Mapping(target = "securityUserId", source = "finInvSecus.id")
    @Mapping(target = "paymentStatus", source = "finInvPaymentStatus")
    @Mapping(target = "createdAt", source = "finInvCreatedAt")
    @Mapping(target = "updatedAt", source = "finInvUpdatedAt")
    @Mapping(target = "net", source = "finInvNet")
    InvoiceDTO toDto(Invoice entity);

    @Mapping(target = "configCompany", ignore = true)
    @Mapping(target = "finInvCus", ignore = true)
    @Mapping(target = "finInvSecus", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "invoiceDetails", ignore = true)
    @Mapping(target = "finInvDate", source = "date")
    @Mapping(target = "finInvTotal", source = "total")
    @Mapping(target = "finInvTax", source = "tax")
    @Mapping(target = "finInvDiscount", source = "discount")
    @Mapping(target = "finInvPaymentStatus", source = "paymentStatus")
    @Mapping(target = "finInvCreatedAt", source = "createdAt")
    @Mapping(target = "finInvUpdatedAt", source = "updatedAt")
    @Mapping(target = "finInvNet", source = "net")
    Invoice toEntity(InvoiceDTO dto);

    @Mapping(target = "configCompany", ignore = true)
    @Mapping(target = "finInvCus", ignore = true)
    @Mapping(target = "finInvSecus", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "invoiceDetails", ignore = true)
    @Mapping(target = "finInvDate", source = "date")
    @Mapping(target = "finInvTotal", source = "total")
    @Mapping(target = "finInvTax", source = "tax")
    @Mapping(target = "finInvDiscount", source = "discount")
    @Mapping(target = "finInvPaymentStatus", source = "paymentStatus")
    @Mapping(target = "finInvCreatedAt", source = "createdAt")
    @Mapping(target = "finInvUpdatedAt", source = "updatedAt")
    @Mapping(target = "finInvNet", source = "net")
    void updateEntity(InvoiceDTO dto, @MappingTarget Invoice entity);
}
