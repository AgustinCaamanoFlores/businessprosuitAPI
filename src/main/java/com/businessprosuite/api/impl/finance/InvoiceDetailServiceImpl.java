package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.InvoiceDetailDTO;
import com.businessprosuite.api.model.finance.InvoiceDetail;
import com.businessprosuite.api.model.inventory.InventoryProduct;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.mapper.InvoiceDetailMapper;
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
    private final InvoiceDetailMapper       detailMapper;

    @Override
    public List<InvoiceDetailDTO> findAll() {
        return detailRepo.findAll().stream()
                .map(detailMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDetailDTO findById(Integer id) {
        InvoiceDetail detail = detailRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InvoiceDetail not found with id " + id));
        return detailMapper.toDto(detail);
    }

    @Override
    public InvoiceDetailDTO create(InvoiceDetailDTO dto) {
        Invoice invoice = invoiceRepo.findById(dto.getInvoiceId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + dto.getInvoiceId()));
        InventoryProduct product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + dto.getProductId()));

        InvoiceDetail detail = detailMapper.toEntity(dto);
        detail.setFinInvdInv(invoice);
        detail.setFinInvdProd(product);
        BigDecimal total = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        detail.setFinInvdTotalPrice(total);
        detail.setFinInvdCreatedAt(LocalDateTime.now());

        InvoiceDetail saved = detailRepo.save(detail);
        return detailMapper.toDto(saved);
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
        detailMapper.updateEntity(dto, detail);
        BigDecimal total = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        detail.setFinInvdTotalPrice(total);
        // createdAt remains unchanged

        InvoiceDetail updated = detailRepo.save(detail);
        return detailMapper.toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!detailRepo.existsById(id)) {
            throw new EntityNotFoundException("InvoiceDetail not found with id " + id);
        }
        detailRepo.deleteById(id);
    }

}