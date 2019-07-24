package motion;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.*;
import utils.Utils;
import javax.swing.*;
import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class MotionDetector {
    private FrameGrabber grabber;
    private OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat ();
    private Mat image;
    private CanvasFrame cFrame;

    public MotionDetector () {
        try {
            this.grabber = OpenCVFrameGrabber.createDefault (0);
            this.cFrame = new CanvasFrame ("Motion Detector");
            this.cFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        } catch (Exception e) {
            Utils.err ("Error, Finish this Program");
            System.exit (0);
        }
    }

    public void capture () throws Exception {
        grabber.start ();
        image = this.getFrame ();
        Mat prevImage = null;

        while ((image = this.getFrame ()) != null) {
            cvtColor (image, image, CV_RGB2GRAY);
            if (prevImage == null) {
                prevImage = image.clone ();
                continue;
            }
            Mat diff = this.getDiff (image, prevImage);
            this.addFigure (diff);

            this.cFrame.showImage (converterToMat.convert (image));
        }
        grabber.stop();
        cFrame.dispose();
    }

    public Mat getDiff(Mat image, Mat prevImage){
        Mat diff = new Mat();
        prevImage.convertTo (prevImage, CV_32F);
        accumulateWeighted (image, prevImage, 0.7);
        prevImage.convertTo (prevImage,CV_8U);
        absdiff (image, prevImage, diff);
        threshold (diff, diff, 64, 255, CV_THRESH_BINARY);
        return diff;
    }

    public void addFigure(Mat diff){
        MatVector contourVec = new MatVector ();
        findContours (diff, contourVec, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);
        double maxArea = 0;

        if (contourVec != null && contourVec.size () > 0) {
            Mat[] contours = contourVec.get ();
            Mat target = contours[0];
            for (Mat contour : contours) {
                double area = contourArea (contour);
                Utils.accept (area);
                if (area > maxArea && area > 0 && area < 100) {
                    maxArea = area;
                    target = contour;
                }
            }

            if (maxArea <= 0) {
                putText (image, "Not Detect", new Point (0, 10), FONT_HERSHEY_PLAIN, 300, Scalar.BLACK);
            } else {
                Rect rect = boundingRect (target);
                int x = rect.x ();
                int y = rect.y ();
                int w = rect.width ();
                int h = rect.height ();
                rectangle (image, new Point (x, y), new Point (x + w, y + h), Scalar.GREEN);
            }
        }
    }

    public Mat getFrame () {
        try {
            return this.converterToMat.convert (this.grabber.grab ());
        } catch (FrameGrabber.Exception e) {
            return null;
        }
    }
}