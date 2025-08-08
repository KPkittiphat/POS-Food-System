package util;

import javax.swing.ImageIcon;
import java.net.URL;

public class IconLoader {
    public static ImageIcon loadIcon(String path) {
        URL url = IconLoader.class.getResource("/icons/" + path);
        if (url != null) {
            return new ImageIcon(url);
        }
        System.err.println("Icon not found: /icons/" + path);
        return null;
    }

    public static ImageIcon loadImage(String path) {
        URL url = IconLoader.class.getResource("/images/" + path);
        if (url != null) {
            return new ImageIcon(url);
        }
        System.err.println("Image not found: /images/" + path);
        return null;
    }
}
