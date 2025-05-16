package com.businessprosuite.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sys_events", schema = "BusinessProSuite")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Lob
    @Column(name = "payload_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private String payloadJson;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

}