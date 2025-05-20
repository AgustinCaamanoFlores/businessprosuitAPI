package com.businessprosuite.api.impl.core;

import com.businessprosuite.api.dto.core.EventDTO;
import com.businessprosuite.api.model.core.Event;
import com.businessprosuite.api.repository.core.EventRepository;
import com.businessprosuite.api.service.core.EventService;
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
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepo;

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> findAll() {
        return eventRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventDTO findById(Integer id) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + id));
        return toDto(event);
    }

    @Override
    public EventDTO create(EventDTO dto) {
        Event event = new Event();
        event.setEventType(dto.getEventType());
        event.setPayloadJson(dto.getPayloadJson());
        event.setOccurredAt(dto.getOccurredAt() != null ? dto.getOccurredAt() : LocalDateTime.now());
        Event saved = eventRepo.save(event);
        return toDto(saved);
    }

    @Override
    public EventDTO update(Integer id, EventDTO dto) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + id));
        event.setEventType(dto.getEventType());
        event.setPayloadJson(dto.getPayloadJson());
        // preserve original occurredAt unless provided
        if (dto.getOccurredAt() != null) {
            event.setOccurredAt(dto.getOccurredAt());
        }
        Event updated = eventRepo.save(event);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!eventRepo.existsById(id)) {
            throw new EntityNotFoundException("Event not found with id " + id);
        }
        eventRepo.deleteById(id);
    }

    private EventDTO toDto(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .eventType(event.getEventType())
                .payloadJson(event.getPayloadJson())
                .occurredAt(event.getOccurredAt())
                .build();
    }
}
