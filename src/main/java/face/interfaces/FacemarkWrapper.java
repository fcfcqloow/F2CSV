package face.interfaces;

import face.Point;
import org.bytedeco.opencv.opencv_core.Mat;

import java.util.List;

public interface FacemarkWrapper {
    List<Point> getList();
    boolean fit();
    void showFacemark();
    void setImage(String fileName);
    void setImage(Mat image);
    Mat img = null;
}
