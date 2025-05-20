package com.businessprosuite.api.model.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "int_translations", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "int_unique", columnNames = {"int_lang", "int_key"})
})
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "int_id", nullable = false)
    private Integer id;

    @Size(max = 2)
    @NotNull
    @Column(name = "int_lang", nullable = false, length = 2)
    private String intLang;

    @Size(max = 100)
    @NotNull
    @Column(name = "int_key", nullable = false, length = 100)
    private String intKey;

    @Size(max = 255)
    @NotNull
    @Column(name = "int_value", nullable = false)
    private String intValue;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "int_created_at", nullable = false)
    private LocalDateTime intCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "int_updated_at", nullable = false)
    private LocalDateTime intUpdatedAt;

}