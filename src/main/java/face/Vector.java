package face;

import lombok.Data;
import lombok.NonNull;


@Data
public class Vector {
    private double ax;
    private double ay;
    public Vector(@NonNull Point p1, @NonNull Point p2){
        this.ax = p1.x - p2.x;
        this.ay = p1.y - p2.y;
    }
}
