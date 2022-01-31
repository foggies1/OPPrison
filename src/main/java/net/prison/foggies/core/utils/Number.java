package net.prison.foggies.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Number {

	public static long add(long original, long input){
		return original + input;
	}

	public static long take(long original, long input){
		if(original - input <= 0) input = original;
		return original - input;
	}

	public static final NavigableMap<Double, String> suffixes = new TreeMap<>();

	static {
		suffixes.put(1_000D, "k");
		suffixes.put(1_000_000D, "M");
		suffixes.put(1_000_000_000D, "B");
		suffixes.put(1_000_000_000_000D, "T");
		suffixes.put(1_000_000_000_000_000D, "Q");
		suffixes.put(1_000_000_000_000_000_000D, "QT");
	}


	public static long getLargest(int[] a, int total){
		int temp;
		for (int i = 0; i < total; i++)
		{
			for (int j = i + 1; j < total; j++)
			{
				if (a[i] > a[j])
				{
					temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
		return a[total-1];
	}

	public static String formatted(double d) {
		NumberFormat form = NumberFormat.getInstance(Locale.ENGLISH);

		form.setMaximumFractionDigits(2);

		form.setMinimumFractionDigits(0);
		if (d < 1000.0D) {
			return form.format(d);
		}
		if (d < 1000000.0D) {
			return form.format(d / 1000.0D) + "K";
		}
		if (d < 1.0E9D) {
			return form.format(d / 1000000.0D) + "M";
		}
		if (d < 1.0E12D) {
			return form.format(d / 1.0E9D) + "B";
		}
		if (d < 1.0E15D) {
			return form.format(d / 1.0E12D) + "T";
		}
		if (d < 1.0E18D) {
			return form.format(d / 1.0E15D) + "Q";
		}
		if (d < 1.0E21D) {
			return form.format(d / 1.0E18D) + "QT";
		}
		if (d < 1.0E24D) {
			return form.format(d / 1.0E21D) + "QD";
		}
		if (d < 1.0E27D) {
			return form.format(d / 1.0E24D) + "QDT";
		}
		if (d < 1.0E30D) {
			return form.format(d / 1.0E27D) + "S";
		}
		if (d < 1.0E33D) {
			return form.format(d / 1.0E30D) + "SX";
		}

		long l = (long) d;
		return String.valueOf(l);
	}

	public static String percentage(double number) {
		return NumberFormat.getPercentInstance().format(number);
	}

	public static String decimals(double number, int places) {
		BigDecimal bigDecimal = new BigDecimal(number);
		bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
		return String.valueOf(bigDecimal.doubleValue());
	}

	public static String simple(double number){
		if (number == Double.MIN_VALUE) return simple(Double.MIN_VALUE + 1);
		if (number < 0) return "-" + simple(-number);
		if (number < 1000) return Double.toString(number);

		Map.Entry<Double, String> e = suffixes.floorEntry(number);
		Double divideBy = e.getKey();
		String suffix = e.getValue();

		double truncated = number / (divideBy / 10);
		boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
		return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
	}

	public static String pretty(double number) {
		return NumberFormat.getNumberInstance().format(number);
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return map.entrySet()
				.stream()
				.sorted(Map.Entry.<K, V>comparingByValue().reversed())
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(e1, e2) -> e1,
						LinkedHashMap::new
				));
	}

}
