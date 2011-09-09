import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static BufferedImage loadImage(String filename) {
		InputStream in = ImageUtils.class.getResourceAsStream(filename);
		BufferedImage ret = null;
		try {
			BufferedImage tmp = ImageIO.read(in);
			ret = new BufferedImage(tmp.getWidth(), tmp.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = ret.createGraphics();
			g.drawImage(tmp, 0, 0, null);
			g.dispose();
			ret.setAccelerationPriority(1.0F);
		} catch (IOException e) {
			System.err.println("Couldn't load " + filename + "!");
			e.printStackTrace();
		}
		return ret;
	}
	
	public static Rectangle getBoundingRectangle(BufferedImage image) {
		ColorModel model  = image.getColorModel();
		int top = -1;
		int bottom = -1;
		int left = -1;
		int right = -1;
		for (int r = 0; r < image.getHeight(); r++) {
			for (int c = 0; c < image.getWidth(); c++) {
				if (model.getAlpha(image.getRGB(c, r)) != 0) {
					bottom = r;
					if (top == -1) {
						top = r;
					}
					if (left == -1 || c < left) {
						left = c;
					}
					if (c > right) {
						right = c;
					}
				}
			}
		}
		if (top == -1 || bottom == -1 || left == -1 || right == -1) {
			System.err.println("Failed bounding image!");
			return null;
		}
		return new Rectangle(left, top, ((right - left) + 1),
				((bottom - top) + 1));
	}
	
}
