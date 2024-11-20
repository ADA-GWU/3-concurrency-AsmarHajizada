package imageprocessor.processor;

import imageprocessor.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SingleThreadProcessor {
    public static void processImage(BufferedImage originalImage, BufferedImage visualizationImage, int squareSize) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage processedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gProcessed = processedImage.createGraphics();
        gProcessed.drawImage(originalImage, 0, 0, null);
        gProcessed.dispose();

        JFrame frame = new JFrame("Single-Threaded Image Processing");
        ImageUtils.ImagePanel imagePanel = new ImageUtils.ImagePanel(visualizationImage);
        frame.add(imagePanel);
        frame.setSize(visualizationImage.getWidth(), visualizationImage.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {

            long startTime = System.nanoTime();

            for (int y = 0; y < height; y += squareSize) {
                for (int x = 0; x < width; x += squareSize) {
                    Color averageColor = ImageUtils.calculateAverageColor(originalImage, x, y, squareSize, width, height);
                    ImageUtils.fillSquare(processedImage, x, y, squareSize, averageColor, width, height);

                    Graphics2D gVis = visualizationImage.createGraphics();
                    gVis.drawImage(processedImage, 0, 0, visualizationImage.getWidth(), visualizationImage.getHeight(), null);
                    gVis.dispose();

                    imagePanel.setImage(visualizationImage);
                    Thread.sleep(10); // for clearer visualization
                }
            }

            long endTime = System.nanoTime();
            long durationMillis = (endTime - startTime) / 1_000_000; 

            String outputFilePath = "output/single_thread_" + squareSize + ".jpg";
            ImageIO.write(processedImage, "jpg", new File(outputFilePath));
            System.out.println("Saved: " + outputFilePath);
            System.out.println("Single-threaded processing took: " + durationMillis + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}