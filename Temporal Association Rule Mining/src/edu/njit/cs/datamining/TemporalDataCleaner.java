package edu.njit.cs.datamining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.stream.events.EndDocument;

public class TemporalDataCleaner {

	/**
	 * File that contains the main program to run apriori algorithm
	 */
	
	//Getters and setters to store important information
	private List<String> transactions;
	private double min_support;
	private double min_confidence;
	private PrintWriter writer;
	private Date startDate;
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public double getMin_support() {
		return min_support;
	}

	public void setMin_support(double min_support) {
		this.min_support = min_support;
	}

	public double getMin_confidence() {
		return min_confidence;
	}

	public void setMin_confidence(double min_confidence) {
		this.min_confidence = min_confidence;
	}

	public List<String> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<String> transactions) {
		this.transactions = transactions;
	}

	/**
	 * 
	 * Main method that starts the application and runs until the user hit q to exit
	 */
	public static void main(String[] args) {
		TemporalDataCleaner apriori=new TemporalDataCleaner();
		
		while(true){
			System.out.println("\nTemporal Mining dataset cleaning application");
			System.out.println("\nPress enter to continue or q to quit");
			Scanner scnnr=new Scanner(System.in);
			String optn=scnnr.nextLine();
			if(optn.toUpperCase().equals("Q"))
				break;
			System.out.println("\nPlease enter the name of file containing transactions ");
			String fileName=scnnr.nextLine();
			if(new File(fileName).exists()){
				
				String stDate="06-10-2016";
				String endDate="10-10-2016";
				
				System.out.println("Start date : "+stDate+" , End date : "+endDate);
				
				apriori.setStartDate(apriori.parseDateFromString(stDate));
				apriori.setEndDate(apriori.parseDateFromString(endDate));
				
				apriori.cleanTransactions(fileName);
				
			}
			else
				System.out.println("\nUnable to find the specified file. Please enter a valid file\n");			
		}
	}
	
	/**
	 * Formatter to read the date in MM-dd-yyyy format, returns date object for a given date tring
	 * @param dt
	 * @return
	 */
	private Date parseDateFromString(String dt){
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd-yyyy");
		Date parsedDate=null;
		try {
			parsedDate=sdf.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}
	
	/**
	 * Writes the cleaned data into a new file
	 * @param fileName
	 */
	private void createOutputFile(String fileName){
		String op_fileName=fileName.split("\\.")[0]+"_cleaned.txt";
		try {
			writer =new PrintWriter(op_fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("\nUnable to create an output file to log the steps");
		} catch (UnsupportedEncodingException e) {
			System.out.println("\nUnable to create an output file to log the steps");
		}
	}
	
	
	
	/**
	 * Read the file with all transactions
	 * @param fileName name of the file
	 */
	public void cleanTransactions(String fileName){
		List<String> trnsctns=new ArrayList<String>();
		File file=new File(fileName);
		System.out.println("Reading transactions");
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line=reader.readLine();
			while(line!=null){
				String[] splitItems=line.split(",");
				System.out.println(splitItems[0]);
				if(isWithinDateRange(parseDateFromString(splitItems[0]), startDate, endDate)){
					trnsctns.add(line.substring(line.indexOf(",")+1));
				}
				line=reader.readLine();
			}
			setTransactions(trnsctns);
			createOutputFile(fileName);
			System.out.println("Finished reading transactions");
			for(String transaction:trnsctns){
				printItem(transaction);
			}
			if(writer!=null){
				writer.close();
			}
		} catch (IOException e) {
			System.out.println("Unable to read file");
		}
	}
	
	private boolean isWithinDateRange(Date actualDate, Date startDate, Date endDate){
		if(actualDate.after(startDate) && actualDate.before(endDate) || actualDate.after(endDate) && actualDate.before(startDate))
			return true;
		return false;
	}
	
	
	/**
	 * Logs the information in console as well as in a separate file
	 * @param itm
	 */
	public void printItem(String itm){
		System.out.println(itm);
		writer.println(itm);
	}
}
