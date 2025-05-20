package com.businessprosuite.api.service.notification;

import com.businessprosuite.api.dto.notification.NotificationTemplateDTO;
import java.util.List;

public interface NotificationTemplateService {
    List<NotificationTemplateDTO> findAll();
    NotificationTemplateDTO findById(Integer id);
    NotificationTemplateDTO create(NotificationTemplateDTO dto);
    NotificationTemplateDTO update(Integer id, NotificationTemplateDTO dto);
    void delete(Integer id);
}