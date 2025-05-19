package com.businessprosuite.api.model.audit;

import com.businessprosuite.api.model.security.SecurityUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "aud_login_attempts", schema = "BusinessProSuite", indexes = {
        @Index(name = "aula_user_idx", columnList = "aula_user_id")
})
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aula_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "aula_user_id")
    private SecurityUser aulaUser;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "aula_attempt_time", nullable = false)
    private LocalDateTime aulaAttemptTime;

    @Size(max = 100)
    @NotNull
    @Column(name = "aula_ip", nullable = false, length = 100)
    private String aulaIp;

    @NotNull
    @Column(name = "aula_success", nullable = false)
    private Byte aulaSuccess;

}