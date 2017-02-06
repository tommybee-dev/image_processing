package com.tobee.image.proc.incubator.filters;

import com.tobee.image.proc.PixelUtils;
import com.tobee.image.proc.incubator.PPMImage;

/**
 * A filter which subtracts Gaussian blur from an image, sharpening it.
 * @author Jerry Huxtable
 */
public class GlowFilter extends GaussianFilter {

	static final long serialVersionUID = 5377089073023183684L;

	private float amount = 0.5f;
	
	public GlowFilter() {
		radius = 2;
	}
	
	public void setAmount( float amount ) {
		this.amount = amount;
	}
	
	public float getAmount() {
		return amount;
	}
	

	public void filter( final PPMImage src, PPMImage dst ) {
        int width = src.getWidth();
        int height = src.getHeight();

        if ( dst == null )
            dst = new PPMImage( height, width );

        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        src.getRGB( inPixels );

		if ( radius > 0 ) {
			convolveAndTranspose(kernel, inPixels, outPixels, width, height, alpha, CLAMP_EDGES);
			convolveAndTranspose(kernel, outPixels, inPixels, height, width, alpha, CLAMP_EDGES);
		}

        src.getRGB( outPixels );

		float a = 4*amount;

		int index = 0;
		for ( int y = 0; y < height; y++ ) {
			for ( int x = 0; x < width; x++ ) {
				int rgb1 = outPixels[index];
				int r1 = (rgb1 >> 16) & 0xff;
				int g1 = (rgb1 >> 8) & 0xff;
				int b1 = rgb1 & 0xff;

				int rgb2 = inPixels[index];
				int r2 = (rgb2 >> 16) & 0xff;
				int g2 = (rgb2 >> 8) & 0xff;
				int b2 = rgb2 & 0xff;

				r1 = PixelUtils.clamp( (int)(r1 + a * r2) );
				g1 = PixelUtils.clamp( (int)(g1 + a * g2) );
				b1 = PixelUtils.clamp( (int)(b1 + a * b2) );

				inPixels[index] = (rgb1 & 0xff000000) | (r1 << 16) | (g1 << 8) | b1;
				index++;
			}
		}

        dst.setRGB( inPixels);
    }

	public String toString() {
		return "Blur/Glow...";
	}
}