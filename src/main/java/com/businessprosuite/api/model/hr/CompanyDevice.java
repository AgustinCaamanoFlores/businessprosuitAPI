package com.businessprosuite.api.model.hr;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hr_device", schema = "BusinessProSuite")
public class CompanyDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hr_device_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "hr_device_location", nullable = false, length = 100)
    private String hrDeviceLocation;

    @NotNull
    @Lob
    @Column(name = "device_type", nullable = false)
    private String deviceType;

    @ColumnDefault("1")
    @Column(name = "hr_device_active")
    private Byte hrDeviceActive;

}