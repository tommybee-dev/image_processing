package com.tobee.tests;

import com.tobee.image.proc.incubator.Blurring;
import com.tobee.image.proc.incubator.PPMImage;
import com.tobee.image.proc.incubator.filters.BoxBlurFilter;
import com.tobee.image.proc.incubator.filters.GaussianFilter;
import com.tobee.image.proc.incubator.filters.GlowFilter;
import com.tobee.image.proc.incubator.filters.MotionBlurFilter;
import com.tobee.image.proc.incubator.filters.SmartBlurFilter;
import com.tobee.image.proc.incubator.filters.UnsharpFilter;

public class PPMProcessing {
	//PPMImage read(final String fileNm)
	private static final String CAR_IMAGE_STRING = "resources/car_sample.ppm";
	private static final String CONV_CAR_IMAGE_STRING = "resources/car_sample_{filter}.ppm";
	private static final String FLOWER_IMAGE_STRING = "resources/flower_src.ppm";
	private static final String CONV_FLOWER_IMAGE_STRING = "resources/flower_src_{filter}.ppm";
	
	
	static class BoxBlurTest implements Blurring
	{
		private static final String BoxBlur_src_path = CAR_IMAGE_STRING;
		private static final String BoxBlur_path = CONV_CAR_IMAGE_STRING.replace("{filter}", "box");
		//private static final String formatName = "jpg";
		private PPMImage BoxBlur_src;
		//private BufferedImage Convolv_dest;
		private PPMImage BoxBlur_img;
		private BoxBlurFilter convfilter;
		
		BoxBlurTest()
		{
			BoxBlur_src = PPMImage.read(BoxBlur_src_path);
			BoxBlur_img = new PPMImage(BoxBlur_src.getHeight(), BoxBlur_src.getWidth());
		}
		
		@Override
		public void doBlur() {
			convfilter = new BoxBlurFilter();
			//convfilter.setHRadius(5);
			//convfilter.setVRadius(30);
			convfilter.setRadius(5);
			
		    convfilter.filter(BoxBlur_src, BoxBlur_img);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				PPMImage.writeToFile(BoxBlur_path, BoxBlur_img);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return success;
		}
		
		@Override
		public String toString()
		{
			return convfilter.toString();
		}
	}
	
	static class GaussianBlurTest implements Blurring
	{
		private static final String Gaussian_src_path = CAR_IMAGE_STRING;
		//private static final String Gaussian_desc_path = "resources/flower_dest.jpg";
		private static final String Gaussian_path = CONV_CAR_IMAGE_STRING.replace("{filter}", "gauss");
		//private static final String formatName = "jpg";
		private PPMImage Gaussian_src;
		//private BufferedImage Gaussian_dest;
		private PPMImage Gaussian_img;
		private GaussianFilter convfilter;
		
		GaussianBlurTest()
		{
			Gaussian_src = PPMImage.read(Gaussian_src_path);
			Gaussian_img = new PPMImage(Gaussian_src.getHeight(), Gaussian_src.getWidth());
		}
		
		@Override
		public void doBlur() {
			convfilter = new GaussianFilter(15);
			//convfilter.setHRadius(5);
			//convfilter.setVRadius(30);
			//convfilter.setRadius(9);
			
		   convfilter.filter(Gaussian_src, Gaussian_img);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				PPMImage.writeToFile(Gaussian_path, Gaussian_img);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return success;
		}
		
		@Override
		public String toString()
		{
			return convfilter.toString();
		}
	}
	
	static class MotionBlurTest implements Blurring
	{
		private static final String Motion_src_path = CAR_IMAGE_STRING;
		private static final String Motion_path = CONV_CAR_IMAGE_STRING.replace("{filter}", "motion");
		//private static final String Motion_src_path = FLOWER_IMAGE_STRING;
		//private static final String Motion_path = CONV_FLOWER_IMAGE_STRING.replace("{filter}", "motion");;
		
		//private static final String formatName = "jpg";
		private PPMImage Motion_src;
		private PPMImage Motion_img;
		private MotionBlurFilter convfilter;
		
		MotionBlurTest()
		{
			Motion_src = PPMImage.read(Motion_src_path);
			Motion_img = new PPMImage(Motion_src.getHeight(), Motion_src.getWidth());
		}
		
		@Override
		public void doBlur() {
			convfilter = new MotionBlurFilter();
			convfilter.setAngle(0.3f);
			convfilter.setDistance(5.5f);
			convfilter.setRotation(0.2f);
			//convfilter.setWrapEdges(true);
			convfilter.setZoom(0.3f);
		    convfilter.filter(Motion_src, Motion_img);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				PPMImage.writeToFile(Motion_path, Motion_img);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return success;
		}
		
		@Override
		public String toString()
		{
			return convfilter.toString();
		}
	}
	
	static class GlowFilterTest implements Blurring
	{
		private static final String Glow_src_path = CAR_IMAGE_STRING;
		private static final String Glow_path = CONV_CAR_IMAGE_STRING.replace("{filter}", "glow");
		//private static final String formatName = "jpg";
		private PPMImage Glow_src;
		private PPMImage Glow_img;
		private GlowFilter convfilter;
		
