package com.businessprosuite.api.impl.subs;

import com.businessprosuite.api.dto.subs.SubsSuscriptionDTO;
import com.businessprosuite.api.model.subs.SubsSuscription;
import com.businessprosuite.api.model.subs.SubsPlan;
import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.subs.SubsSuscriptionRepository;
import com.businessprosuite.api.repository.subs.SubsPlanRepository;
import com.businessprosuite.api.repository.customer.CustomerRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.subs.SubsSuscriptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubsSuscriptionServiceImpl implements SubsSuscriptionService {

    private final SubsSuscriptionRepository subsRepo;
    private final CustomerRepository customerRepo;
    private final SubsPlanRepository planRepo;
    private final ConfigCompanyRepository companyRepo;

    @Override
    public List<SubsSuscriptionDTO> findAll() {
        return subsRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubsSuscriptionDTO findById(Integer id) {
        SubsSuscription s = subsRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SubsSuscription not found with id " + id));
        return toDto(s);
    }

    @Override
    public SubsSuscriptionDTO create(SubsSuscriptionDTO dto) {
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + dto.getCustomerId()));
        SubsPlan plan = planRepo.findById(dto.getPlanId())
                .orElseThrow(() -> new EntityNotFoundException("SubsPlan not found with id " + dto.getPlanId()));
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));

        SubsSuscription s = new SubsSuscription();
        s.setSubsCus(customer);
        s.setSubsPlan(plan);
        s.setSubsFechaInicio(dto.getStartDate());
        s.setSubsProxCobro(dto.getNextCharge());
        s.setSubsEstado(dto.getStatus());
        s.setConfigCompany(company);

        SubsSuscription saved = subsRepo.save(s);
        return toDto(saved);
    }

    @Override
    public SubsSuscriptionDTO update(Integer id, SubsSuscriptionDTO dto) {
        SubsSuscription s = subsRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SubsSuscription not found with id " + id));
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + dto.getCustomerId()));
        SubsPlan plan = planRepo.findById(dto.getPlanId())
                .orElseThrow(() -> new EntityNotFoundException("SubsPlan not found with id " + dto.getPlanId()));
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));

        s.setSubsCus(customer);
        s.setSubsPlan(plan);
        s.setSubsFechaInicio(dto.getStartDate());
        s.setSubsProxCobro(dto.getNextCharge());
        s.setSubsEstado(dto.getStatus());
        s.setConfigCompany(company);

        SubsSuscription updated = subsRepo.save(s);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!subsRepo.existsById(id)) {
            throw new EntityNotFoundException("SubsSuscription not found with id " + id);
        }
        subsRepo.deleteById(id);
    }

    private SubsSuscriptionDTO toDto(SubsSuscription s) {
        return SubsSuscriptionDTO.builder()
                .id(s.getId())
                .customerId(s.getSubsCus().getId())
                .planId(s.getSubsPlan().getId())
                .startDate(s.getSubsFechaInicio())
                .nextCharge(s.getSubsProxCobro())
                .status(s.getSubsEstado())
                .companyId(s.getConfigCompany().getId())
                .build();
    }
}
