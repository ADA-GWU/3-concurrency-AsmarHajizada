package imageprocessor.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageUtils {

    /**
     * Loads the image from file path.
     *
     * @param filePath Path to the input image.
     * @return BufferedImage object.
     * @throws Exception If file loading fails.
     */
    public static BufferedImage loadImage(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("File not found: " + filePath);
        }
        return ImageIO.read(file);
    }

    /**
     * Resizes the image to fit within the given maximum dimensions (maintains the ratio).
     *
     * @param originalImage The original image.
     * @param maxSize The maximum dimensions for resizing.
     * @return The resized image.
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, Dimension maxSize) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        double widthRatio = (double) maxSize.width / originalWidth;
        double heightRatio = (double) maxSize.height / originalHeight;
        double resizeRatio = Math.min(widthRatio, heightRatio);

        if (resizeRatio >= 1.0) {
            return originalImage;
        }

        int newWidth = (int) (originalWidth * resizeRatio);
        int newHeight = (int) (originalHeight * resizeRatio);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resizedImage;
    }

    /**
     * Calculates the average color of a square in the image.
     */
    public static Color calculateAverageColor(BufferedImage image, int startX, int startY, int squareSize, int width, int height) {
        int red = 0, green = 0, blue = 0, count = 0;

        for (int y = startY; y < startY + squareSize && y < height; y++) {
            for (int x = startX; x < startX + squareSize && x < width; x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                red += pixelColor.getRed();
                green += pixelColor.getGreen();
                blue += pixelColor.getBlue();
                count++;
            }
        }

        return new Color(red / count, green / count, blue / count);
    }

    /**
     * Fills square in the processed image with a single color.
     */
    public static void fillSquare(BufferedImage image, int startX, int startY, int squareSize, Color color, int width, int height) {
        for (int y = startY; y < startY + squareSize && y < height; y++) {
            for (int x = startX; x < startX + squareSize && x < width; x++) {
                image.setRGB(x, y, color.getRGB());
            }
        }
    }

    /**
     * Panel for displaying image.
     */
    public static class ImagePanel extends JPanel {
        private BufferedImage image;

        public ImagePanel(BufferedImage img) {
            this.image = img;
        }

        public void setImage(BufferedImage img) {
            this.image = img;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }
}