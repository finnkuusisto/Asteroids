import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
	
	public static List<Rectangle> getBoundingRectangles(BufferedImage image,
			int wBoxes, int hBoxes) {
		List<Rectangle> ret = new ArrayList<Rectangle>();
		int boxW = (image.getWidth() / wBoxes);
		int boxH = (image.getHeight() / hBoxes);
		for (int xNum = 0; xNum < wBoxes; xNum++) {
			boolean last = (xNum == (wBoxes - 1));
			int x1 = (xNum * boxW);
			int x2 = (!last) ? (x1 + boxW) : image.getWidth(); 
			for (int yNum = 0; yNum < hBoxes; yNum++) {
				last = yNum == (hBoxes - 1);
				int y1 = (yNum * boxH);
				int y2 = (!last) ? (y1 + boxH) : image.getHeight();
				Rectangle rect = ImageUtils.getBoundingRectangle(image,
						x1, y1, x2, y2);
				if (rect != null) {
					ret.add(rect);
				}
			}
		}
		return ret;
	}
	
	public static Rectangle getBoundingRectangle(BufferedImage image) {
		return ImageUtils.getBoundingRectangle(image, 0, 0, image.getWidth(),
				image.getHeight());
	}
	
	public static Rectangle getBoundingRectangle(BufferedImage image,
			int x1, int y1, int x2, int y2) {
		ColorModel model  = image.getColorModel();
		int top = -1;
		int bottom = -1;
		int left = -1;
		int right = -1;
		for (int r = y1; r < y2; r++) {
			for (int c = x1; c < x2; c++) {
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
