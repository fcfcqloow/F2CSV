package face;

import face.interfaces.FacemarkWrapper;
import org.bytedeco.opencv.global.opencv_face;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.Facemark;
import org.bytedeco.opencv.opencv_face.FacemarkLBF;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import utils.FileManager;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_highgui.cvWaitKey;
import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class LBFWrapper implements FacemarkWrapper {
    public Mat img;

    private boolean status = false;
    private List<Point> list = new ArrayList<Point>();
    private Facemark facemark = FacemarkLBF.create();
    private Mat gray = new Mat ();
    private RectVector faces = new RectVector ();
    private Point2fVectorVector landmarks = new Point2fVectorVector();
    private CascadeClassifier faceDetector = new CascadeClassifier(FileManager.BASE_PATH + "models/haarcascade_frontalface_alt.xml");

    public LBFWrapper() {
        Utils.info(FileManager.BASE_PATH + "models/lbfmodel.yaml");
        this.facemark.loadModel(FileManager.BASE_PATH +"models/lbfmodel.yaml");
    }
    public void setImage(String filename) {
        this.img = imread(filename);
        cvtColor(this.img, this.gray, COLOR_BGR2GRAY);
        equalizeHist(this.gray, this.gray );
        faceDetector.detectMultiScale(gray, faces);
    }
    public void setImage(Mat image) {
        this.img = image;
        cvtColor(this.img, this.gray, COLOR_BGR2GRAY);
        equalizeHist(this.gray, this.gray );
        faceDetector.detectMultiScale(gray, faces);
    }
    public boolean fit() {
        this.status = this.facemark.fit(
                this.img,
                this.faces,
                this.landmarks
        );
        if(!this.status) {
            return false;
        }
        for (long i = 0; i < this.landmarks.size(); i++) {
            Point2fVector v = this.landmarks.get(i);
            for (int j = 0;  j < v.size(); j++) {
                Point2f points = v.get(j);
                this.list.add(new Point(points.x(), points.y()));
            }

            opencv_face.drawFacemarks(this.img, v, Scalar.YELLOW);
        }
        return true;
    }
    public List<Point> getList() {
        return this.list;
    }
    public void showFacemark() {
        imshow(" Facial Landmark", this.img);
        cvWaitKey(0);
    }
}
