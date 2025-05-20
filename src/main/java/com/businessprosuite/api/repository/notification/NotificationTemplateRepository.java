package com.businessprosuite.api.repository.notification;

import com.businessprosuite.api.model.notification.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Integer> {
}