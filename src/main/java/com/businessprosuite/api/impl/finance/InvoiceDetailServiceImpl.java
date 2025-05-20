package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.InvoiceDetailDTO;
import com.businessprosuite.api.model.finance.InvoiceDetail;
import com.businessprosuite.api.model.inventory.InventoryProduct;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.repository.finance.InvoiceDetailRepository;
import com.businessprosuite.api.repository.inventory.InventoryProductRepository;
import com.businessprosuite.api.repository.finance.InvoiceRepository;
import com.businessprosuite.api.service.finance.InvoiceDetailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceDetailServiceImpl implements InvoiceDetailService {

    private final InvoiceDetailRepository detailRepo;
    private final InvoiceRepository invoiceRepo;
    private final InventoryProductRepository productRepo;

    @Override
    public List<InvoiceDetailDTO> findAll() {
        return detailRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDetailDTO findById(Integer id) {
        InvoiceDetail detail = detailRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InvoiceDetail not found with id " + id));
        return toDto(detail);
    }

    @Override
    public InvoiceDetailDTO create(InvoiceDetailDTO dto) {
        Invoice invoice = invoiceRepo.findById(dto.getInvoiceId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + dto.getInvoiceId()));
        InventoryProduct product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + dto.getProductId()));

        InvoiceDetail detail = new InvoiceDetail();
        detail.setFinInvdInv(invoice);
        detail.setFinInvdProd(product);
        detail.setFinInvdQuantity(dto.getQuantity());
        detail.setFinInvdUnitPrice(dto.getUnitPrice());
        BigDecimal total = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        detail.setFinInvdTotalPrice(total);
        detail.setFinInvdCreatedAt(LocalDateTime.now());

        InvoiceDetail saved = detailRepo.save(detail);
        return toDto(saved);
    }

    @Override
    public InvoiceDetailDTO update(Integer id, InvoiceDetailDTO dto) {
        InvoiceDetail detail = detailRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InvoiceDetail not found with id " + id));
        Invoice invoice = invoiceRepo.findById(dto.getInvoiceId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + dto.getInvoiceId()));
        InventoryProduct product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + dto.getProductId()));

        detail.setFinInvdInv(invoice);
        detail.setFinInvdProd(product);
        detail.setFinInvdQuantity(dto.getQuantity());
        detail.setFinInvdUnitPrice(dto.getUnitPrice());
        BigDecimal total = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        detail.setFinInvdTotalPrice(total);
        // createdAt remains unchanged

        InvoiceDetail updated = detailRepo.save(detail);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!detailRepo.existsById(id)) {
            throw new EntityNotFoundException("InvoiceDetail not found with id " + id);
        }
        detailRepo.deleteById(id);
    }

    private InvoiceDetailDTO toDto(InvoiceDetail detail) {
        return InvoiceDetailDTO.builder()
                .id(detail.getId())
                .invoiceId(detail.getFinInvdInv().getId())
                .productId(detail.getFinInvdProd().getId())
                .quantity(detail.getFinInvdQuantity())
                .unitPrice(detail.getFinInvdUnitPrice())
                .totalPrice(detail.getFinInvdTotalPrice())
                .createdAt(detail.getFinInvdCreatedAt())
                .build();
    }
}