package entities;

import javax.persistence.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;
import org.hibernate.spatial.GeometryType;

/**
 * Created by steven on 01/08/16.
 */
@Entity
@Table(name = "`Maps`", schema = "public")
public class MapEntity {
    private Integer zoom;
    private Integer id;
    private String name;
    private Point center;

    @Basic
    @Column(name = "`Zoom`", nullable = true)
    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    @Id
    @Column(name = "`Id`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`Name`", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "`Center`", nullable = true, columnDefinition = "geometry(Point,4326)")
    @Type(type = "org.hibernate.spatial.GeometryType")
    public Point getCenter(){
        return this.center;
    }

    public void setCenter(Point center) { this.center = center; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapEntity that = (MapEntity) o;

        if (zoom != null ? !zoom.equals(that.zoom) : that.zoom != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = zoom != null ? zoom.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
