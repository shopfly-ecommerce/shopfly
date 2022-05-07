/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.util;

import java.math.BigDecimal;

/**
 * Due to theJavaCant operate on floating point numbers exactly, This utility class provides precise floating-point operations, including addition, subtraction, multiplication, division, and rounding.
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018years3month23The morning of10:26:41
 */
public final class CurrencyUtil {
	/**
	 * Default division accuracy
	 */
	private static final int DEF_DIV_SCALE = 2;

	/**
	 * This class cannot be instantiated
	 */
	private CurrencyUtil() {
	}

	/**
	 * Provides accurate addition operations.
	 * 
	 * @param v1
	 *            augend
	 * @param v2
	 *            addend
	 * @return The sum of two parameters
	 */
	public static Double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * Provides accurate subtraction operations.
	 * 
	 * @param v1
	 *            minuend
	 * @param v2
	 *            reduction
	 * @return The difference between two parameters
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * Provides accurate multiplication.
	 * 
	 * @param v1
	 *            multiplicand
	 * @param v2
	 *            The multiplier
	 * @return The product of two parameters
	 */
	public static Double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * provide（The relative）Accurate division, and in the event that you cant divide enough, Accurate to the decimal point10Bit, the next number is rounded.
	 * 
	 * @param v1
	 *            dividend
	 * @param v2
	 *            divisor
	 * @return The quotient of two parameters
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * provide（The relative）An accurate division operation. When there is an inexhaustible situation, byscaleParameter specifies precision, and subsequent numbers are rounded.
	 * 
	 * @param v1
	 *            dividend
	 * @param v2
	 *            divisor
	 * @param scale
	 *            Indicates to be accurate to several decimal places.
	 * @return The quotient of two parameters
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * Provides accurate rounding of decimal places.
	 * 
	 * @param v
	 *            Numbers that need to be rounded
	 * @param scale
	 *            Let me keep a few decimal places
	 * @return The rounded result
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static void main(String[] args){
		Double d1 = 1.01;
		Double d2 = 0.42;
//		//System.out.println(d1-d2);
//		//System.out.println(Arith.sub(d1, d2));
//		//System.out.println(Arith.sub1(d1, d2));
//		long a = System.currentTimeMillis();
//		for(int i=0;i<10000;i++){
//			double r = Arith.sub1(d1, d2);
//		}
//		//System.out.println(System.currentTimeMillis() - a);
//		//System.out.println(0.05+0.01);  
//	    //System.out.println(1.0-0.42);  
//	    //System.out.println(4.015*100);  
//	    //System.out.println(123.3/100);
	    System.out.println(CurrencyUtil.mul(4.01511, 100));
		
		//System.out.println( CurrencyUtil.sub(2D, 10D) );
	}
}
