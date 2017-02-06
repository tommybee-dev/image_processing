package com.tobee.tests;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ImageProcessingTest {

	private static final int DEFAULT_BRIGHTNESS = 0;

	static class PGMImage {
		byte M, N; // 매직 넘버
		int width;
		int height;
		int max;
		byte pixels[][];
	};
	
	static class PPMImage {
		byte M, N; // 매직 넘버
		int width;
		int height;
		int max;
		byte pixels[][];
	};

	boolean fnWritePGM(final String fileNm, final PGMImage pgmImg) {

		return fnWritePGM(fileNm, pgmImg, DEFAULT_BRIGHTNESS);
	}

	boolean fnWriteContPGM(final String fileNm, final PGMImage pgmImg, final int limit, final double contrast) {
		RandomAccessFile fp = null;

		File file = new File(fileNm);

		try {
			fp = new RandomAccessFile(file, "rw"); // binary mode
			// write magic number
			fp.writeByte(pgmImg.M);
			fp.writeByte(pgmImg.N);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("Exception occurred while creating random access file: %s\n", fileNm);
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
			int pixval;

			for (int i = 0; i < pgmImg.height; i++) {
				for (int j = 0; j < pgmImg.width; j++) {
					tmp = pixval = (pgmImg.pixels[i][j] & 0xFF);

					// 밝은곳은 더 밝게, 어두운 곳은 더 어둡게
					if (tmp > limit) {
						tmp = (int) ((pixval - limit) * contrast);
						tmp = pixval + tmp;
					} else {
						tmp = (int) ((limit - pixval) * contrast);
						tmp = pixval - tmp;
					}

					// prevent underflow
					if (tmp < 0)
						tmp = 0;
					// prevent overflow
					if (tmp > 255)
						tmp = 255;

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

	boolean fnWriteInvPGM(final String fileNm, final PGMImage pgmImg, final int brightness) {
		RandomAccessFile fp = null;

		File file = new File(fileNm);

		try {
			fp = new RandomAccessFile(file, "rw"); // binary mode
			// write magic number
			fp.writeByte(pgmImg.M);
			fp.writeByte(pgmImg.N);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("Exception occurred while creating random access file: %s\n", fileNm);
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
				for (int j = 0; j < pgmImg.width; j++) {
					tmp = brightness - (pgmImg.pixels[i][j] & 0xFF);

					// prevent underflow
					if (tmp < 0)
						tmp = 0;
					// prevent overflow
					if (tmp > 255)
						tmp = 255;

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

	boolean fnWritePGM(final String fileNm, final PGMImage pgmImg, final int brightness) {
		RandomAccessFile fp = null;

		File file = new File(fileNm);

		try {
			fp = new RandomAccessFile(file, "rw"); // binary mode
			// write magic number
			fp.writeByte(pgmImg.M);
			fp.writeByte(pgmImg.N);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("Exception occurred while creating random access file: %s\n", fileNm);
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
				for (int j = 0; j < pgmImg.width; j++) {
					tmp = (pgmImg.pixels[i][j] & 0xFF);
					tmp = tmp + brightness;

					// prevent underflow
					if (tmp < 0)
						tmp = 0;
					// prevent overflow
					if (tmp > 255)
						tmp = 255;

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

	boolean fnWritePGM_Error(final String fileNm, final PGMImage pgmImg, final int brightness) {
		RandomAccessFile fp = null;

		File file = new File(fileNm);

		try {
			fp = new RandomAccessFile(file, "rw"); // binary mode
			// write magic number
			fp.writeByte(pgmImg.M);
			fp.writeByte(pgmImg.N);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("Exception occurred while creating random access file: %s\n", fileNm);
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
				for (int j = 0; j < pgmImg.width; j++) {
					tmp = pgmImg.pixels[i][j] + brightness;

					if (tmp > 255) {
						fp.writeByte(255);
					} else
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

	boolean fnReadPGM(final String fileNm, PGMImage pgmImg) {
		RandomAccessFile fp = null;
		File file = new File(fileNm);

		if (!file.exists()) {
			System.err.println("fnReadPGM file doesn't exist\n");
			return false;
		}

		try {
			fp = new RandomAccessFile(file, "r"); // binary mode
			// read magic number
			pgmImg.M = fp.readByte();
			pgmImg.N = fp.readByte();

			if (pgmImg.M != 'P' || pgmImg.N != '5') {
				System.err.printf("Not a PGM header : %c%c\n", pgmImg.M, pgmImg.N);
				if (fp != null)
					try {
						fp.close();
					} catch (IOException e) {
					}
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("Exception occurred while creating random access file: %s\n", fileNm);
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

		// memory allocation of the image

		pgmImg.pixels = new byte[pgmImg.height][pgmImg.width];

		// for(int i=0; i<pgmImg.height; i++){
		// pgmImg.pixels[i] = new byte[pgmImg.width];
		// }
		try {
			// <-- load pixel values of the image
			for (int i = 0; i < pgmImg.height; i++) {
				for (int j = 0; j < pgmImg.width; j++) {
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

	boolean fnMoveImg(final PGMImage img_org, final PGMImage img_mov, int xd, int yd) {
		int new_x;
		int new_y;

		// 헤더값 복사
		img_mov.M = img_org.M;
		img_mov.N = img_org.N;
		img_mov.width = img_org.width;
		img_mov.height = img_org.height;
		img_mov.max = img_org.max;

		// <-- 메모리 할당 - 원본이미지와 동일한 크기로
		img_mov.pixels = new byte[img_org.height][];

		for (int i = 0; i < img_org.height; i++) {
			img_mov.pixels[i] = new byte[img_org.width];
		}
		// -->

		// <-- 배경색으로 초기화
		for (int i = 0; i < img_org.height; i++) {
			for (int j = 0; j < img_mov.width; j++) {
				img_mov.pixels[i][j] = 0; // 0: 검정색
			}
		}
		// -->

		// <-- xd(가로), yd(세로) 만큼 좌표를 이동시킴
		for (int i = 0; i < img_org.height; i++) {
			new_y = i + yd;

			// y - 유효한 좌표값인지 체크
			if (new_y < 0 || new_y >= img_org.height) {
				continue;
			}

			for (int j = 0; j < img_org.width; j++) {
				new_x = j + xd;

				// x - 유효한 좌표값인지 체크
				if (new_x < 0 || new_x >= img_org.width) {
					continue;
				}

				img_mov.pixels[new_y][new_x] = img_org.pixels[i][j];
			}
		}
		// -->

		return true;
	}

	boolean fnMirrorImg(final PGMImage img_org, final PGMImage img_mir) {
		int new_x;

		// 헤더값 복사
		img_mir.M = img_org.M;
		img_mir.N = img_org.N;
		img_mir.width = img_org.width;
		img_mir.height = img_org.height;
		img_mir.max = img_org.max;

		// <-- 메모리 할당 - 원본이미지와 동일한 크기로
		img_mir.pixels = new byte[img_org.height][];

		for (int i = 0; i < img_org.height; i++) {
			img_mir.pixels[i] = new byte[img_org.width];
		}
		// -->

		// <-- 미러(좌우반전) 효과
		for (int i = 0; i < img_org.height; i++) {
			for (int j = 0; j < img_org.width; j++) {
				new_x = img_org.width - j - 1; // 배열은 0부터 시작이므로 1을 빼줌

				img_mir.pixels[i][new_x] = img_org.pixels[i][j];
			}
		}
		// -->

		return true;
	}

	boolean fnFlipImg(final PGMImage img_org, final PGMImage img_mir) {
		int new_y;

		// 헤더값 복사
		img_mir.M = img_org.M;
		img_mir.N = img_org.N;
		img_mir.width = img_org.width;
		img_mir.height = img_org.height;
		img_mir.max = img_org.max;

		// <-- 메모리 할당 - 원본이미지와 동일한 크기로
		img_mir.pixels = new byte[img_org.height][];

		for (int i = 0; i < img_org.height; i++) {
			img_mir.pixels[i] = new byte[img_org.width];
		}
		// -->

		// <-- 미러(좌우반전) 효과
		for (int i = 0; i < img_org.height; i++) {
			for (int j = 0; j < img_org.width; j++) {
				new_y = img_org.height - i - 1; // 배열은 0부터 시작이므로 1을 빼줌

				img_mir.pixels[new_y][j] = img_org.pixels[i][j];
			}
		}
		// -->

		return true;
	}

	// 인자로 넘어온 시작점과 끝점을 포함한 영역을 크롭
	boolean fnCropImg(final PGMImage img_org, final PGMImage img_crp, final int xs, final int ys, final int xe,
			final int ye) {
		int new_height = ye - ys + 1;
		int new_width = xe - xs + 1;

		// System.out.printf("%d/%d \n", new_height, new_width);

		int y = 0;
		int x = 0;

		// 헤더값 복사
		img_crp.M = img_org.M;
		img_crp.N = img_org.N;
		img_crp.width = new_width;
		img_crp.height = new_height;
		img_crp.max = img_org.max;

		// <-- 메모리 할당 - 크롭영역의 크기만큼
		img_crp.pixels = new byte[new_height][];

		for (int i = 0; i < new_height; i++) {
			img_crp.pixels[i] = new byte[new_width];
		}
		// -->

		// <-- 크롭
		for (int i = ys; i <= ye; i++) {
			x = 0;

			for (int j = xs; j <= xe; j++) {
				img_crp.pixels[y][x] = img_org.pixels[i][j];
				x++;
			}

			y++;
		}
		// -->

		return true;
	}

	boolean fnResizeImg(final PGMImage img_org, final PGMImage img_new, final double n) {
		int new_height = (int) (img_org.height * n);
		int new_width = (int) (img_org.width * n);
		int new_x;
		int new_y;

		// 헤더값 복사
		img_new.M = img_org.M;
		img_new.N = img_org.N;
		img_new.width = new_width;
		img_new.height = new_height;
		img_new.max = img_org.max;

		// <-- 메모리 할당 - 새로 생성될 이미지의 크기에 따라
		img_new.pixels = new byte[new_height][];

		for (int i = 0; i < new_height; i++) {
			img_new.pixels[i] = new byte[new_width];
		}
		// -->

		// <-- 배경색으로 초기화
		for (int i = 0; i < img_new.height; i++) {
			for (int j = 0; j < img_new.width; j++) {
				img_new.pixels[i][j] = 0; // 0: 검정색
			}
		}
		// -->

		// <-- 리사이즈(확대, 축소)
		for (int i = 0; i < img_org.height; i++) {
			new_y = (int) Math.ceil(i * n);

			if (new_y >= img_new.height) {
				new_y = img_new.height - 1;
			}

			for (int j = 0; j < img_org.width; j++) {
				new_x = (int) Math.ceil(j * n);

				if (new_x >= img_new.width) {
					new_x = img_new.width - 1;
				}

				img_new.pixels[new_y][new_x] = img_org.pixels[i][j];
			}
		}
		// -->

		return true;
	}

	boolean fnRotateImg(final PGMImage img_org, final PGMImage img_new, final double degree) {
		int center_x = img_org.width / 2;
		int center_y = img_org.height / 2;
		int new_x;
		int new_y;
		double pi = 3.141592;
		double seta;

		// seta값 계산
		seta = pi / (180.0 / degree);

		// 헤더값 복사
		img_new.M = img_org.M;
		img_new.N = img_org.N;
		img_new.width = img_org.width;
		img_new.height = img_org.height;
		img_new.max = img_org.max;

		// <-- 메모리 할당 - 새로 생성될 이미지의 크기에 따라
		img_new.pixels = new byte[img_org.height][];

		for (int i = 0; i < img_org.height; i++) {
			img_new.pixels[i] = new byte[img_org.width];
		}
		// -.

		// <-- 배경색으로 초기화
		for (int i = 0; i < img_new.height; i++) {
			for (int j = 0; j < img_new.width; j++) {
				img_new.pixels[i][j] = 0; // 0: 검정색
			}
		}
		// -.

		// <-- 회전
		for (int i = 0; i < img_org.height; i++) {
			for (int j = 0; j < img_org.width; j++) {
				new_x = (int) ((i - center_y) * Math.sin(seta) + (j - center_x) * Math.cos(seta) + center_x);
				new_y = (int) ((i - center_y) * Math.cos(seta) - (j - center_x) * Math.sin(seta) + center_y);

				if (new_x < 0)
					continue;
				if (new_x >= img_org.width)
					continue;
				if (new_y < 0)
					continue;
				if (new_y >= img_org.height)
					continue;

				img_new.pixels[new_y][new_x] = img_org.pixels[i][j];
			}
		}
		// -.

		return true;
	}
	
	boolean fnInterpolate(final PGMImage img)
	{
		int left_pixval = 0;
		int right_pixval = 0;

		for(int i=0; i<img.height; i++){
			for(int j=0; j<img.width; j++){
				if(j == 0){
					right_pixval = img.pixels[i][j+1];
					left_pixval  = right_pixval;
				}
				else if(j == img.width - 1){
					left_pixval  = img.pixels[i][j-1];
					right_pixval = left_pixval;
				}
				else{
					left_pixval  = img.pixels[i][j-1];
					right_pixval = img.pixels[i][j+1];
				}
				

				if(img.pixels[i][j] == 0 && left_pixval != 0 && right_pixval != 0){
					img.pixels[i][j] = (byte) (( left_pixval + right_pixval ) / 2);
				}
			}
		}

		return true;
	}

	
	boolean fnInterpolate_zoom(final PGMImage img)
	{
		int pre_pixval = 0;

		// → 방향으로 진행
		for(int i=0; i<img.height; i++){
			for(int j=0; j<img.width; j++){
				if(img.pixels[i][j] == 0 && pre_pixval != 0){
					img.pixels[i][j] = (byte) pre_pixval;
				}

				pre_pixval = img.pixels[i][j];
			}

			pre_pixval = 0;
		}


		// ↓ 방향으로 진행
		for(int m=0; m<img.width; m++){
			for(int n=0; n<img.height; n++){
				if(img.pixels[n][m] == 0 && pre_pixval != 0){
					img.pixels[n][m] = (byte) pre_pixval;
				}

				pre_pixval = img.pixels[n][m];
			}

			pre_pixval = 0;
		}


		return true;
	}
	
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
		final String orginfile = "C:/DEV/DOCs/ImageProcessing/강의/car_sample.pgm";
		final String fileNm1 = "C:/DEV/DOCs/ImageProcessing/강의/Output/car_sample.su.pgm";
		final String fileNm2 = "C:/DEV/DOCs/ImageProcessing/강의/Output/car_sample.sd.pgm";
		ImageProcessingTest imgtest = new ImageProcessingTest();
		PGMImage pgmImg = new PGMImage();
		imgtest.fnReadPGM(orginfile, pgmImg);
		PGMImage img_zoom_in = new PGMImage();
		imgtest.fnResizeImg(pgmImg, img_zoom_in, 2.5);
		PGMImage img_zoom_out = new PGMImage();
		imgtest.fnResizeImg(pgmImg, img_zoom_out, 0.5);
		
		
		imgtest.fnWritePGM(fileNm1, img_zoom_in);
		imgtest.fnWritePGM(fileNm2, img_zoom_out);
		
	}

	// C:\DEV\DOCs\ImageProcessing\강의
	public static void old_main_placeholder(String[] args) {
		byte b = (byte) 0xFA;
		int i = 0xFFFFFFFA;
		short s = (short) (b & 0xFF);
		i = (int) (s & 0xFFFFFF);

		System.out.printf("%d %d %d \n", b, i, s);

		i = -1;
		s = (short) (i & 0xFFFF);
		b = (byte) i;
		s = (short) (b & 0xFF);
		i = (int) (s & 0xFFFFFF);
		System.out.printf("%d %d %d \n", b, i, s);

		ImageProcessingTest imgtest = new ImageProcessingTest();
		PGMImage pgmImg = new PGMImage();

		String fileNm = "C:/DEV/DOCs/ImageProcessing/강의/sample1.pgm";
		String fileNm2 = "C:/DEV/DOCs/ImageProcessing/강의/sample2.pgm";
		String fileNm3 = "C:/DEV/DOCs/ImageProcessing/강의/sample2_err.pgm";
		imgtest.fnReadPGM(fileNm, pgmImg);
		imgtest.fnWritePGM(fileNm2, pgmImg, 30);
		imgtest.fnWritePGM_Error(fileNm3, pgmImg, 25);

		int brightness[] = { -30, -20, -10, 0, 10, 20, 30 };

		String orginfile = "C:/DEV/DOCs/ImageProcessing/강의/sample1.pgm";
		String destinfile = "C:/DEV/DOCs/ImageProcessing/강의/sample_{name}({brightness}).pgm";
		imgtest.fnReadPGM(orginfile, pgmImg);

		String filename = null;
		for (i = 0; i < brightness.length; i++) {
			filename = destinfile.replace("{name}", String.valueOf(i)).replace("{brightness}",
					String.valueOf(brightness[i]));
			imgtest.fnWritePGM(filename, pgmImg, brightness[i]);
		}

		int itmp = -4;
		byte btmp = (byte) itmp;

		short tmp = (short) (btmp & 0xFF);

		System.err.println(tmp);

		int invertVal[] = { 255, 250, 240, 200, 100, 50, -10 };

		String destinfile2 = "C:/DEV/DOCs/ImageProcessing/강의/sample_{name}({inv}).pgm";
		imgtest.fnReadPGM(orginfile, pgmImg);

		String filename2 = null;
		for (i = 0; i < invertVal.length; i++) {
			filename2 = destinfile2.replace("{name}", String.valueOf(i)).replace("{inv}", String.valueOf(invertVal[i]));
			imgtest.fnWriteInvPGM(filename2, pgmImg, invertVal[i]);
		}

		final int limit = 125;
		final double contrast = 0.5;

		imgtest.fnReadPGM(orginfile, pgmImg);

		imgtest.fnWriteContPGM(fileNm, pgmImg, limit, contrast);

		int xd = 10;
		int yd = 20;
		PGMImage img_mov = new PGMImage();

		imgtest.fnMoveImg(pgmImg, img_mov, xd, yd);
		imgtest.fnWritePGM(fileNm, img_mov);

		PGMImage img_mir = new PGMImage();

		imgtest.fnMirrorImg(pgmImg, img_mir);
		imgtest.fnWritePGM(fileNm, img_mir);

		PGMImage img_crop = new PGMImage();

		int sx = (int) pgmImg.width / 5;
		int sy = (int) pgmImg.height / 5;
		int ex = sx + 300;
		int ey = sy + 170;
		System.out.println(pgmImg.width + " : " + pgmImg.height);
		System.out.println(String.format("sx:%d sy:%d ex:%d ey:%d", sx, sy, ex, ey));
		imgtest.fnCropImg(pgmImg, img_crop, sx, sy, ex, ey);
		imgtest.fnWritePGM(fileNm, img_crop);
		
		PGMImage img_zoom_in = new PGMImage();
		imgtest.fnResizeImg(pgmImg, img_zoom_in, 2.5);
		PGMImage img_zoom_out = new PGMImage();
		imgtest.fnResizeImg(pgmImg, img_zoom_out, 0.7);

		//imgtest.fnWritePGM(fileNm1, img_zoom_in);

		imgtest.fnWritePGM(fileNm2, img_zoom_out);
	}
}
