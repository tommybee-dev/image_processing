package com.tobee.image.proc.incubator.filters;

import java.awt.image.Kernel;

import com.tobee.image.proc.incubator.PPMImage;
import com.tobee.image.proc.incubator.Pixel;

public abstract class Filter {
	public static int ZERO_EDGES = 0;
	public static int CLAMP_EDGES = 1;
	public static int WRAP_EDGES = 2;

	protected Kernel kernel = null;
	public boolean alpha = true;
	private int edgeAction = CLAMP_EDGES;
	
	public abstract void filter( final PPMImage src, final PPMImage dst );
	public abstract void filter( final PPMImage src, final PPMImage dst, final int iterCnt );
	/*
	public int[] getRGB( final PPMImage image, int[] pixels ) {
		int height = image.getHeight();
		
		Pixel[] raw_pixels = image.getPixel();
		
		int wpix[] = null;
		int offset = 0;
		
		for(int i =0; i< height; i++)
		{
			wpix = raw_pixels[i].getRGBValue();
			System.arraycopy(wpix, 0, pixels, offset, wpix.length);
			offset += wpix.length;
		}
		
		return pixels;
    }
	
	public void setRGB( PPMImage image, int[] pixels ) {
		int height = image.getHeight();
		int width = image.getWidth();
		Pixel[] raw_pixels = new Pixel[height];
		
		int wpix[] = new int[width];
		int offset = 0;
		
		for(int i =0; i< height; i++)
		{
			raw_pixels[i] = new Pixel(width);
			System.arraycopy(pixels, offset, wpix, 0, wpix.length);
			offset += wpix.length;
			
			raw_pixels[i].setRGBValue(wpix);
		}
		
		image.setPixel(raw_pixels);
    }
    */
}
