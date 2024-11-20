package imageprocessor.utils;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenUtils {

    /**
     * Gets the maximum screen size available.
     * @return The maximum screen size.
     */
    public static Dimension getMaxScreenSize() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();

        int width = dm.getWidth();
        int height = dm.getHeight();

        return new Dimension(width, height);
    }

    /**
     * Checks if the image needs to be resized based on screen dimensions.
     * @return True if resizing is needed, otherwise false.
     */
    public static boolean needsResizing(BufferedImage image) {
        Dimension screenSize = getMaxScreenSize();
        return image.getWidth() > screenSize.width || image.getHeight() > screenSize.height;
    }
}