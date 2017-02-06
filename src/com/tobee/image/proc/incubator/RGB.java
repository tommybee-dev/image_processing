package com.tobee.image.proc.incubator;

public class RGB
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
	
	RGB(int r, int g, int b)
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
	
	int getRGBInt()
	{
		//alpha,red,green,blue
		//return (RED << 16) | (GREEN << 8) | BLUE;
		return (0xFF << 24) | (RED << 16) | (GREEN << 8) | BLUE;
	}
	
	int getARGBInt()
	{
		//alpha,red,green,blue
		return (0xFF << 24) | (RED << 16) | (GREEN << 8) | BLUE;
	}
	
	@Override
	public String toString()
	{
		return RED + "/" + GREEN + "/" + BLUE;
	}
}
