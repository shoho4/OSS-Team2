import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
public class Main {
    
    static {
        // Load the native OpenCV library
        System.loadLibrary("opencv_java2411");
    }
 
    public static void main(String[] args) {
        // Register the default camera
        VideoCapture cap = new VideoCapture(0);
 
        // Check if video capturing is enabled
        if (!cap.isOpened()) {
            System.exit(-1);
        }
 
        // Matrix for storing image
        Mat image = new Mat();
        // Frame for displaying image
        MyFrame frame = new MyFrame();
        frame.setVisible(true);
 
        // Main loop
        while (true) {
            // Read current camera frame into matrix
            cap.read(image);
            // Render frame if the camera is still acquiring images
            if (!image.empty()) {
                frame.render(image);
            } else {
                System.out.println("No captured frame -- camera disconnected");
            }
        }
    }
}
