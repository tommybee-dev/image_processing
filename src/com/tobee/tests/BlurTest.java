package com.tobee.tests;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.tobee.image.proc.filters.BoxBlurFilter;
import com.tobee.image.proc.filters.ConvolveFilter;
import com.tobee.image.proc.filters.GaussianFilter;
import com.tobee.image.proc.filters.GlowFilter;
import com.tobee.image.proc.filters.LensBlurFilter;
import com.tobee.image.proc.filters.MotionBlurFilter;
import com.tobee.image.proc.filters.MotionBlurOp;
import com.tobee.image.proc.filters.RaysFilter;
import com.tobee.image.proc.filters.ShadowFilter;
import com.tobee.image.proc.filters.SmartBlurFilter;
import com.tobee.image.proc.filters.UnsharpFilter;
import com.tobee.image.proc.filters.VariableBlurFilter;
import com.tobee.image.proc.incubator.Blurring;

public class BlurTest {
	
	static class NormalBlurTest implements Blurring
	{
		private static final String Blur1_src_path = "resources/flower_src.jpg";
		private static final String Blur1_desc_path = "resources/flower_dest.jpg";
		private static final String Blur1_path = "resources/flower_blur.jpg";
		private static final String formatName = "jpg";
		private static BufferedImage Blur1_src;
		private static BufferedImage Blur1_dest;
		private static BufferedImage Blur1_img;
		
