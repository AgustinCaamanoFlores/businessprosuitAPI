package com.businessprosuite.api.model;

import com.businessprosuite.api.model.security.SecUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "notif_queue", schema = "BusinessProSuite")
public class NotifQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nq_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tpl_id", nullable = false)
    private com.businessprosuite.api.model.NotifTemplate tpl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secus_id")
    private SecUser secus;

    @Lob
    @Column(name = "payload_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private String payloadJson;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "send_at", nullable = false)
    private LocalDateTime sendAt;

    @Builder.Default
    @NotNull
    @ColumnDefault("0")
    @Column(name = "sent", nullable = false)
    private Boolean sent = false;

    @Lob
    @Column(name = "result_message")
    private String resultMessage;

}