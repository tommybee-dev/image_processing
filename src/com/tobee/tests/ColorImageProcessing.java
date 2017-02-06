package com.tobee.tests;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import com.tobee.tests.ImageProcessingTest.PGMImage;

public class ColorImageProcessing {
	
	static class RGB
	{
		int RED;
		int GREEN;
		int BLUE;
		
		RGB(byte r, byte g, byte b)
		{
			RED = (r & 0xFF);
			GREEN = (g & 0xFF);
			BLUE = (b & 0xFF);
		}
		
		int red(){ return RED;}
		int green(){ return GREEN;}
		int blue(){ return BLUE;}
		
		byte[] getBytes()
		{
			byte[] bb = {(byte)RED,(byte)GREEN,(byte)BLUE};
			return bb;
		}
		
		@Override
		public String toString()
		{
			return RED + "/" + GREEN + "/" + BLUE;
		}
	}
	
	static class Pixel
	{
		final int width;
		RGB pixs[];
		byte[] pixel;
		int cnt;
		int pixelCnt;
		
		Pixel(final int width)
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
		
		byte[] getPixel()
		{
			return pixel;
		}
		
		byte[] getPixelAll()
		{
			//byte pixel[] = new byte[cnt];
			RGB rgb[] = getRGBPixel();
			
			System.out.println("...." + rgb.length);
			
			ByteBuffer bbuf = ByteBuffer.allocate(pixelCnt);
			
			for(int i = 0; i <rgb.length; i++)
			{
				//System.out.println("...." + i);
				bbuf.put(rgb[i].getBytes());
			}
			
			bbuf.flip();
			
			return bbuf.array();
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
		
		RGB[] getRGBPixel()
		{
			return pixs;
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
	
	static class PPMImage {
		byte M, N; // 매직 넘버
		int width;
		int height;
		int max;
		Pixel imgPixel[];
	};
	
	
	boolean fnReadPPM(final String fileNm, final PPMImage pgmImg)
	{
		RandomAccessFile fp = null;
		File file = new File(fileNm);

		if (!file.exists()) {
			System.err.println("fnReadPPM file doesn't exist\n");
			return false;
		}

		try {
			fp = new RandomAccessFile(file, "r"); // binary mode
			// read magic number
			pgmImg.M = fp.readByte();
			pgmImg.N = fp.readByte();

			if (pgmImg.M != 'P' || pgmImg.N != '6') {
				System.err.printf("PPM 이미지 포멧이 아닙니다 : %c%c\n", pgmImg.M, pgmImg.N);
				if (fp != null)
					try {
						fp.close();
					} catch (IOException e) {
					}
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("Exception occurred while creating file: %s\n", fileNm);
			return false;
		}
		
		// read width height, image intensity
		try {
			// skip carrage return
			fp.readLine();
			String wh[] = fp.readLine().split(" ");
			pgmImg.width = Integer.valueOf(wh[0]);
			pgmImg.height = Integer.valueOf(wh[1]);
			pgmImg.max = Integer.valueOf(fp.readLine());

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (pgmImg.max != 255) {
			System.err.println("Not valid image intensity.\n");
			if (fp != null)
				try {
					fp.close();
				} catch (IOException e) {
				}
			return false;
		}
		
		pgmImg.imgPixel = new Pixel[pgmImg.height];
		
		try {
			// <-- ppm 파일로부터 픽셀값을 읽어서 할당한 메모리에 load
			// 1개의 픽셀을 위해 R, G, B 3byte가 필요
			byte[] rgb = new byte[pgmImg.width*3];
			
			for(int i=0; i<pgmImg.height; i++){
				pgmImg.imgPixel[i] = new Pixel(pgmImg.width);
				fp.read(rgb);
				
				//pgmImg.imgPixel[i].pixs = new RGB[pgmImg.width];
				pgmImg.imgPixel[i].setPixelAll(rgb);
				
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
	
	private void testPixel(final PPMImage ppmImg)
	{
		RGB []_rgb = null;
		for(int i=0; i<ppmImg.height; i++){
			_rgb = ppmImg.imgPixel[i].getRGBPixel();
			
			//System.out.println("1qq--" + _rgb.length);
			
			for(int j  = 0; j < _rgb.length; j++)
			{
				System.out.println("2qq--" + _rgb[j]);
				//if(j == 2)
				//	System.out.println("2qq--" + _rgb[j]);
			}
			//System.out.println("--" + rgb[0] + ":" + rgb[1] + ":" + rgb[2]);
			
		}
	}
	
	boolean fnWritePPM(final String fileNm, final PPMImage pgmImg, final int brightness)
	{
		return fnWritePPM(fileNm, pgmImg, brightness, brightness, brightness);
	}
	
	boolean fnWritePPM(final String fileNm, final PPMImage pgmImg, final int rb, final int gb, final int bb)
	{
		RandomAccessFile fp = null;

		File file = new File(fileNm);

		try {
			fp = new RandomAccessFile(file, "rw"); // binary mode
			// write magic number
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
			//int tmp;
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
	
	boolean fnMirrorImg(final PPMImage img_org, final PPMImage img_mir)
	{
		// 헤더값 복사
	    img_mir.M      = img_org.M;
	    img_mir.N      = img_org.N;
	    img_mir.width  = img_org.width;
	    img_mir.height = img_org.height;
	    img_mir.max    = img_org.max;

	    // <-- 메모리 할당 - 원본이미지와 동일한 크기로
	    img_mir.imgPixel = new Pixel[img_org.height];

	    // <-- 미러(좌우반전) 효과
	    for(int i=0; i<img_org.height; i++){
	    	img_mir.imgPixel[i] = new Pixel(img_org.width);
	    	
	    	img_mir.imgPixel[i].setPixelAll(img_org.imgPixel[i].getReversePixel());
	    	
	    }
	    // -->

	    return true;
	}
	
	boolean fnMoveImg(final PPMImage img_org, final PPMImage img_mov, int xd, int yd) {
		int new_x;
		int new_y;

		// 헤더값 복사
		img_mov.M = img_org.M;
		img_mov.N = img_org.N;
		img_mov.width = img_org.width;
		img_mov.height = img_org.height;
		img_mov.max = img_org.max;

		// <-- 메모리 할당 - 원본이미지와 동일한 크기로
		img_mov.imgPixel = new Pixel[img_mov.height];
	    System.out.printf("%d/%d", img_mov.height, img_mov.width);
	 // <-- 배경색으로 초기화
	    for(int i=0; i<img_mov.height; i++){
	    	img_mov.imgPixel[i] = new Pixel(img_mov.width);
	    	img_mov.imgPixel[i].setPixelAll(new byte[]{0,0,0});
	    }
		// -->

	    RGB[] wPixel = null;
	    RGB[] cPixel = null;
		// <-- xd(가로), yd(세로) 만큼 좌표를 이동시킴
		for (int i = 0; i < img_org.height; i++) {
			new_y = i + yd;

			// y - 유효한 좌표값인지 체크
			if (new_y < 0 || new_y >= img_org.height) {
				continue;
			}
			
			wPixel = img_org.imgPixel[i].getRGBPixel();
	        cPixel = img_mov.imgPixel[new_y].getRGBPixel();
			
			for (int j = 0; j < img_org.width; j++) {
				new_x = j + xd;

				// x - 유효한 좌표값인지 체크
				if (new_x < 0 || new_x >= img_org.width) {
					continue;
				}
				
				cPixel[new_x] = wPixel[j] ;
			}
		}
		// -->

		return true;
	}
	
	boolean fnResizeImg(final PPMImage img_org, final PPMImage img_new, final double n)
	{
	    int new_height = (int) (img_org.height * n);
	    int new_width  = (int) (img_org.width  * n);
	    int new_x;
	    int new_y;

	    // 헤더값 복사
	    img_new.M      = img_org.M;
	    img_new.N      = img_org.N;
	    img_new.width  = new_width;
	    img_new.height = new_height;
	    img_new.max    = img_org.max;

	 // <-- 메모리 할당 - 원본이미지와 동일한 크기로
	    img_new.imgPixel = new Pixel[img_new.height];
	    System.out.printf("%d/%d", img_new.height, img_new.width);
	 // <-- 배경색으로 초기화
	    for(int i=0; i<img_new.height; i++){
	    	img_new.imgPixel[i] = new Pixel(img_new.width);
	    	img_new.imgPixel[i].setPixelAll(new byte[]{0,0,0});
	    }

	    RGB[] wPixel = null;
	    RGB[] cPixel = null;
	    // <-- 리사이즈(확대, 축소)
	    for(int i=0; i<img_org.height; i++){
	        new_y = (int) Math.ceil(i * n);

	        if(new_y >= img_new.height){
	            new_y = img_new.height - 1;
	        }
	        
	        //img_new.imgPixel[new_y] = img_org.imgPixel[i];
	        
	        wPixel = img_org.imgPixel[i].getRGBPixel();
	        cPixel = img_new.imgPixel[new_y].getRGBPixel();
	        
	        //System.out.printf("%d/%d", wPixel.length, cPixel.length);
	        
	        for(int j=0; j < wPixel.length; j++)
	        {
	        	new_x = (int) Math.ceil(j * n) ;
	        	
	        	if(new_x >= img_new.width){
	                new_x = img_new.width - 1;
	            }

	        	//System.out.printf("%d:::::%d\n", new_x, j);
	        	
	        	cPixel[new_x] = wPixel[j] ;
	        }
	        img_new.imgPixel[new_y].setRGBPixel(cPixel);
	    }
	    // -->
	    return true;
	}
	
	void fnGussianBlur(final PPMImage img_org, final PPMImage img_new, final PPMImage edgeDir)
	{
		int W, H;		// Width and Height of current frame [pixels]
		int row, col;		// Pixel's row and col positions
		int i;		// Dummy variable for row-column vector
		int upperThreshold = 60;	// Gradient strength nessicary to start edge
		int lowerThreshold = 30;	// Minimum gradient strength to continue edge
		long iOffset;		// Variable to offset row-column vector during sobel mask
		int rowOffset;			// Row offset from the current pixel
		int colOffset;			// Col offset from the current pixel
		int rowTotal = 0;		// Row position of offset pixel
		int colTotal = 0;		// Col position of offset pixel
		int Gx;				// Sum of Sobel mask products values in the x direction
		int Gy;				// Sum of Sobel mask products values in the y direction
		float thisAngle;		// Gradient direction based on Gx and Gy
		int newAngle;			// Approximation of the gradient direction
		boolean edgeEnd;			// Stores whether or not the edge is at the edge of the possible image
		int GxMask[][] = new int[3][3];		// Sobel mask in the x direction
		int GyMask[][] = new int[3][3];		// Sobel mask in the y direction
		int newPixel;			// Sum pixel values for gaussian
		int gaussianMask[][] = new int[5][5];		// Gaussian mask

		W = img_org.width;  // biWidth: number of columns
	   	H = img_org.height; // biHeight: number of rows
		
		//for (row = 0; row < H; row++) {
		//	for (col = 0; col < W; col++) {
		//		edgeDir[row][col] = 0;
		//	}
		//}
		
	   	for(i=0; i<img_new.height; i++){
	   		edgeDir.imgPixel[i] = new Pixel(img_new.width);
	   		edgeDir.imgPixel[i].setPixelAll(new byte[]{0,0,0});
	    }
	   	
		for(i=0; i<img_new.height; i++){
	    	img_new.imgPixel[i] = new Pixel(img_new.width);
	    	img_new.imgPixel[i].setPixelAll(new byte[]{0,0,0});
	    }
		
		/* Declare Sobel masks */
		GxMask[0][0] = -1; GxMask[0][1] = 0; GxMask[0][2] = 1;
		GxMask[1][0] = -2; GxMask[1][1] = 0; GxMask[1][2] = 2;
		GxMask[2][0] = -1; GxMask[2][1] = 0; GxMask[2][2] = 1;
		
		GyMask[0][0] =  1; GyMask[0][1] =  2; GyMask[0][2] =  1;
		GyMask[1][0] =  0; GyMask[1][1] =  0; GyMask[1][2] =  0;
		GyMask[2][0] = -1; GyMask[2][1] = -2; GyMask[2][2] = -1;

		/* Declare Gaussian mask */
		gaussianMask[0][0] = 2;		gaussianMask[0][1] = 4;		gaussianMask[0][2] = 5;		gaussianMask[0][3] = 4;		gaussianMask[0][4] = 2;	
		gaussianMask[1][0] = 4;		gaussianMask[1][1] = 9;		gaussianMask[1][2] = 12;	gaussianMask[1][3] = 9;		gaussianMask[1][4] = 4;	
		gaussianMask[2][0] = 5;		gaussianMask[2][1] = 12;	gaussianMask[2][2] = 15;	gaussianMask[2][3] = 12;	gaussianMask[2][4] = 2;	
		gaussianMask[3][0] = 4;		gaussianMask[3][1] = 9;		gaussianMask[3][2] = 12;	gaussianMask[3][3] = 9;		gaussianMask[3][4] = 4;	
		gaussianMask[4][0] = 2;		gaussianMask[4][1] = 4;		gaussianMask[4][2] = 5;		gaussianMask[4][3] = 4;		gaussianMask[4][4] = 2;	
		

		/* Gaussian Blur */
		RGB[] wPixel = null;
		
		for (row = 2; row < H-2; row++) {
			wPixel = img_org.imgPixel[row].getRGBPixel();

			for (col = 2; col < W-2; col++) {
				newPixel = 0;
				for (rowOffset=-2; rowOffset<=2; rowOffset++) {
					for (colOffset=-2; colOffset<=2; colOffset++) {
						rowTotal = row + rowOffset;
						colTotal = col + colOffset;
						iOffset = (long)(rowTotal*3*W + colTotal*3);
						//newPixel += (*(m_destinationBmp + iOffset)) * gaussianMask[2 + rowOffset][2 + colOffset];
						newPixel += wPixel[(int)iOffset].BLUE * gaussianMask[2 + rowOffset][2 + colOffset];
					}
				}
				i = (int)(row*3*W + col*3);
				//*(m_destinationBmp + i) = newPixel / 159;
				int val = (int)(newPixel / 159);
				wPixel[(int)i].BLUE = val;
				wPixel[(int)i].RED = val;
				wPixel[(int)i].GREEN = val;
				
				img_new.imgPixel[(int)i].setRGBPixel(wPixel);
			}
		}
	}
	
	public static void main(String[] args) {
		final String orginfile = "C:/DEV/DOCs/ImageProcessing/강의/BMW-Motorrad-K1600GTL.ppm";
		final String copyfile = "C:/DEV/DOCs/ImageProcessing/강의/Output/BMW-Motorrad-K1600GTL.copy.ppm";
		final String resizefile = "C:/DEV/DOCs/ImageProcessing/강의/Output/BMW-Motorrad-K1600GTL.resize.ppm";
		final String fileNm = "C:/DEV/DOCs/ImageProcessing/강의/Output/BMW-Motorrad-K1600GTL.mirror.ppm";
		final String mvfile = "C:/DEV/DOCs/ImageProcessing/강의/Output/BMW-Motorrad-K1600GTL.move.ppm";
		
		ColorImageProcessing imgtest = new ColorImageProcessing();
		PPMImage pgmImg = new PPMImage();
		PPMImage img_sz = new PPMImage();
		PPMImage edgeDir = new PPMImage();
		
		imgtest.fnReadPPM(orginfile, pgmImg);
		//imgtest.fnWritePPM(copyfile, pgmImg, 0);
		//imgtest.fnMirrorImg(pgmImg, img_sz);
		//imgtest.fnResizeImg(pgmImg, img_sz, 0.5);
		//imgtest.fnMoveImg(pgmImg, img_sz, 100,150);
		imgtest.fnGussianBlur(pgmImg, img_sz, edgeDir);
		imgtest.fnWritePPM(mvfile, img_sz, 0);
		
	}

	// C:\DEV\DOCs\ImageProcessing\강의
	public static void old_main_placeholder(String[] args) {
		final String orginfile = "C:/DEV/DOCs/ImageProcessing/강의/car_sample.ppm";
		final String fileNm = "C:/DEV/DOCs/ImageProcessing/강의/Output/car_sample.out.ppm";
		
		ColorImageProcessing imgtest = new ColorImageProcessing();
		PPMImage pgmImg = new PPMImage();
		
		int rb = 50;
		int gb = 50;
		int bb = 50;
		
		//imgtest.fnReadPPM(orginfile, pgmImg);
		//imgtest.fnWritePPM(fileNm, pgmImg, rb, gb, bb);
	}
}
