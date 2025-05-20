package com.businessprosuite.api.impl.analytics;

import com.businessprosuite.api.dto.analytics.KpiValorDTO;
import com.businessprosuite.api.model.analytics.KpiDef;
import com.businessprosuite.api.model.analytics.KpiValor;
import com.businessprosuite.api.repository.analytics.KpiValorRepository;
import com.businessprosuite.api.repository.analytics.KpiDefRepository;
import com.businessprosuite.api.service.analytics.KpiValorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KpiValorServiceImpl implements KpiValorService {

    private final KpiValorRepository valorRepo;
    private final KpiDefRepository defRepo;

    @Override
    @Transactional(readOnly = true)
    public List<KpiValorDTO> findAll() {
        return valorRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public KpiValorDTO findById(Integer id) {
        KpiValor valor = valorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("KpiValor not found with id " + id));
        return toDto(valor);
    }

    @Override
    public KpiValorDTO create(KpiValorDTO dto) {
        KpiDef def = defRepo.findById(dto.getKpiId())
                .orElseThrow(() -> new EntityNotFoundException("KpiDef not found with id " + dto.getKpiId()));
        KpiValor valor = new KpiValor();
        valor.setKpi(def);
        valor.setKpvPeriodo(dto.getPeriod());
        valor.setKpvValor(dto.getValue());
        KpiValor saved = valorRepo.save(valor);
        return toDto(saved);
    }

    @Override
    public KpiValorDTO update(Integer id, KpiValorDTO dto) {
        KpiValor valor = valorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("KpiValor not found with id " + id));
        if (dto.getKpiId() != null) {
            KpiDef def = defRepo.findById(dto.getKpiId())
                    .orElseThrow(() -> new EntityNotFoundException("KpiDef not found with id " + dto.getKpiId()));
            valor.setKpi(def);
        }
        valor.setKpvPeriodo(dto.getPeriod());
        valor.setKpvValor(dto.getValue());
        KpiValor updated = valorRepo.save(valor);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!valorRepo.existsById(id)) {
            throw new EntityNotFoundException("KpiValor not found with id " + id);
        }
        valorRepo.deleteById(id);
    }

    private KpiValorDTO toDto(KpiValor valor) {
        return KpiValorDTO.builder()
                .id(valor.getId())
                .kpiId(valor.getKpi().getId())
                .period(valor.getKpvPeriodo())
                .value(valor.getKpvValor())
                .build();
    }
}
