package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.PaymentDTO;
import com.businessprosuite.api.model.finance.Payment;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.repository.finance.PaymentRepository;
import com.businessprosuite.api.repository.finance.InvoiceRepository;
import com.businessprosuite.api.service.finance.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepo;
    private final InvoiceRepository invoiceRepo;

    @Override
    public List<PaymentDTO> findAll() {
        return paymentRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO findById(Integer id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id " + id));
        return toDto(payment);
    }

    @Override
    public PaymentDTO create(PaymentDTO dto) {
        Invoice invoice = invoiceRepo.findById(dto.getInvoiceId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + dto.getInvoiceId()));

        Payment payment = new Payment();
        payment.setFinPayInv(invoice);
        payment.setFinPayDate(dto.getDate());
        payment.setFinPayAmount(dto.getAmount());
        payment.setFinPayMethod(dto.getMethod());
        payment.setFinPayCreatedAt(LocalDateTime.now());

        Payment saved = paymentRepo.save(payment);
        return toDto(saved);
    }

    @Override
    public PaymentDTO update(Integer id, PaymentDTO dto) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id " + id));
        Invoice invoice = invoiceRepo.findById(dto.getInvoiceId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + dto.getInvoiceId()));

        payment.setFinPayInv(invoice);
        payment.setFinPayDate(dto.getDate());
        payment.setFinPayAmount(dto.getAmount());
        payment.setFinPayMethod(dto.getMethod());
        // createdAt remains unchanged

        Payment updated = paymentRepo.save(payment);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!paymentRepo.existsById(id)) {
            throw new EntityNotFoundException("Payment not found with id " + id);
        }
        paymentRepo.deleteById(id);
    }

    private PaymentDTO toDto(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .invoiceId(payment.getFinPayInv().getId())
                .date(payment.getFinPayDate())
                .amount(payment.getFinPayAmount())
                .method(payment.getFinPayMethod())
                .createdAt(payment.getFinPayCreatedAt())
                .build();
    }
}