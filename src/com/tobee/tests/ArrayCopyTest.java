package com.tobee.tests;

public class ArrayCopyTest {

	public static void main(String[] args)
	{
		int[][] my2d = new int[][]{{1,2},{3,4},{5,6}};
		int[] my1d = new int[3*2];
		System.err.println("length:" + my2d.length);
		
		int tmp[] = null;
		int offset = 0;
		for(int i = 0; i< my2d.length;i++)
		{
			tmp = my2d[i];
			System.err.println("tmp length:" + tmp.length);
			System.arraycopy(tmp, 0, my1d, offset, tmp.length);
			offset += tmp.length;
		}
		System.err.println("copy length:" + my1d.length);
		
		for(int i = 0; i< my1d.length;i++)
		{
			System.err.print(my1d[i] +",");
		}
	}
}
