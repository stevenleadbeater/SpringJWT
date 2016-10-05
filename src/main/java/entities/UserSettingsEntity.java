package entities;

import javax.persistence.*;

/**
 * Created by steven on 01/09/16.
 */
@Entity
@Table(name = "`UserSettings`", schema = "public")
public class UserSettingsEntity {
    private Integer id;
    private Integer userId;
    private Integer speedConversionId;

    @Id
    @Column(name = "`Id`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`UserId`", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "`SpeedConversionId`", nullable = true)
    public Integer getSpeedConversionId() {
        return speedConversionId;
    }

    public void setSpeedConversionId(Integer speedConversionId) {
        this.speedConversionId = speedConversionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSettingsEntity that = (UserSettingsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (speedConversionId != null ? !speedConversionId.equals(that.speedConversionId) : that.speedConversionId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (speedConversionId != null ? speedConversionId.hashCode() : 0);
        return result;
    }
}
