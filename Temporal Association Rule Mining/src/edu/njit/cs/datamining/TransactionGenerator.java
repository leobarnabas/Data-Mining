package edu.njit.cs.datamining;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class TransactionGenerator {

	/**
	 * File that generates transactions for different databases
	 */
	public static void main(String[] args) {
		String fileName = "nodefailure.txt";
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			List<String> items = new ArrayList<String>();
			for(int i=1;i<=20;i++)
				items.add("Node"+i);

			for (int i = 0; i < 40; i++) {
				Random rndm = new Random();
				Integer maxItm = rndm.nextInt(7) + 8;
				HashSet<String> itm = new HashSet<String>();
				for (int j = 0; j < maxItm; j++) {
					itm.add(items.get(rndm.nextInt(10)).toUpperCase());
				}
				writer.println(generateDate() + ", "
						+ itm.toString().replace("[", "").replace("]", "").trim());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Generates a random date within the current year
	 * 
	 * @return a date within 2016
	 */
	private static String generateDate() {
		GregorianCalendar gc = new GregorianCalendar();
		int year = 2016;
		gc.set(Calendar.YEAR, year);
		int day = randomBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
		gc.set(Calendar.DAY_OF_YEAR, day);
		int month = randomBetween(1, gc.getActualMaximum(Calendar.MONTH));
		gc.set(Calendar.MONTH, month);
		String date=(gc.get(Calendar.MONTH) + 1) + "-"
				+ gc.get(Calendar.DAY_OF_MONTH) + "-" + gc.get(Calendar.YEAR);
		Random random = new Random();
		int millisInDay = 24*60*60*1000;
		Time time = new Time((long)random.nextInt(millisInDay));
		return date+" "+time.toString();
	}

	/**
	 * Returns a random value for a given range
	 * 
	 * @param start
	 * @param end
	 * @return a random between the range
	 */
	private static int randomBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

}
