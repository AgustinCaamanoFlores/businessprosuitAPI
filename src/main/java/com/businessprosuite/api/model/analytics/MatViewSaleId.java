package com.businessprosuite.api.model.analytics;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class MatViewSaleId implements Serializable {
    //private static final long serialVersionUID = -7466105043163999805L;
    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "month", nullable = false)
    private Integer month;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MatViewSaleId entity = (MatViewSaleId) o;
        return Objects.equals(this.month, entity.month) &&
                Objects.equals(this.year, entity.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }

}