package com.tobee.image.proc.incubator;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PPMImage {
	private byte M, N; // 매직 넘버
	private final int width;
	private final int height;
	private int max;
	private Pixel imgPixel[];
	
	public PPMImage(final int height, final int width)
	{
		this.width = width;
		this.height = height;
		M = 'P';
		N = '6';
		max = 255;
	}
	
	public byte getM(){ return M; }
	public byte getN(){ return N; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	
	public int getMax(){ return max; }
	public Pixel[] getPixel(){ return imgPixel; }
	
	public void setPixel(final Pixel[] pixs){ 
		//imgPixel = pixs;
		
		imgPixel = new Pixel[pixs.length];
		
		System.arraycopy(pixs, 0, imgPixel, 0, imgPixel.length);
	}
	
	public void setRGB( int[] pixels ) {
		int height = getHeight();
		int width = getWidth();
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
		
		setPixel(raw_pixels);
    }
	
	public int[] getRGB( int[] pixels ) {
		int height = getHeight();
		
		Pixel[] raw_pixels = getPixel();
		
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
	
	
	public static PPMImage read(final String fileNm)
	{
		RandomAccessFile fp = null;
		File file = new File(fileNm);
		int width=0,height=0,max=0;
		PPMImage image = null;
		
		if (!file.exists()) {
			System.err.println("fnReadPPM file doesn't exist\n");
			return null;
		}

		try {
			fp = new RandomAccessFile(file, "r"); // binary mode
			// read magic number
			byte M = fp.readByte();
			byte N = fp.readByte();

			if (M != 'P' || N != '6') {
				System.err.printf("PPM 이미지 포멧이 아닙니다 : %c%c\n", M, N);
				if (fp != null)
					try {
						fp.close();
					} catch (IOException e) {
					}
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("Exception occurred while creating file: %s\n", fileNm);
			return null;
		}
		
		// read width height, image intensity
		try {
			// skip carrage return
			fp.readLine();
			String wh[] = fp.readLine().split(" ");
			width = Integer.valueOf(wh[0]);
			height = Integer.valueOf(wh[1]);
			max = Integer.valueOf(fp.readLine());

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		image = new PPMImage(height,width);
		
		if (max != 255) {
			System.err.println("Not valid image intensity.\n");
			if (fp != null)
				try {
					fp.close();
				} catch (IOException e) {
				}
			return null;
		}
		
		image.imgPixel = new Pixel[height];
		
		try {
			// <-- ppm 파일로부터 픽셀값을 읽어서 할당한 메모리에 load
			// 1개의 픽셀을 위해 R, G, B 3byte가 필요
			byte[] rgb = new byte[width*3];
			
			for(int i=0; i<height; i++){
				image.imgPixel[i] = new Pixel(width);
				fp.read(rgb);
				
				image.imgPixel[i].setPixelAll(rgb);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fp != null)
				try {
					fp.close();
				} catch (IOException e) {
				}
			fp = null;
		}
		return image;
	}
	
	public static boolean writeToFile(final String fileNm, final PPMImage pgmImg)
	{
		RandomAccessFile fp = null;

		File file = new File(fileNm);

		try {
			fp = new RandomAccessFile(file, "rw"); // binary mode
			fp.writeByte(pgmImg.M);
			fp.writeByte(pgmImg.N);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("Exception occurred while creating file: %s\n", fileNm);
			return false;
		}

		try {
			fp.write('\n');
			fp.writeBytes(pgmImg.width + " " + pgmImg.height);
			fp.write('\n');
			fp.writeBytes(String.valueOf(pgmImg.max));
			fp.write('\n');
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			RGB [] rgb;
			
			for (int i = 0; i < pgmImg.height; i++) {
				rgb = pgmImg.imgPixel[i].getRGBPixel();
				
				for(int j  = 0; j < rgb.length; j++)
				{
					if(rgb[j] == null)
						fp.write(new byte[]{0,0,0});
					else
						fp.write(rgb[j].getBytes());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fp != null)
				try {
					fp.close();
				} catch (IOException e) {
				}
			fp = null;
		}
		
		return true;
	}
}
