package com.tobee.tests;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ColorImageProcessingTest {
	
	static class PPMImage {
		byte M, N; // 매직 넘버
		int width;
		int height;
		int max;
		byte pixels[][];
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
		
		pgmImg.pixels = new byte[pgmImg.height][];
		
		for(int i=0; i<pgmImg.height; i++){
		   // 1개의 픽셀을 위해 R, G, B 3byte가 필요
			pgmImg.pixels[i] = new byte[pgmImg.width * 3];
		}
		
		
		try {
			// <-- ppm 파일로부터 픽셀값을 읽어서 할당한 메모리에 load
			for(int i=0; i<pgmImg.height; i++){
				for(int j=0; j<pgmImg.width * 3; j++){
					pgmImg.pixels[i][j] = fp.readByte();
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
	    int new_x;


	    // 헤더값 복사
	    img_mir.M      = img_org.M;
	    img_mir.N      = img_org.N;
	    img_mir.width  = img_org.width;
	    img_mir.height = img_org.height;
	    img_mir.max    = img_org.max;


	    // <-- 메모리 할당 - 원본이미지와 동일한 크기로
	    img_mir.pixels = new byte[img_org.height][];

	    for(int i=0; i<img_org.height; i++){
	        img_mir.pixels[i] = new byte[img_org.width * 3];
	    }
	    
	    // <-- 미러(좌우반전) 효과
	    for(int i=0; i<img_org.height; i++){
	        for(int j=0; j<img_org.width * 3; j+=3){
	            // 배열은 0부터 시작이므로 1px의 크기(3 byte)만큼 빼줌
	            new_x = (img_org.width * 3) - j - 3;   

	            img_mir.pixels[i][new_x]     = img_org.pixels[i][j];
	            img_mir.pixels[i][new_x + 1] = img_org.pixels[i][j + 1];
	            img_mir.pixels[i][new_x + 2] = img_org.pixels[i][j + 2];
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
	    
	    final int r = 0;
	    final int g = 1;
	    final int b = 2;

	    // 헤더값 복사
	    img_new.M      = img_org.M;
	    img_new.N      = img_org.N;
	    img_new.width  = new_width;
	    img_new.height = new_height;
	    img_new.max    = img_org.max;


	 // <-- 메모리 할당 - 원본이미지와 동일한 크기로
	    img_new.pixels = new byte[img_new.height][];

	    for(int i=0; i<img_new.height; i++){
	    	img_new.pixels[i] = new byte[img_new.width * 3];
	    }
	    
	 // <-- 배경색으로 초기화
	    for(int i=0; i<img_new.height; i++){
	        for(int j=0; j<img_new.width * 3; j+=3){
	            img_new.pixels[i][r] = 0;
	            img_new.pixels[i][g] = 0;
	            img_new.pixels[i][b] = 0;
	        }
	    }


	    
	    // <-- 리사이즈(확대, 축소)
	    for(int i=0; i<img_org.height; i++){
	        new_y = (int) Math.ceil(i * n);


	        if(new_y >= img_new.height){
	            new_y = img_new.height - 1;
	        }

	        for(int j=0; j < img_org.width * 3; j+=3){
	            new_x = (int) Math.ceil(j * n) ;

	            if(new_x >= img_new.width * 3){
	                new_x = img_new.width * 3 - 3;
	                
	                img_new.pixels[new_y][new_x + r] = img_org.pixels[i][j + r];
		            img_new.pixels[new_y][new_x + g] = img_org.pixels[i][j + g];
		            img_new.pixels[new_y][new_x + b] = img_org.pixels[i][j + b];
	            }            
	            else if(new_x < img_new.width * 3)
	            {
	            	//System.out.println(i + " : " + j + "=" + new_y + ":" + new_x);
	            	new_x = new_x-3;
	            	
	            	if(new_x > 2)
		            {
	            		
		            	img_new.pixels[new_y][new_x + r] = img_org.pixels[i][j + r];
			            img_new.pixels[new_y][new_x + g] = img_org.pixels[i][j + g];
			            img_new.pixels[new_y][new_x + b] = img_org.pixels[i][j + b];
		            }
		            else
		            {
		            	System.out.println(i + " : " + j + "=" + new_y + ":" + new_x);
		            	if(new_x < 0)
		            	{
		            		new_x = 0;
		            	}
		            	
		            	img_new.pixels[new_y][new_x + r] = img_org.pixels[i][j + r];
			            img_new.pixels[new_y][new_x + g] = img_org.pixels[i][j + g];
			            img_new.pixels[new_y][new_x + b] = img_org.pixels[i][j + b];
		            }
	            }
	        }
	    }
	    // -->


	    return true;
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
			int tmp;

			for (int i = 0; i < pgmImg.height; i++) {
				for (int j = 0; j < pgmImg.width * 3; j+=3) {
					tmp = (pgmImg.pixels[i][j] & 0xFF);
					tmp = tmp + rb;
					// prevent underflow
					if (tmp < 0)tmp = 0;
					// prevent overflow
					if (tmp > 255)	tmp = 255;
					fp.writeByte(tmp);
					
					tmp = (pgmImg.pixels[i][j+1] & 0xFF);
					tmp = tmp + gb;
					// prevent underflow
					if (tmp < 0)tmp = 0;
					// prevent overflow
					if (tmp > 255)	tmp = 255;
					fp.writeByte(tmp);
					
					tmp = (pgmImg.pixels[i][j+2] & 0xFF);
					tmp = tmp + bb;
					// prevent underflow
					if (tmp < 0)tmp = 0;
					// prevent overflow
					if (tmp > 255)tmp = 255;
					
					fp.writeByte(tmp);
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


	
	public static void main(String[] args) {
		final String orginfile = "C:/DEV/DOCs/ImageProcessing/강의/BMW-Motorrad-K1600GTL.ppm";
		final String fileNm = "C:/DEV/DOCs/ImageProcessing/강의/Output/BMW-Motorrad-K1600GTL.size.ppm";
		
		ColorImageProcessingTest imgtest = new ColorImageProcessingTest();
		PPMImage pgmImg = new PPMImage();
		PPMImage img_sz = new PPMImage();
		
		imgtest.fnReadPPM(orginfile, pgmImg);
		//imgtest.fnMirrorImg(pgmImg, img_sz);
		imgtest.fnResizeImg(pgmImg, img_sz, 0.5);
		imgtest.fnWritePPM(fileNm, img_sz, 0);
	}

	// C:\DEV\DOCs\ImageProcessing\강의
	public static void old_main_placeholder(String[] args) {
		final String orginfile = "C:/DEV/DOCs/ImageProcessing/강의/car_sample.ppm";
		final String fileNm = "C:/DEV/DOCs/ImageProcessing/강의/Output/car_sample.out.ppm";
		
		ColorImageProcessingTest imgtest = new ColorImageProcessingTest();
		PPMImage pgmImg = new PPMImage();
		
		int rb = 50;
		int gb = 50;
		int bb = 50;
		
		imgtest.fnReadPPM(orginfile, pgmImg);
		imgtest.fnWritePPM(fileNm, pgmImg, rb, gb, bb);
	}
}
