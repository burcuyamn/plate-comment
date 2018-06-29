package com.yorum.plaka;

import java.util.regex.*;
import com.yorum.plaka.dao.pojo.Plate; 

public class PlateUtils {

	public static final String PLATE_PATTERN = "^(0[1-9]|[1-7][0-9]|8[01])(([A-PR-VYZ])(\\d{4,5})|([A-PR-VYZ]{2})(\\d{3,4})|([A-PR-VYZ]{3})(\\d{2,3}))";
	
	/**
	 * Gelen plaka stringini PLATE_PATTERN ile karsilastirir ve plaka standardina uygun olup olmadigini kontrol eder.
	 * @param plate string tipte plaka verisidir.
	 * @return boolean deger dondurur.
	 */
	public static boolean validate(String plate) {
		return Pattern.matches(PLATE_PATTERN, standardize(plate));
	}
	
	/**
	 * Parametre olarak gelen plakanin bosluklari temizler ve tum harfleri buyuk harfe cevirir.
	 * @param plate string tipte plaka verisidir.
	 * @return string tipte {@link Plate} doner.
	 */
	public static String standardize(String plate) {
		return plate.replaceAll("\\s+", "").toUpperCase();
	}
	
}