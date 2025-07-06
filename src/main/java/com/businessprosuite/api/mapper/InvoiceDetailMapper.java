package com.businessprosuite.api.mapper;

import com.businessprosuite.api.dto.finance.InvoiceDetailDTO;
import com.businessprosuite.api.model.finance.InvoiceDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceDetailMapper {

    @Mapping(target = "invoiceId", source = "finInvdInv.id")
    @Mapping(target = "productId", source = "finInvdProd.id")
    @Mapping(target = "quantity", source = "finInvdQuantity")
    @Mapping(target = "unitPrice", source = "finInvdUnitPrice")
    @Mapping(target = "totalPrice", source = "finInvdTotalPrice")
    @Mapping(target = "createdAt", source = "finInvdCreatedAt")
    InvoiceDetailDTO toDto(InvoiceDetail entity);

    @Mapping(target = "finInvdInv", ignore = true)
    @Mapping(target = "finInvdProd", ignore = true)
    @Mapping(target = "finInvdQuantity", source = "quantity")
    @Mapping(target = "finInvdUnitPrice", source = "unitPrice")
    @Mapping(target = "finInvdTotalPrice", source = "totalPrice")
    @Mapping(target = "finInvdCreatedAt", source = "createdAt")
    InvoiceDetail toEntity(InvoiceDetailDTO dto);

    @Mapping(target = "finInvdInv", ignore = true)
    @Mapping(target = "finInvdProd", ignore = true)
    @Mapping(target = "finInvdQuantity", source = "quantity")
    @Mapping(target = "finInvdUnitPrice", source = "unitPrice")
    @Mapping(target = "finInvdTotalPrice", source = "totalPrice")
    @Mapping(target = "finInvdCreatedAt", source = "createdAt")
    void updateEntity(InvoiceDetailDTO dto, @MappingTarget InvoiceDetail entity);
}