		NormalBlurTest()
		{
			try {
				Blur1_src = ImageIO.read(new File(Blur1_src_path));
				Blur1_dest = ImageIO.read(new File(Blur1_desc_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
		    float[] matrix = new float[9];
			
			for (int i = 0; i < matrix.length; i++)
				matrix[i] = 1.0f/9.0f;

		    BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, matrix), ConvolveOp.EDGE_NO_OP, null );
		    Blur1_img = op.filter(Blur1_src, Blur1_dest);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Blur1_img, formatName, new File(Blur1_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return success;
		}
		
		@Override
		public String toString()
		{
			return "NormalBlurTest..";
		}
	}
	
	static class BigBlurTest implements Blurring
	{
		private static final String Blur1_src_path = "resources/flower_src.jpg";
		private static final String Blur1_desc_path = "resources/flower_dest.jpg";
		private static final String Blur1_path = "resources/flower_bigblur.jpg";
		private static final String formatName = "jpg";
		private static BufferedImage Blur1_src;
		private static BufferedImage Blur1_dest;
		private static BufferedImage Blur1_img;
		
		BigBlurTest()
		{
			try {
				Blur1_src = ImageIO.read(new File(Blur1_src_path));
				Blur1_dest = ImageIO.read(new File(Blur1_desc_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			float[] matrix = new float[400];
			
			for (int i = 0; i < matrix.length; i++)
				matrix[i] = 1.0f/400.0f;

		    BufferedImageOp op = new ConvolveOp( new Kernel(20, 20, matrix), ConvolveOp.EDGE_NO_OP, null );
		    Blur1_img = op.filter(Blur1_src, Blur1_dest);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Blur1_img, formatName, new File(Blur1_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return success;
		}
		
		@Override
		public String toString()
		{
			return "BigBlurTest..";
		}
	}
	
	static class ConvolvTest implements Blurring
	{
		private static final String Convolv_src_path = "resources/flower_src.jpg";
		private static final String Convolv_desc_path = "resources/flower_dest.jpg";
		private static final String Convolv_path = "resources/flower_convolv.jpg";
		private static final String formatName = "jpg";
		private static BufferedImage Convolv_src;
		private static BufferedImage Convolv_dest;
		private static BufferedImage Convolv_img;
		
		private ConvolveFilter convfilter;
		
		ConvolvTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
				Convolv_dest = ImageIO.read(new File(Convolv_desc_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			float[] matrix = new float[400];
			int i = 0; 
			
			for (i = 0; i < matrix.length; i++)
				matrix[i] = 1.0f/9.0f;
			
			//matrix[--i] = 1.0f/400.0f;
			
			convfilter = new ConvolveFilter(new Kernel(20, 20, matrix));
			//ConvolveFilter convfilter = new ConvolveFilter(matrix);
			
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
	
	static class BoxBlurTest implements Blurring
	{
		private static final String BoxBlur_src_path = "resources/flower_src.jpg";
		private static final String BoxBlur_desc_path = "resources/flower_dest.jpg";
		private static final String BoxBlur_path = "resources/flower_box.jpg";
		private static final String formatName = "jpg";
		private BufferedImage BoxBlur_src;
		//private BufferedImage Convolv_dest;
		private BufferedImage BoxBlur_img;
		private BoxBlurFilter convfilter;
		
		BoxBlurTest()
		{
			try {
				BoxBlur_src = ImageIO.read(new File(BoxBlur_src_path));
				//Convolv_dest = ImageIO.read(new File(BoxBlur_desc_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new BoxBlurFilter();
			convfilter.setHRadius(0);
			convfilter.setVRadius(10);
			//convfilter.setIterations(10);
			//convfilter.setRadius(9);
			
		    BoxBlur_img = convfilter.filter(BoxBlur_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(BoxBlur_img, formatName, new File(BoxBlur_path));
			} catch (IOException e) {
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
		private static final String Convolv_src_path = "resources/flower_src.jpg";
		private static final String Convolv_desc_path = "resources/flower_dest.jpg";
		private static final String Convolv_path = "resources/flower_gauss.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_dest;
		private BufferedImage Convolv_img;
		private GaussianFilter convfilter;
		
		GaussianBlurTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
				Convolv_dest = ImageIO.read(new File(Convolv_desc_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new GaussianFilter(20);
			
			//convfilter.setRadius(9);
			
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
		private static final String Convolv_src_path = "resources/flower_src.jpg";
		private static final String Convolv_path = "resources/flower_motion.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_img;
		private MotionBlurFilter convfilter;
		
		MotionBlurTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new MotionBlurFilter();
			//convfilter.setAngle(20.0f);
			//convfilter.setDistance(30.0f);
			//convfilter.setRotation(0.45f);
			//convfilter.setWrapEdges(true);
			convfilter.setZoom(0.45f);
			
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
	
	static class MotionBlurOpTest implements Blurring
	{
		private static final String Convolv_src_path = "resources/flower_src.jpg";
		private static final String Convolv_path = "resources/flower_motion_op.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_img;
		private MotionBlurOp convfilter;
		
		MotionBlurOpTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new MotionBlurOp();
			//convfilter.setAngle(20.0f);
			//convfilter.setDistance(30.0f);
			convfilter.setRotation(1.45f);
			//convfilter.setWrapEdges(true);
			//convfilter.setZoom(0.45f);
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
	
	static class LensBlurFilterTest implements Blurring
	{
		private static final String Convolv_src_path = "resources/christmas_src.jpg";
		private static final String Convolv_path = "resources/christmas_lens.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_img;
		private LensBlurFilter convfilter;
		
		LensBlurFilterTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new LensBlurFilter();
			//convfilter.setBloom(0.9f);
			//convfilter.setBloomThreshold(10.5f);
			//convfilter.setRadius(5.5f);
			//convfilter.setSides(10);
			
		    Convolv_img = convfilter.filter(Convolv_src, null, BufferedImage.TYPE_INT_ARGB);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
		private static final String Convolv_src_path = "resources/flower_src.jpg";
		private static final String Convolv_path = "resources/flower_smart.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_img;
		private SmartBlurFilter convfilter;
		
		SmartBlurFilterTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new SmartBlurFilter();
			
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
	
	static class VariableBlurFilterTest implements Blurring
	{
		private static final String Convolv_src_path = "resources/flower_src.jpg";
		private static final String Convolv_path = "resources/flower_variable.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_img;
		private VariableBlurFilter convfilter;
		
		VariableBlurFilterTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new VariableBlurFilter();
			
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
		private static final String Convolv_src_path = "resources/flower_src.jpg";
		private static final String Convolv_path = "resources/flower_unsharp.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_img;
		private UnsharpFilter convfilter;
		
		UnsharpFilterTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new UnsharpFilter();
			
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
		private static final String Convolv_src_path = "resources/flower_src.jpg";
		private static final String Convolv_path = "resources/flower_unsharp.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_img;
		private GlowFilter convfilter;
		
		GlowFilterTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new GlowFilter();
			
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
	
	static class ShadowFilterTest implements Blurring
	{
		private static final String Convolv_src_path = "resources/flower_src.jpg";
		private static final String Convolv_path = "resources/flower_unsharp.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_img;
		private ShadowFilter convfilter;
		
		ShadowFilterTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new ShadowFilter();
			
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
	
	static class RaysFilterTest implements Blurring
	{
		private static final String Convolv_src_path = "resources/shadow_before.jpg";
		private static final String Convolv_path = "resources/shadow_after.jpg";
		private static final String formatName = "jpg";
		private BufferedImage Convolv_src;
		private BufferedImage Convolv_img;
		private RaysFilter convfilter;
		
		RaysFilterTest()
		{
			try {
				Convolv_src = ImageIO.read(new File(Convolv_src_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void doBlur() {
			convfilter = new RaysFilter();
			
		    Convolv_img = convfilter.filter(Convolv_src, null);
		}

		@Override
		public boolean makeImageBlurred() {
			boolean success = false;
			try {
				success = ImageIO.write(Convolv_img, formatName, new File(Convolv_path));
			} catch (IOException e) {
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
	
	private static final int NORMAL_BLUR = 1;
	private static final int BIG_BLUR = 2;
	private static final int CONVOLV_BLUR = 3;
	private static final int BOX_BLUR = 4;
	private static final int GAUSSIAN_BLUR = 5;
	private static final int MOTION_BLUR = 6;
	private static final int MOTION_BLUR_OP = 7;
	private static final int LENS_BLUR = 8;
	private static final int SMART_BLUR = 9;
	private static final int VARIABLE_BLUR = 10;
	private static final int UNSHARP = 11;
	private static final int GLOW = 12;
	private static final int SHADOW = 13;
	private static final int RAYS = 14;
	
	private static final int SELECTED_TEST = LENS_BLUR;
	
	public static void main(String[] args)
	{
		
		Blurring blurring = null;

		switch(SELECTED_TEST)
		{
		case NORMAL_BLUR:
			blurring = new NormalBlurTest();
			break;
		case BIG_BLUR:
			blurring = new BigBlurTest();
			break;
		case CONVOLV_BLUR:
			blurring = new ConvolvTest();
			break;
		case BOX_BLUR:
			blurring = new BoxBlurTest();
			break;
		case GAUSSIAN_BLUR:
			blurring = new GaussianBlurTest();
			break;
		case MOTION_BLUR:
			blurring = new MotionBlurTest();
			break;
		case MOTION_BLUR_OP:
			blurring = new MotionBlurOpTest();
			break;
		case LENS_BLUR:
			blurring = new LensBlurFilterTest();
			break;
		case SMART_BLUR:
			blurring = new SmartBlurFilterTest();
			break;
		case VARIABLE_BLUR:
			blurring = new VariableBlurFilterTest();
			break;
		case UNSHARP:
			blurring = new UnsharpFilterTest();
			break;
		case GLOW:
			blurring = new GlowFilterTest();
			break;
		case SHADOW:
			blurring = new ShadowFilterTest();
			break;
		case RAYS:
			blurring = new RaysFilterTest();
			break;
		}
		
		blurring.doBlur();
		if(blurring.makeImageBlurred())
		{
			System.err.println(blurring.toString() + "...Sucess");
		}
		
	}
}
