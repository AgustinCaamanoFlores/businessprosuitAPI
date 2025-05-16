package com.businessprosuite.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notif_templates", schema = "BusinessProSuite")
public class NotifTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tpl_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 255)
    @Column(name = "subject")
    private String subject;

    @Lob
    @Column(name = "body")
    private String body;

    @Builder.Default
    @OneToMany(mappedBy = "tpl")
    private Set<NotifQueue> notifQueues = new LinkedHashSet<>();

}