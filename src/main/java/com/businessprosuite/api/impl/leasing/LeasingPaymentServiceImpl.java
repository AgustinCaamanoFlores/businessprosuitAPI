package com.businessprosuite.api.impl.leasing;

import com.businessprosuite.api.dto.leasing.LeasingPaymentDTO;
import com.businessprosuite.api.model.leasing.LeasingContract;
import com.businessprosuite.api.model.leasing.LeasingPayment;
import com.businessprosuite.api.repository.leasing.LeasingPaymentRepository;
import com.businessprosuite.api.repository.leasing.LeasingContractRepository;
import com.businessprosuite.api.service.leasing.LeasingPaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeasingPaymentServiceImpl implements LeasingPaymentService {

    private final LeasingPaymentRepository paymentRepo;
    private final LeasingContractRepository contractRepo;

    @Override
    public List<LeasingPaymentDTO> findAll() {
        return paymentRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeasingPaymentDTO findById(Integer id) {
        LeasingPayment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeasingPayment not found with id " + id));
        return toDto(payment);
    }

    @Override
    public LeasingPaymentDTO create(LeasingPaymentDTO dto) {
        LeasingContract contract = contractRepo.findById(dto.getContractId())
                .orElseThrow(() -> new EntityNotFoundException("LeasingContract not found with id " + dto.getContractId()));
        LeasingPayment payment = new LeasingPayment();
        payment.setLc(contract);
        payment.setLpFechaVenc(dto.getDueDate());
        payment.setLpMonto(dto.getAmount());
        payment.setLpStatus(dto.getStatus());
        LeasingPayment saved = paymentRepo.save(payment);
        return toDto(saved);
    }

    @Override
    public LeasingPaymentDTO update(Integer id, LeasingPaymentDTO dto) {
        LeasingPayment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeasingPayment not found with id " + id));
        LeasingContract contract = contractRepo.findById(dto.getContractId())
                .orElseThrow(() -> new EntityNotFoundException("LeasingContract not found with id " + dto.getContractId()));
        payment.setLc(contract);
        payment.setLpFechaVenc(dto.getDueDate());
        payment.setLpMonto(dto.getAmount());
        payment.setLpStatus(dto.getStatus());
        LeasingPayment updated = paymentRepo.save(payment);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!paymentRepo.existsById(id)) {
            throw new EntityNotFoundException("LeasingPayment not found with id " + id);
        }
        paymentRepo.deleteById(id);
    }

    private LeasingPaymentDTO toDto(LeasingPayment payment) {
        return LeasingPaymentDTO.builder()
                .id(payment.getId())
                .contractId(payment.getLc().getId())
                .dueDate(payment.getLpFechaVenc())
                .amount(payment.getLpMonto())
                .status(payment.getLpStatus())
                .build();
    }
}