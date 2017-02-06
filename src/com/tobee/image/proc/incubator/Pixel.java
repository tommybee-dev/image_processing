package com.tobee.image.proc.incubator;

import java.nio.ByteBuffer;

public class Pixel
{
	final int width;
	RGB pixs[];
	byte[] pixel;
	int cnt;
	int pixelCnt;
	
	public Pixel(final int width)
	{
		this.width = width;
		pixs = new RGB[width];
		pixel = new byte[width*3];
		pixelCnt = cnt = 0;
	}
	
	void setPixelAll(final byte[] rgb)
	{
		pixelCnt = rgb.length;
		
		for(int i = 0; i <pixelCnt; i += 3)
		{
			if(i == this.width * 3) break;
			pixs[cnt] = new RGB(rgb[i], rgb[i+1], rgb[i+2]);
			
			cnt++;
		}
	}
	
	byte[] getAllPixel()
	{
		return pixel;
	}
	
	byte[] getPixelAll()
	{
		//byte pixel[] = new byte[cnt];
		RGB rgb[] = getRGBPixel();
		
		//System.out.println("...." + rgb.length);
		
		ByteBuffer bbuf = ByteBuffer.allocate(pixelCnt);
		
		for(int i = 0; i <rgb.length; i++)
		{
			//System.out.println("...." + i);
			bbuf.put(rgb[i].getBytes());
		}
		
		bbuf.flip();
		
		return bbuf.array();
	}
	
	public int[] getRGBValue()
	{
		RGB rgb[] = getRGBPixel();
		int[] pixels = new int[rgb.length];
		//System.out.println("...." + rgb.length);
		
		for(int i = 0; i <rgb.length; i++)
			pixels[i] = rgb[i].getRGBInt();
		
		
		return pixels;
	}
	
	RGB[] getRGBPixel()
	{
		return pixs;
	}
	
	public void setRGBValue(final int rgbVals[])
	{
		
		for(int i = 0; i <pixs.length; i++)
		{
			int rgb = rgbVals[i];

			int r = (rgb >> 16) & 0xff;
			int g = (rgb >> 8) & 0xff;
			int b = rgb & 0xff;
			
			pixs[i] = new RGB(r,g,b);
		}
	}
	
	void setARGBValue(final int rgbVals[])
	{
		
		for(int i = 0; i <pixs.length; i++)
		{
			int rgb = rgbVals[i];
			int a = (rgb >> 24) & 0xff;
			int r = (rgb >> 16) & 0xff;
			int g = (rgb >> 8) & 0xff;
			int b = rgb & 0xff;
			
			pixs[i] = new RGB(r,g,b);
		}
	}
	
	byte[] getReversePixel()
	{
		//byte pixel[] = new byte[cnt];
		ByteBuffer bbuf = ByteBuffer.allocate(pixelCnt);
		RGB rgb[] = reverseRGBPixel();
		
		for(int i = 0; i <rgb.length; i++)
		{
			bbuf.put(rgb[i].getBytes());
		}
		
		bbuf.flip();
		
		return bbuf.array();
	}
	
	
	
	void setRGBPixel(final RGB[] rgb)
	{
		//ByteBuffer bbuf = ByteBuffer.allocate(pixelCnt);
		//for(int i = 0; i <rgb.length; i++)
		//{
		//	bbuf.put(rgb[i].getBytes());
		//}
		//bbuf.flip();
		System.arraycopy(pixs, 0, rgb, 0, rgb.length);
	}
	
	RGB[] reverseRGBPixel()
	{
		int idx = 0;
		RGB rpixs[] = new RGB[width];
		
		for(int i = cnt-1; i >= 0; --i)
		{
			rpixs[i] = pixs[idx];
			idx++;
		}
		
		return rpixs;
	}
}
