/**
 * 
 */
package com.jiafang.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * 
 */
public class ImageUtils {

	public static enum LOCK {
		WIDTH, HEIGHT, ALL
	}

	public static int[] scale(InputStream src, OutputStream output,
			int extent) throws Exception {
		BufferedImage bi = ImageIO.read(src);
		int srcWidth = bi.getWidth(); // 源图宽度
		int srcHeight = bi.getHeight(); // 源图高度
		
		if (srcWidth <= extent && srcHeight <= extent) {
			throw new Exception("图片尺寸小于或等于要求的尺寸");
		}
		int[] wh = null;
		if (srcWidth > srcHeight) {
			wh = scale(bi, output, extent, -1, LOCK.WIDTH);
		} else {
			wh = scale(bi, output, -1, extent, LOCK.HEIGHT);
		}
		return wh;
	}
	/**
	 * 生成一个正方形的图片
	 * 
	 * @param src
	 *            源图片
	 * @param extent
	 *            长度
	 * @throws Exception
	 */
	public static void createSquareImg(InputStream src, OutputStream output,
			int extent) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int[] wh = scale(src,outputStream,extent);
		
		// 被绽放后的大小
		int srcWidth = wh[0];
		int srcHeight = wh[1];
		
		src = new ByteArrayInputStream(outputStream.toByteArray());
		BufferedImage newImg = ImageIO.read(src);

		if (srcWidth > srcHeight) {
			abscut(newImg, output, (srcWidth - srcHeight) / 2, 0, srcHeight,
					srcHeight);
		} else {
			abscut(newImg, output, 0, (srcHeight - srcWidth) / 2, srcWidth,
					srcWidth);
		}

	}

	public static void abscut(BufferedImage bi, OutputStream output, int x,
			int y, int destWidth, int destHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				// 改进的想法:是否可用多线程加快切割速度
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", output);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缩放图片
	 * 
	 * @param input
	 * @param output
	 * @param lockWidth 锁定宽度
	 * @param lockHeight  锁定高度
	 * @param lock  锁定模式
	 * @return [width,height]
	 * @throws Exception
	 * 
	 */
	public static final int[] scale(BufferedImage bImage, OutputStream output,
			Number lockWidth, Number lockHeight, LOCK lock) throws Exception {
		int originalHeight = bImage.getHeight(null);
		int originalWidth = bImage.getWidth(null);
		int width = 0;
		int height = 0;

		switch (lock) {
		case WIDTH:
			width = lockWidth.intValue();
			height = originalHeight * width / originalWidth;
			break;
		case HEIGHT:
			height = lockHeight.intValue();
			width = originalWidth * height / originalHeight;
			break;
		case ALL:
			width = lockWidth.intValue();
			height = lockHeight.intValue();
			break;
		default:
			break;
		}

		if (width < 1 || height < 1) {
			throw new RuntimeException("width or height is zero!");
		}
		int type = BufferedImage.TYPE_INT_RGB;
		String formatName = "JPG";
		if(BufferedImage.TYPE_INT_ARGB == bImage.getType()||
				BufferedImage.TYPE_4BYTE_ABGR == bImage.getType()||
				BufferedImage.TYPE_4BYTE_ABGR_PRE == bImage.getType()||
				BufferedImage.TYPE_INT_ARGB_PRE == bImage.getType()){
			type = BufferedImage.TYPE_INT_ARGB;
			formatName = "PNG";
		}else if(BufferedImage.TYPE_BYTE_INDEXED==bImage.getType()){
			throw new Exception();
		}
		
		BufferedImage tag = new BufferedImage(width, height, type);
		
		// Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		Graphics2D graphics2d = tag.createGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		Image image = bImage.getScaledInstance(width, height, Image.SCALE_FAST);
		graphics2d.drawImage(image , 0, 0, null);
		ImageIO.write(tag, formatName, output);
		return new int[] { width, height };
	}
	
	public static void main(String[] args) throws Exception {
		String inFile = "d:/aa.gif";
		String outFile = "d:/aa2.gif";
//		String inFile = "d:/t1.png";
//		String outFile = "d:/t2.png";
		InputStream input = new FileInputStream(inFile);
		OutputStream output = new FileOutputStream(outFile);
		scale(input, output, 100);
//		bufferedImageTobytes(input, output, 0.5f);
		input.close();
		output.close();
	}

}
