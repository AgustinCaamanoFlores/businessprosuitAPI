package com.businessprosuite.api.repository.notification;

import com.businessprosuite.api.model.notification.NotificationQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationQueueRepository extends JpaRepository<NotificationQueue, Integer> {
    java.util.List<NotificationQueue> findBySentFalse();
}