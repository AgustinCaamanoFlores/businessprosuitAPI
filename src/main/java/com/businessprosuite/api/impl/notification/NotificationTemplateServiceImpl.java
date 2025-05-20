package com.businessprosuite.api.impl.notification;

import com.businessprosuite.api.dto.notification.NotificationTemplateDTO;
import com.businessprosuite.api.model.notification.NotificationTemplate;
import com.businessprosuite.api.repository.notification.NotificationTemplateRepository;
import com.businessprosuite.api.service.notification.NotificationTemplateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private final NotificationTemplateRepository templateRepo;

    @Override
    public List<NotificationTemplateDTO> findAll() {
        return templateRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationTemplateDTO findById(Integer id) {
        NotificationTemplate tpl = templateRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NotificationTemplate not found with id " + id));
        return toDto(tpl);
    }

    @Override
    public NotificationTemplateDTO create(NotificationTemplateDTO dto) {
        NotificationTemplate tpl = new NotificationTemplate();
        tpl.setName(dto.getName());
        tpl.setSubject(dto.getSubject());
        tpl.setBody(dto.getBody());
        NotificationTemplate saved = templateRepo.save(tpl);
        return toDto(saved);
    }

    @Override
    public NotificationTemplateDTO update(Integer id, NotificationTemplateDTO dto) {
        NotificationTemplate tpl = templateRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NotificationTemplate not found with id " + id));

        tpl.setName(dto.getName());
        tpl.setSubject(dto.getSubject());
        tpl.setBody(dto.getBody());
        NotificationTemplate updated = templateRepo.save(tpl);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!templateRepo.existsById(id)) {
            throw new EntityNotFoundException("NotificationTemplate not found with id " + id);
        }
        templateRepo.deleteById(id);
    }

    private NotificationTemplateDTO toDto(NotificationTemplate tpl) {
        return NotificationTemplateDTO.builder()
                .id(tpl.getId())
                .name(tpl.getName())
                .subject(tpl.getSubject())
                .body(tpl.getBody())
                .build();
    }
}
