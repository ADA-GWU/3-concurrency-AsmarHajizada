package imageprocessor.processor;

import imageprocessor.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;

public class MultiThreadProcessor {
    public static void processImage(BufferedImage originalImage, BufferedImage visualizationImage, int squareSize) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage processedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gProcessed = processedImage.createGraphics();
        gProcessed.drawImage(originalImage, 0, 0, null); 
        gProcessed.dispose();

        JFrame frame = new JFrame("Multi-Threaded Image Processing");
        ImageUtils.ImagePanel imagePanel = new ImageUtils.ImagePanel(visualizationImage);
        frame.add(imagePanel);
        frame.setSize(visualizationImage.getWidth(), visualizationImage.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            int numThreads = Runtime.getRuntime().availableProcessors();
            int regionHeight = height / numThreads;

            for (int threadIndex = 0; threadIndex < numThreads; threadIndex++) {
                final int startY = threadIndex * regionHeight;
                final int endY = (threadIndex == numThreads - 1) ? height : startY + regionHeight;

                executor.submit(() -> {
                    for (int y = startY; y < endY; y += squareSize) {
                        for (int x = 0; x < width; x += squareSize) {
                            Color averageColor = ImageUtils.calculateAverageColor(originalImage, x, y, squareSize, width, height);
                            synchronized (processedImage) {
                                ImageUtils.fillSquare(processedImage, x, y, squareSize, averageColor, width, height);
                            }

                            synchronized (visualizationImage) {
                                Graphics2D gVis = visualizationImage.createGraphics();
                                gVis.drawImage(processedImage, 0, 0, visualizationImage.getWidth(), visualizationImage.getHeight(), null);
                                gVis.dispose();
                                imagePanel.setImage(visualizationImage);
                            }
                        }
                    }
                });
            }

            executor.shutdown();
            while (!executor.isTerminated()) { }

            String outputFilePath = "output/multi_thread_" + squareSize + ".jpg";
            ImageIO.write(processedImage, "jpg", new File(outputFilePath));
            System.out.println("Saved: " + outputFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}