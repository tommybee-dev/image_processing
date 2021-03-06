package com.tobee.image.proc.incubator.filters;

import com.tobee.image.proc.ImageMath;
import com.tobee.image.proc.incubator.PPMImage;

public class BoxBlurFilter extends Filter {

	private int hRadius;
	private int vRadius;
	private int iterations = 1;
	
    public void filter( final PPMImage src, final PPMImage dst ) {
        int width = src.getWidth();
        int height = src.getHeight();
        

        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        
        src.getRGB( inPixels );
        
        for (int i = 0; i < iterations; i++ ) {
            blur( inPixels, outPixels, width, height, hRadius );
            blur( outPixels, inPixels, height, width, vRadius );
        }
        
        dst.setRGB( inPixels );
        
    }
    
    public void filter( final PPMImage src, final PPMImage dst, final int iterCnt ) {
        int width = src.getWidth();
        int height = src.getHeight();
        

        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        
        src.getRGB( inPixels );
        
        for (int i = 0; i < iterCnt; i++ ) {
            blur( inPixels, outPixels, width, height, hRadius );
            blur( outPixels, inPixels, height, width, vRadius );
        }
        
        dst.setRGB( inPixels );
        
    }

    public static void blur( int[] in, int[] out, int width, int height, int radius ) {
        int widthMinus1 = width-1;
        int tableSize = 2*radius+1;
        int divide[] = new int[256*tableSize];

        for ( int i = 0; i < 256*tableSize; i++ )
            divide[i] = i/tableSize;

        int inIndex = 0;
        
        for ( int y = 0; y < height; y++ ) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;

            for ( int i = -radius; i <= radius; i++ ) {
                int rgb = in[inIndex + ImageMath.clamp(i, 0, width-1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }
            
            for ( int x = 0; x < width; x++ ) {
                out[ outIndex ] = (divide[ta] << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];
                
                int i1 = x+radius+1;
                if ( i1 > widthMinus1 )
                    i1 = widthMinus1;
                int i2 = x-radius;
                if ( i2 < 0 )
                    i2 = 0;
                int rgb1 = in[inIndex+i1];
                int rgb2 = in[inIndex+i2];
                
                ta += ((rgb1 >> 24) & 0xff)-((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000)-(rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00)-(rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff)-(rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }
        
	public void setHRadius(int hRadius) {
		this.hRadius = hRadius;
	}
	
	public int getHRadius() {
		return hRadius;
	}
	
	public void setVRadius(int vRadius) {
		this.vRadius = vRadius;
	}
	
	public int getVRadius() {
		return vRadius;
	}
	
	public void setRadius(int radius) {
		this.hRadius = this.vRadius = radius;
	}
	
	public int getRadius() {
		return hRadius;
	}
	
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	
	public int getIterations() {
		return iterations;
	}
	
	public String toString() {
		return "Blur/Box Blur...";
	}
}