package com.businessprosuite.api.impl.notification;

import com.businessprosuite.api.dto.notification.NotificationQueueDTO;
import com.businessprosuite.api.model.notification.NotificationQueue;
import com.businessprosuite.api.model.notification.NotificationTemplate;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.notification.NotificationQueueRepository;
import com.businessprosuite.api.repository.notification.NotificationTemplateRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.notification.NotificationQueueService;
import com.businessprosuite.api.service.notification.EmailSenderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationQueueServiceImpl implements NotificationQueueService {

    private final NotificationQueueRepository queueRepo;
    private final NotificationTemplateRepository templateRepo;
    private final SecurityUserRepository userRepo;
    private final EmailSenderService emailService;

    @Override
    public List<NotificationQueueDTO> findAll() {
        return queueRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationQueueDTO findById(Integer id) {
        NotificationQueue nq = queueRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NotificationQueue not found with id " + id));
        return toDto(nq);
    }

    @Override
    public NotificationQueueDTO create(NotificationQueueDTO dto) {
        NotificationTemplate tpl = templateRepo.findById(dto.getTemplateId())
                .orElseThrow(() -> new EntityNotFoundException("NotificationTemplate not found with id " + dto.getTemplateId()));
        SecurityUser user = null;
        if (dto.getUserId() != null) {
            user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
        }
        NotificationQueue nq = new NotificationQueue();
        nq.setTpl(tpl);
        nq.setSecus(user);
        nq.setPayloadJson(dto.getPayloadJson());
        nq.setSendAt(dto.getSendAt());
        nq.setSent(dto.getSent() != null ? dto.getSent() : false);
        nq.setResultMessage(dto.getResultMessage());
        NotificationQueue saved = queueRepo.save(nq);
        return toDto(saved);
    }

    @Override
    public NotificationQueueDTO update(Integer id, NotificationQueueDTO dto) {
        NotificationQueue nq = queueRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NotificationQueue not found with id " + id));
        NotificationTemplate tpl = templateRepo.findById(dto.getTemplateId())
                .orElseThrow(() -> new EntityNotFoundException("NotificationTemplate not found with id " + dto.getTemplateId()));
        SecurityUser user = null;
        if (dto.getUserId() != null) {
            user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
        }
        nq.setTpl(tpl);
        nq.setSecus(user);
        nq.setPayloadJson(dto.getPayloadJson());
        nq.setSendAt(dto.getSendAt());
        nq.setSent(dto.getSent());
        nq.setResultMessage(dto.getResultMessage());
        NotificationQueue updated = queueRepo.save(nq);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!queueRepo.existsById(id)) {
            throw new EntityNotFoundException("NotificationQueue not found with id " + id);
        }
        queueRepo.deleteById(id);
    }

    @Override
    public void dispatchPending() {
        List<NotificationQueue> pending = queueRepo.findBySentFalse();
        for (NotificationQueue nq : pending) {
            if (nq.getSecus() == null || nq.getSecus().getSecusEmail() == null) {
                continue;
            }
            emailService.sendEmail(
                    nq.getSecus().getSecusEmail(),
                    nq.getTpl().getSubject(),
                    nq.getTpl().getBody()
            );
            nq.setSent(true);
            nq.setResultMessage("SENT");
            queueRepo.save(nq);
        }
    }

    private NotificationQueueDTO toDto(NotificationQueue nq) {
        return NotificationQueueDTO.builder()
                .id(nq.getId())
                .templateId(nq.getTpl().getId())
                .userId(nq.getSecus() != null ? nq.getSecus().getId() : null)
                .payloadJson(nq.getPayloadJson())
                .sendAt(nq.getSendAt())
                .sent(nq.getSent())
                .resultMessage(nq.getResultMessage())
                .build();
    }
}