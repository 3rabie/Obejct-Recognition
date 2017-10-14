/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectrecv100;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.face.Face;
import org.opencv.face.FaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

/**
 *
 * @author rabie
 */
public class ObjectRec {

    FaceRecognizer fr = Face.createLBPHFaceRecognizer(2, 10, 10, 10, 190d);

    public Size loadTrainDir(String dir) {
        Size s = null;
        int label = 0;
        List<Mat> images = new ArrayList<>();
        List<java.lang.Integer> labels = new ArrayList<>();
        File node = new File(dir);
        String[] subNode = node.list();
        for (String p : subNode) {
            System.out.println("" + p);
        }
        if (subNode == null) {
            return null;
        }

        for (String person : subNode) {

            File subDir = new File(node, person);
            if (!subDir.isDirectory()) {
                continue;
            }
            File[] pics = subDir.listFiles();
            for (File f : pics) {
                Mat m = Imgcodecs.imread(f.getAbsolutePath(), 0);
                Imgproc.GaussianBlur(m, m, new Size(5, 5), 5);
               Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
                Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
               Imgproc.erode(m, m, element);
               Imgproc.dilate(m, m, element2);
         //      Imgproc.threshold(m, m,
		//					2, 255, Imgproc.THRESH_BINARY);
               Imgproc.equalizeHist(m, m);
                if (!m.empty()) {
                    images.add(m);
                    labels.add(label);
                    fr.setLabelInfo(label, subDir.getName());
                    s = m.size();
                }
            }
            label++;
        }
        fr.train(images, Converters.vector_int_to_Mat(labels));
        return s;
    }

    public String predict(Mat img) {
        int[] id = {-1};
        double[] dist = {-1};
        fr.predict(img, id, dist);
        if (id[0] == -1 ) {
            return "Unknown";
        } else {
            double d = ((int) (dist[0] * 100));
            return fr.getLabelInfo(id[0]);
        }
    }
}
