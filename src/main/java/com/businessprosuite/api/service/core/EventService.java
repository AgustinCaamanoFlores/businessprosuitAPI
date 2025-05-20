package com.businessprosuite.api.service.core;

import com.businessprosuite.api.dto.core.EventDTO;
import java.util.List;

public interface EventService {
    List<EventDTO> findAll();
    EventDTO findById(Integer id);
    EventDTO create(EventDTO dto);
    EventDTO update(Integer id, EventDTO dto);
    void delete(Integer id);
}
