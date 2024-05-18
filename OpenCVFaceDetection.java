/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentmanegment;

/**
 *
 * @author ramla
 */


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;

public class OpenCVFaceDetection {
    
   



   
class ImageCapture {

    public static void main(String[] args) {
     
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

       
        VideoCapture camera = new VideoCapture(0);

     
        if (!camera.isOpened()) {
            System.out.println("Error: Unable to access camera.");
            return;
        }

       
        Mat frame = new Mat();

       
        if (camera.read(frame)) {
         
            String filename = "captured_image.jpg";
            Imgcodecs.imwrite(filename, frame);

           
            System.out.println("Image captured and saved as " + filename);
        } else {
           
            System.out.println("Error: Failed to capture frame.");
        }

        
        camera.release();
    }
}





 

