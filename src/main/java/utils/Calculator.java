package utils;
import face.Point;
import face.Vector;

import  static  java.lang.Math.*;
public class Calculator {
    public static double getEuclidean(Vector v1, Vector v2) {
        Point point = new Point(
                v1.getAx() - v2.getAx(),
                v1.getAy() - v2.getAy()
        );
        return  sqrt(pow(point.x,2) + pow(point.y, 2));
    }
    public static double[] getManhattan(Vector v1, Vector v2) {
        Point point = new Point(
                v1.getAx() - v2.getAx(),
                v1.getAy() - v2.getAy()
        );
        return new double[]{abs(point.y), abs(point.y)};
    }

}
