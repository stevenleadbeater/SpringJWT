package mappers.geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import dto.geometry.PointDTO;
import org.springframework.stereotype.Component;

/**
 * Created by steven on 27/08/16.
 */
@Component
public class PointMapper {
    public Point asPoint (PointDTO pointDTO) {
        GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createPoint(new Coordinate(pointDTO.getX(), pointDTO.getY()));
    }

    public PointDTO asPointDTO (Point point) {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setX(point.getX());
        pointDTO.setY(point.getY());
        return pointDTO;
    }
}
