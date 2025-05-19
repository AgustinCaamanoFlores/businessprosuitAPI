package com.businessprosuite.api.repository.notification;

import com.businessprosuite.api.model.notification.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifTemplateRepository extends JpaRepository<NotificationTemplate, Integer> {
}