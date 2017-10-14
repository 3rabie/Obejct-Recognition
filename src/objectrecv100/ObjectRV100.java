package objectrecv100;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class ObjectRV100 {

    static JFrame frame;
    static JLabel lbl;
    static ImageIcon icon;
    static JButton savedetectedface = new JButton("Save face");
    static JPanel panel = new JPanel(new BorderLayout(5, 5));
    static Size size;
    static String s;
    static List<String> Names = new ArrayList<>();
    static boolean cond = true;

    public static void main(String[] args) {

        System.load("/home/rabie/Downloads/opencv-master/build/lib/libopencv_java310.so");

        CascadeClassifier face_cascade = new CascadeClassifier();
        //face_cascade.load("/home/rabie/NetBeansProjects/FaceR-v1.0.1/src/data/cascades/lbpcascade_frontalface.xml");
        face_cascade.load("/home/rabie/GP/opencv-haar-classifier-training/classifier/cascade.xml");
        
        //FaceRec face = new FaceRec();

        /*Size trainSize = face.loadTrainDir("/home/rabie/NetBeansProjects/FaceR-v1.0.1/src/data/persons");
        if (trainSize != null) {
            System.out.println("facerec trained: " + (trainSize != null) + " !");
        }
        welcomemessage welmsg = new welcomemessage();
*/
        VideoCapture Camera = new VideoCapture(0);
        //Camera.open(0);

  //      int imageincreament = 1;

        if (Camera.isOpened()) {
            //to continue display the  without stop until close  

            while (true) {

                Mat frame_original = new Mat();
                Mat frame_gray = new Mat();
                MatOfRect faces = new MatOfRect();

                Camera.read(frame_original);

                size = frame_gray.size();

                Imgproc.cvtColor(frame_original, frame_gray, Imgproc.COLOR_BGRA2GRAY);
                Imgproc.GaussianBlur(frame_gray, frame_gray, new Size(5, 5), 5);
                //Imgproc.GaussianBlur(frame_gray, frame_gray, new Size(5, 5), 5);
              //       Imgproc.GaussianBlur(frame_gray, frame_gray, new Size(5, 5), 5);
           
             
                Imgproc.equalizeHist(frame_gray, frame_gray);
                Imgproc.threshold(frame_gray, frame_gray, 25, 255, Imgproc.THRESH_OTSU);

                //load and convert the frames of video to detect faces 
                face_cascade.detectMultiScale(frame_gray, faces);

                //draw a named rectungular surround detected faces 
                for (Rect rect : faces.toArray()) {

                    Imgproc.rectangle(frame_original, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 100, 0), 1);
                    //  Imgcodecs.imwrite(null, frame_gray);
/*                  
                    Mat fi = frame_gray.submat(rect);

                    

                    if (fi.size() != trainSize) // not needed for lbph, but for eigen and fisher
                    {
                        Imgproc.resize(fi, fi, trainSize);
                    }

                    if (trainSize != null) {
                        s = face.predict(fi);
                        if (!"".equals(s)) {
                            Imgproc.putText(frame_original, s, new Point(rect.x, rect.y), 1, 2, new Scalar(0, 0, 255), 2);
                        }

                    }

                    if (savedetectedface.getModel().isPressed()) {
                        Mat image_roi = new Mat(frame_original, new Rect(rect.x, rect.y, rect.width, rect.height));
                        Imgproc.GaussianBlur(image_roi, image_roi, new Size(5, 5), 5);

                    Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
                    Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));

                    Imgproc.erode(image_roi, image_roi, element);
                    Imgproc.dilate(image_roi, image_roi, element2);

                        Size sz = new Size(200, 200);
                        Imgproc.resize(image_roi, image_roi, sz);
                        Imgcodecs.imwrite("/home/rabie/NetBeansProjects/FaceR-v1.0.1/src/data/pic" + imageincreament + ".png", image_roi);
                        imageincreament++;

                    }
*/
                //}
                PushImage(ConvertMat2Image(frame_original));
               

                        welcomemessage wm = new welcomemessage();
                        wm.speak("soda can detected");
                       }

        }} else {
            System.out.println("Couldn't connect to Camera");
        }
    
    }
    private static BufferedImage ConvertMat2Image(Mat VideoData) {

        MatOfByte byteMaT = new MatOfByte();
        Imgcodecs.imencode(".jpg", VideoData, byteMaT);
        byte[] byteArray = byteMaT.toArray();
        BufferedImage videoframe;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            videoframe = ImageIO.read(in);

        } catch (IOException e) {
            return null;
        }
        return videoframe;
    }

//create a frame to but the video on it 
    public static void FrameSetup() {
        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(700, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    //method to check the frame and the image icon to display the video farmes on it 
    public static void PushImage(Image img) {
        //check  the frame creation and display
        if (frame == null) {
            FrameSetup();
        }
        //check the lable to remove old frame(image) and put the new one 
        if (lbl != null) {
            frame.remove(lbl);
        }
        icon = new ImageIcon(img);

        lbl = new JLabel();
        lbl.setIcon(icon);

        frame.add(lbl);
        frame.add(panel);
        panel.add(savedetectedface, BorderLayout.CENTER);

        //Frame validate update 
        frame.revalidate();
    }

}
