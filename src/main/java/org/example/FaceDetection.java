package org.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class FaceDetection {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    private JFrame frame;
    private VideoCapture capture;

    public FaceDetection() {
        frame = new JFrame("Face Detection");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);

        capture = new VideoCapture(0);
        capture.set(3, 640);
        capture.set(4, 480);
    }

    public void runMainLoop(String[] args) {
        CascadeClassifier faceDetector = new CascadeClassifier("C:/Users/joaqu/Downloads/opencv/sources/data/haarcascades_cuda/haarcascade_frontalface_default.xml");

        Mat webcamImage = new Mat();
        MatOfRect faceDetections = new MatOfRect();

        while (true) {
            capture.read(webcamImage);

            if (!webcamImage.empty()) {
                faceDetector.detectMultiScale(webcamImage, faceDetections);

                for (Rect rect : faceDetections.toArray()) {
                    Imgproc.rectangle(webcamImage, new Point(rect.x, rect.y),
                            new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 0, 255), 2);
                }

                frame.setSize(webcamImage.width() + 40, webcamImage.height() + 60);
                frame.setVisible(true);
                FrameFaceDetection display = new FrameFaceDetection(webcamImage);
                frame.setContentPane(display);
                frame.validate();
            } else {
                System.out.println(" --(!) No captured frame -- Break!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FaceDetection().runMainLoop(args));
    }

    class FrameFaceDetection extends javax.swing.JPanel {
        private static final long serialVersionUID = 1L;
        private Mat image;

        public FrameFaceDetection(Mat image) {
            this.image = image;
        }
    }
}