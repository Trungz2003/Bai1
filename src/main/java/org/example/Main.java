package org.example;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Đường dẫn tới ba ảnh cần xử lý
        String[] imagePaths = {"src/main/java/org/example/image_input/Nguoi.png",
                                "src/main/java/org/example/image_input/canh.png",
                                "src/main/java/org/example/image_input/y_te.png"};
        String[] outputPaths = {"src/main/java/org/example/image_output/Nguoi.png",
                                "src/main/java/org/example/image_output/canh.png",
                                "src/main/java/org/example/image_output/y_te.png"};

        for (int i = 0; i < imagePaths.length; i++) {
            // Đọc từng ảnh
            Mat image = Imgcodecs.imread(imagePaths[i]);

            // Ảnh âm tính
            Core.bitwise_not(image, image);

            // Tăng độ tương phản
            Mat contrastedImage = new Mat();
            image.convertTo(contrastedImage, -1, 1.5, 0);

            // Biến đổi log
            Mat imageFloat = new Mat();
            image.convertTo(imageFloat, CvType.CV_32F);
            Core.add(imageFloat, Scalar.all(1), imageFloat);
            Core.log(imageFloat, imageFloat);
            Core.normalize(imageFloat, imageFloat, 0, 255, Core.NORM_MINMAX);
            imageFloat.convertTo(image, CvType.CV_8U);

            // Cân bằng Histogram
            Mat imageGray = new Mat();
            Imgproc.cvtColor(image, imageGray, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(imageGray, imageGray);

            // Lưu từng ảnh sau khi xử lý
            Imgcodecs.imwrite(outputPaths[i], imageGray);
        }
    }
}
