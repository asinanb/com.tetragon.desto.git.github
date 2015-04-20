package com.tetragon.desto.util;

import java.util.Random;


public class MathUtil {
	
//	 Create a random number generator,
//	 seeds with current time by default:
	public static int randNum (int min,int max){
		Random rand = new Random();
		int i;
		//	 '%' limits maximum value to 99:
		int marj=max-min;
		i = min+Math.abs(rand.nextInt()) % marj;		
		return i;
	}
	
//	 Floating-point number tests:
//	 applies to doubles, too
	public static double randNum (double min,double max){
		Random rand = new Random();
		double i;
		double marj= max-min;
		//	 '%' limits maximum value to 99:
		double rnd = Math.abs(rand.nextDouble()) ;
		rnd = rnd * marj;
		i=min+rnd;
		return Math.abs(i);
	}
	public static float randNum (float min,float max){
		Random rand = new Random();
		float i;
		float marj= max-min;
		//	 '%' limits maximum value to 99:
		float rnd = Math.abs(rand.nextFloat()) ;
		rnd = rnd * marj;
		i=min+rnd;
		return Math.abs(i);
	}
	public static int randNum (int max){
		Random rand = new Random();
		int i;
		//	 '%' limits maximum value to 99:
		int marj=max;
		i = Math.abs(rand.nextInt()) % marj;		
		return Math.abs(i);
	}
}
