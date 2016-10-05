package entities;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by steven on 01/09/16.
 */
@Entity
@Table(name = "`SpeedConversions`", schema = "public")
public class SpeedConversionsEntity {
    private Integer id;
    private String description;
    private BigDecimal multiplier;

    @Id
    @Column(name = "`Id`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`Description`", nullable = true, length = 64)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "`Multiplier`", nullable = true, precision = 10)
    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpeedConversionsEntity that = (SpeedConversionsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (multiplier != null ? !multiplier.equals(that.multiplier) : that.multiplier != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (multiplier != null ? multiplier.hashCode() : 0);
        return result;
    }
}
