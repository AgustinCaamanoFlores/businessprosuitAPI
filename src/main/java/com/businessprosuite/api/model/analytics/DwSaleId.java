package com.businessprosuite.api.model.analytics;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class DwSaleId implements Serializable {
    //private static final long serialVersionUID = -3042273462018634243L;
    @NotNull
    @Column(name = "dt_date", nullable = false)
    private LocalDate dtDate;

    @NotNull
    @Column(name = "dc_id", nullable = false)
    private Integer dcId;

    @NotNull
    @Column(name = "dp_id", nullable = false)
    private Integer dpId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DwSaleId entity = (DwSaleId) o;
        return Objects.equals(this.dcId, entity.dcId) &&
                Objects.equals(this.dpId, entity.dpId) &&
                Objects.equals(this.dtDate, entity.dtDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dcId, dpId, dtDate);
    }

}