package edu.njit.cs.datamining;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class TransactionGenerator {

	/**
	 * File that generates transactions for different databases
	 */
	public static void main(String[] args) {
		String fileName="petstore.txt";
		try {
			PrintWriter writer =new PrintWriter(fileName, "UTF-8");
			List<String> items=new ArrayList<String>();
			items.add("Leash");
			items.add("Cat litter");
			items.add("Green bowl");
			items.add("Green ball");
			items.add("Red ball");
			items.add("Scoop");
			items.add("Dog treat");
			items.add("Fish food");
			items.add("Feather toy");
			items.add("Eye drops");
			
			for(int i=0;i<40;i++){
				Random rndm=new Random();
				Integer maxItm=rndm.nextInt(7)+8;
				HashSet<String> itm=new HashSet<String>();
				for(int j=0;j<maxItm;j++){
					itm.add(items.get(rndm.nextInt(10)).toUpperCase());
				}
				writer.println(generateDate()+", "+itm.toString().replace("[","").replace("]", ""));
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
	 * @return
	 */
	private static String generateDate(){
		GregorianCalendar gc = new GregorianCalendar();
		int year = 2016;
		gc.set(gc.YEAR, year);
		int day=randomBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
		gc.set(gc.DAY_OF_YEAR, day);
		int month=randomBetween(1, gc.getActualMaximum(gc.MONTH));
		gc.set(gc.MONTH, month);
		return (gc.get(gc.MONTH)+1)+"-"+gc.get(gc.DAY_OF_MONTH)+"-"+gc.get(gc.YEAR);
	}
	
	/**
	 * Returns a random value for a given range
	 * @param start
	 * @param end
	 * @return
	 */
	private static int randomBetween(int start, int end){
		return start + (int) Math.round(Math.random() * (end - start));
	}

}
