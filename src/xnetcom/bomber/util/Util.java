package xnetcom.bomber.util;

import java.util.Random;

public class Util {
	
	public static int tomaDecision(int aStart, int aEnd) {		
		Random generator= new Random(23424267379234L);
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * generator.nextDouble());
		int aleatorio = (int) (fraction + aStart);
		return aleatorio;
	}
}
