/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentmanegment;

/**
 *
 * @author ramla
 */




import com.github.sarxos.webcam.Webcam;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureAndSaveImage {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Webcam webcam = Webcam.getDefault();
        if (webcam == null) {
            System.out.println("No webcam detected");
            return;
        }

        webcam.open();
        BufferedImage image = webcam.getImage();
        webcam.close();

        if (image != null) {
            // Convert BufferedImage to Mat
            Mat matImage = bufferedImageToMat(image);

            // Load Haar Cascade XML file
            CascadeClassifier faceDetector = new CascadeClassifier("path/to/haarcascade_frontalface_default.xml");
            MatOfRect faceDetections = new MatOfRect();

            // Detect faces
            faceDetector.detectMultiScale(matImage, faceDetections);

            // Crop the face
            if (faceDetections.toArray().length > 0) {
                Rect rect = faceDetections.toArray()[0];
                matImage = new Mat(matImage, rect);
            } else {
                System.out.println("No face detected.");
                return;
            }

            // Convert Mat back to BufferedImage
            image = matToBufferedImage(matImage);

            File directory = new File("/path/to/directory12");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(System.currentTimeMillis()));
            String filename = "capture_" + timestamp + ".jpg";
            File outputFile = new File(directory, filename);

            try {
                ImageIO.write(image, "JPG", outputFile);
                System.out.println("Image captured and saved successfully: " + filename);

                // Convert the image to byte array
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(image, "jpg", baos);
                    byte[] imageData = baos.toByteArray();
                    System.out.println("Image converted to byte array successfully.");

                    
                   
                } catch (IOException e) {
                    System.out.println("Error converting image to byte array: " + e.getMessage());
                }
            } catch (IOException e) {
                System.err.println("Error capturing image or saving to file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: No image captured from webcam");
        }
    }

   

    private static Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        int[] data = new int[bi.getWidth() * bi.getHeight() * (int) mat.elemSize()];
        bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), data, 0, bi.getWidth());
        mat.put(0, 0, data);
        return mat;
    }

    private static BufferedImage matToBufferedImage(Mat mat) {
        int width = mat.width();
        int height = mat.height();
        int channels = mat.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        mat.get(0, 0, sourcePixels);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
        return image;
    }
}

