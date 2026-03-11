package simpletexteditor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class IconUtils {

    public static ImageIcon recolor(ImageIcon icon, Color color) {
        Image img = icon.getImage();

        BufferedImage buffered = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = buffered.createGraphics();
        g.drawImage(img, 0, 0, null);

        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0, 0, buffered.getWidth(), buffered.getHeight());

        g.dispose();

        return new ImageIcon(buffered);
    }
}
