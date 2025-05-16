package com.businessprosuite.api.model.finance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_conv", schema = "BusinessProSuite", indexes = {
        @Index(name = "fin_conv_from_idx", columnList = "fin_conv_cur_from"),
        @Index(name = "fin_conv_to_idx", columnList = "fin_conv_cur_to")
})
public class Conv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_conv_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_conv_cur_from", nullable = false)
    private Currency finConvCurFrom;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_conv_cur_to", nullable = false)
    private Currency finConvCurTo;

    @NotNull
    @Column(name = "fin_conv_factor", nullable = false, precision = 10, scale = 4)
    private BigDecimal finConvFactor;

    @Lob
    @Column(name = "fin_conv_description")
    private String finConvDescription;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_conv_created_at", nullable = false)
    private LocalDateTime finConvCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_conv_updated_at", nullable = false)
    private LocalDateTime finConvUpdatedAt;

}