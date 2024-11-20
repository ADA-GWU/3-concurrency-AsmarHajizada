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
        gProcessed.drawImage(originalImage, 0, 0, null); // Initialize processed image
        gProcessed.dispose();

        // Display the visualization image
        JFrame frame = new JFrame("Multi-Threaded Image Processing");
        ImageUtils.ImagePanel imagePanel = new ImageUtils.ImagePanel(visualizationImage);
        frame.add(imagePanel);
        frame.setSize(visualizationImage.getWidth(), visualizationImage.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Create ExecutorService
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        int regionHeight = height / cores; // Divide image into slices

        try {
            long startTime = System.nanoTime();

            for (int threadIndex = 0; threadIndex < cores; threadIndex++) {
                final int startY = threadIndex * regionHeight;
                final int endY = (threadIndex == cores - 1) ? height : startY + regionHeight;

                executor.submit(() -> {
                    for (int y = startY; y < endY; y += squareSize) {
                        for (int x = 0; x < width; x += squareSize) {
                            // Process each square independently
                            Color averageColor = ImageUtils.calculateAverageColor(originalImage, x, y, squareSize, width, height);
                            ImageUtils.fillSquare(processedImage, x, y, squareSize, averageColor, width, height);
                        }
                    }
                });
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // periodically update the visualization
                Graphics2D gVis = visualizationImage.createGraphics();
                gVis.drawImage(processedImage, 0, 0, visualizationImage.getWidth(), visualizationImage.getHeight(), null);
                gVis.dispose();
                imagePanel.setImage(visualizationImage);

                Thread.sleep(10); // for clearer visualization
            }

            long endTime = System.nanoTime();
            long durationMillis = (endTime - startTime) / 1_000_000;

            // Save the final processed image
            String outputFilePath = "output/multi_thread_" + squareSize + ".jpg";
            ImageIO.write(processedImage, "jpg", new File(outputFilePath));
            System.out.println("Saved: " + outputFilePath);
            System.out.println("Multi-threaded processing took: " + durationMillis + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}