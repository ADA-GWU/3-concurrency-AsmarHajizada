package imageprocessor;

import imageprocessor.utils.ImageUtils;
import imageprocessor.utils.ScreenUtils;
import imageprocessor.processor.SingleThreadProcessor;
import imageprocessor.processor.MultiThreadProcessor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {

        ScreenUtils.printSystemInfo();
        
        if (args.length < 3) {
            System.out.println("Usage: java Main <file name> <square size> <processing mode>");
            return;
        }

        String inputFile = args[0];
        int squareSize = Integer.parseInt(args[1]);
        String mode = args[2].toUpperCase(); // to accept lower case as well

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
            } else if ("M".equals(mode)) {
                System.out.println("Processing in multi-threaded mode...");
                MultiThreadProcessor.processImage(originalImage, visualizationImage, squareSize);
            } else {
                System.out.println("Invalid mode. Use 'S' for single-threaded or 'M' for multi-threaded.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}