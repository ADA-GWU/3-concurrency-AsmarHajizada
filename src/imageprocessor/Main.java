package imageprocessor;

import imageprocessor.utils.ImageUtils;
import imageprocessor.utils.ScreenUtils;
import imageprocessor.processor.SingleThreadProcessor;
import imageprocessor.processor.MultiThreadProcessor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    /**
     * Main class to process the image.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {

        ScreenUtils.printSystemInfo();

        if (args.length < 3) {
            System.out.println("Usage: java Main <file name> <square size> <processing mode>");
            return;
        }

        // Validate and parse input arguments
        String inputFile = args[0];
        int squareSize = validateSquareSize(args[1]);
        if (squareSize <= 0) return;

        String mode = args[2].toUpperCase();
        if (!"S".equals(mode) && !"M".equals(mode)) {
            System.out.println("Invalid mode. Use 'S' for single-threaded or 'M' for multi-threaded.");
            return;
        }

        try {
            BufferedImage originalImage = ImageUtils.loadImage(inputFile);

            // check if resizing is needed based on screen dimensions
            Dimension screenSize = ScreenUtils.getMaxScreenSize();
            boolean needsResizing = ScreenUtils.needsResizing(originalImage);

            BufferedImage visualizationImage;
            if (needsResizing) {
                System.out.println("Image exceeds screen size. Creating resized visualization...");
                visualizationImage = ImageUtils.resizeImage(originalImage, screenSize);
            } else {
                System.out.println("No resizing needed.");
                visualizationImage = originalImage;
            }

            if ("S".equals(mode)) {
                System.out.println("Processing in single-threaded mode...");
                SingleThreadProcessor.processImage(originalImage, visualizationImage, squareSize);
            } else {
                System.out.println("Processing in multi-threaded mode...");
                MultiThreadProcessor.processImage(originalImage, visualizationImage, squareSize);
            }

        } catch (Exception e) {
            System.out.println("An error occurred during image processing: " + e.getMessage());
            // e.printStackTrace();  // uncomment for debugging if needed
        }
    }

    /**
     * Validates and parses the square size from input arguments.
     * @param squareSizeArg The square size as a string.
     * @return Parsed square size, or 0 if invalid.
     */
    private static int validateSquareSize(String squareSizeArg) {
        try {
            int squareSize = Integer.parseInt(squareSizeArg);
            if (squareSize <= 0) {
                System.out.println("Square size must be a positive integer.");
                return 0;
            }
            return squareSize;
        } catch (NumberFormatException e) {
            System.out.println("Square size must be an integer.");
            return 0;
        }
    }
}