		GlowFilterTest()
		{
			Glow_src = PPMImage.read(Glow_src_path);
			Glow_img = new PPMImage(Glow_src.getHeight(), Glow_src.getWidth());
		}
		
		@Override
		public void doBlur() {
			convfilter = new GlowFilter();
			convfilter.setAmount(0.3f);
			convfilter.setRadius(0.6f);
		    convfilter.filter(Glow_src, Glow_img);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				PPMImage.writeToFile(Glow_path, Glow_img);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return success;
		}
		
		@Override
		public String toString()
		{
			return convfilter.toString();
		}
	}
	
	static class SmartBlurFilterTest implements Blurring
	{
		private static final String Smart_src_path = CAR_IMAGE_STRING;
		private static final String Smart_path = CONV_CAR_IMAGE_STRING.replace("{filter}", "smart");
		//private static final String Smart_src_path = FLOWER_IMAGE_STRING;
		//private static final String Smart_path = CONV_FLOWER_IMAGE_STRING.replace("{filter}", "smart");
		
		//private static final String formatName = "jpg";
		private PPMImage Smart_src;
		private PPMImage Smart_img;
		private SmartBlurFilter convfilter;
		
		SmartBlurFilterTest()
		{
			Smart_src = PPMImage.read(Smart_src_path);
			Smart_img = new PPMImage(Smart_src.getHeight(), Smart_src.getWidth());
		}
		
		@Override
		public void doBlur() {
			convfilter = new SmartBlurFilter();
			convfilter.setRadius(20);
			convfilter.setThreshold(20);
		    convfilter.filter(Smart_src, Smart_img);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				PPMImage.writeToFile(Smart_path, Smart_img);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return success;
		}
		
		@Override
		public String toString()
		{
			return convfilter.toString();
		}
	}
	
	static class UnsharpFilterTest implements Blurring
	{
		private static final String Unsharp_src_path = CAR_IMAGE_STRING;
		private static final String Unsharp_path = CONV_CAR_IMAGE_STRING.replace("{filter}", "unsharp");
		//private static final String Unsharp_src_path = FLOWER_IMAGE_STRING;
		//private static final String Unsharp_path = CONV_FLOWER_IMAGE_STRING.replace("{filter}", "unsharp");;

		private PPMImage Unsharp_src;
		private PPMImage Unsharp_img;
		private UnsharpFilter convfilter;
		
		UnsharpFilterTest()
		{
			Unsharp_src = PPMImage.read(Unsharp_src_path);
			Unsharp_img = new PPMImage(Unsharp_src.getHeight(), Unsharp_src.getWidth());
		}
		
		@Override
		public void doBlur() {
			convfilter = new UnsharpFilter();
			convfilter.setAmount(30f);
			convfilter.setRadius(10f);
			convfilter.setThreshold(10);
		    convfilter.filter(Unsharp_src, Unsharp_img);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				PPMImage.writeToFile(Unsharp_path, Unsharp_img);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return success;
		}
		
		@Override
		public String toString()
		{
			return convfilter.toString();
		}
	}
	
	//private static final int NORMAL_BLUR = 1;
	//private static final int BIG_BLUR = 2;
	//private static final int CONVOLV_BLUR = 3;
	private static final int BOX_BLUR = 4;
	private static final int GAUSSIAN_BLUR = 5;
	private static final int MOTION_BLUR = 6;
	//private static final int MOTION_BLUR_OP = 7;
	//private static final int LENS_BLUR = 8;
	private static final int SMART_BLUR = 9;
	//private static final int VARIABLE_BLUR = 10;
	private static final int UNSHARP = 11;
	private static final int GLOW = 12;
	//private static final int SHADOW = 13;
	//private static final int RAYS = 14;
	
	private static final int SELECTED_TEST = MOTION_BLUR;
	
	public static void main(String[] args)
	{
		
		Blurring blurring = null;

		switch(SELECTED_TEST)
		{
		//case NORMAL_BLUR:
			//blurring = new NormalBlurTest();
			//break;
		//case BIG_BLUR:
			//blurring = new BigBlurTest();
			//break;
		//case CONVOLV_BLUR:
			//blurring = new ConvolvTest();
			//break;
		case BOX_BLUR:
			blurring = new BoxBlurTest();
			break;
		case GAUSSIAN_BLUR:
			blurring = new GaussianBlurTest();
			break;
		case MOTION_BLUR:
			blurring = new MotionBlurTest();
			break;
		//case MOTION_BLUR_OP:
			//blurring = new MotionBlurOpTest();
			//break;
		//case LENS_BLUR:
			//blurring = new LensBlurFilterTest();
			//break;
		case SMART_BLUR:
			blurring = new SmartBlurFilterTest();
			break;
		//case VARIABLE_BLUR:
			//blurring = new VariableBlurFilterTest();
			//break;
		case UNSHARP:
			blurring = new UnsharpFilterTest();
			break;
		case GLOW:
			blurring = new GlowFilterTest();
			break;
		//case SHADOW:
			//blurring = new ShadowFilterTest();
			//break;
		//case RAYS:
			//blurring = new RaysFilterTest();
			//break;
		}
		
		blurring.doBlur();
		if(blurring.makeImageBlurred())
		{
			System.err.println(blurring.toString() + "...Sucess");
		}
		
	}
}
