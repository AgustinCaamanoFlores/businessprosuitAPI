package com.businessprosuite.api.repository.notification;

import com.businessprosuite.api.model.notification.NotificationQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifQueueRepository extends JpaRepository<NotificationQueue, Integer> {
}