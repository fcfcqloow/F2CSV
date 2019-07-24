package video;

import face.LBFWrapper;
import face.interfaces.FacemarkWrapper;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import utils.Utils;



public class Video extends Thread{
    private Mat image = new Mat ();
    private FrameGrabber grabber;
    private static Frame frame;
    private CanvasFrame cFrame;
    private OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
    private FacemarkWrapper facemarker = new LBFWrapper();
    private VideoCapture cam;

    public Video() {
        try {
            this.frame = null;
            this.cam = new VideoCapture (0);
            this.grabber = FrameGrabber.createDefault(0);
            this.cFrame = new CanvasFrame("Farme", CanvasFrame.getDefaultGamma() / grabber.getGamma());
            this.cFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        } catch (Exception e) {
            Utils.err("Error, Finish this Program");
            System.exit(0);
        }
    }
    public void capture() throws Exception {
        this.grabber.start();
        while ((this.frame = this.grabber.grab()) != null) {
            if (this.cFrame.isVisible()) {
                Mat image = converterToMat.convert(frame);
                facemarker.setImage(image);
                facemarker.fit();
                this.cFrame.showImage(converterToMat.convert(image));
            }
        }
    }
    public void cvCap() {
        while(this.cam.read(this.image)) {
            facemarker.setImage(this.image);
            facemarker.fit();
            this.cFrame.showImage(converterToMat.convert(this.image));
        }
    }
    public void captureAndPrintout() {
        while(this.cam.read(this.image)) {
            this.cFrame.showImage(converterToMat.convert(this.image));
        }
    }
    public void run() {

    }

}
