package com.businessprosuite.api.service.notification;

import com.businessprosuite.api.dto.notification.NotificationQueueDTO;
import java.util.List;

public interface NotificationQueueService {
    List<NotificationQueueDTO> findAll();
    NotificationQueueDTO findById(Integer id);
    NotificationQueueDTO create(NotificationQueueDTO dto);
    NotificationQueueDTO update(Integer id, NotificationQueueDTO dto);
    void delete(Integer id);
    void dispatchPending();
}
