package com.businessprosuite.api.impl.leasing;

import com.businessprosuite.api.dto.leasing.LeasingContractDTO;
import com.businessprosuite.api.model.leasing.LeasingContract;
import com.businessprosuite.api.model.asset.Asset;
import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.leasing.LeasingContractRepository;
import com.businessprosuite.api.repository.asset.AssetRepository;
import com.businessprosuite.api.repository.customer.CustomerRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.leasing.LeasingContractService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeasingContractServiceImpl implements LeasingContractService {

    private final LeasingContractRepository repo;
    private final AssetRepository assetRepo;
    private final CustomerRepository customerRepo;
    private final ConfigCompanyRepository companyRepo;

    @Override
    public List<LeasingContractDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeasingContractDTO findById(Integer id) {
        LeasingContract lc = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeasingContract not found with id " + id));
        return toDto(lc);
    }

    @Override
    public LeasingContractDTO create(LeasingContractDTO dto) {
        Asset asset = assetRepo.findById(dto.getAssetId())
                .orElseThrow(() -> new EntityNotFoundException("Asset not found with id " + dto.getAssetId()));
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + dto.getCustomerId()));
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));

        LeasingContract lc = new LeasingContract();
        lc.setLcAct(asset);
        lc.setLcCus(customer);
        lc.setLcFechaInicio(dto.getStartDate());
        lc.setLcFechaFin(dto.getEndDate());
        lc.setLcCuota(dto.getQuota());
        lc.setLcFrecuencia(dto.getFrequency());
        lc.setConfigCompany(company);

        LeasingContract saved = repo.save(lc);
        return toDto(saved);
    }

    @Override
    public LeasingContractDTO update(Integer id, LeasingContractDTO dto) {
        LeasingContract lc = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeasingContract not found with id " + id));
        Asset asset = assetRepo.findById(dto.getAssetId())
                .orElseThrow(() -> new EntityNotFoundException("Asset not found with id " + dto.getAssetId()));
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + dto.getCustomerId()));
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));

        lc.setLcAct(asset);
        lc.setLcCus(customer);
        lc.setLcFechaInicio(dto.getStartDate());
        lc.setLcFechaFin(dto.getEndDate());
        lc.setLcCuota(dto.getQuota());
        lc.setLcFrecuencia(dto.getFrequency());
        lc.setConfigCompany(company);

        LeasingContract updated = repo.save(lc);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("LeasingContract not found with id " + id);
        }
        repo.deleteById(id);
    }

    private LeasingContractDTO toDto(LeasingContract lc) {
        return LeasingContractDTO.builder()
                .id(lc.getId())
                .assetId(lc.getLcAct().getId())
                .customerId(lc.getLcCus().getId())
                .startDate(lc.getLcFechaInicio())
                .endDate(lc.getLcFechaFin())
                .quota(lc.getLcCuota())
                .frequency(lc.getLcFrecuencia())
                .companyId(lc.getConfigCompany().getId())
                .build();
    }
}
