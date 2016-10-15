package edu.njit.cs.datamining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Apriori {

	/**
	 * File that contains the main program to run apriori algorithm
	 */
	
	//Getters and setters to store important information
	private List<String> transactions;
	private double min_support;
	private double min_confidence;
	private PrintWriter writer;
	
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
		Apriori apriori=new Apriori();
		
		while(true){
			System.out.println("\nAssociation Rule Mining application");
			System.out.println("\nPress enter to continue or q to quit");
			Scanner scnnr=new Scanner(System.in);
			String optn=scnnr.nextLine();
			if(optn.toUpperCase().equals("Q"))
				break;
			System.out.println("\nPlease enter the name of file containing transactions ");
			String fileName=scnnr.nextLine();
			if(new File(fileName).exists()){
				System.out.println("\nPlease enter minimum support");
				Double minSupport=scnnr.nextDouble();
				apriori.setMin_support(minSupport);
				System.out.println("\nPlease enter minimum confidence");
				Double minConfidence=scnnr.nextDouble();
				apriori.setMin_confidence(minConfidence);
				apriori.runAlgorithm(fileName);	
			}
			else
				System.out.println("\nUnable to find the specified file. Please enter a valid file\n");			
		}
	}
	
	/**
	 * Function that generates 1,2,3,... itemsets and also generates the rules
	 * @param fileName filename specified by the user that contains transactions
	 */
	public void runAlgorithm(String fileName){
		readTransactions(fileName);
		String op_fileName=fileName+"_"+min_support+"-support_"+min_confidence+"-confidence.txt";
		try {
			writer =new PrintWriter(op_fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("\nUnable to create an output file to log the steps");
		} catch (UnsupportedEncodingException e) {
			System.out.println("\nUnable to create an output file to log the steps");
		}
		
		TreeMap<String,Integer> oneItemset=new TreeMap<String,Integer>();
		
		for(String itemset:transactions){
			String[] items=itemset.split(",");
			Arrays.sort(items);
			for(String item:items){
				item=item.trim();
				if(oneItemset.containsKey(item)){
					oneItemset.put(item, oneItemset.get(item)+1);
				}
				else
					oneItemset.put(item,1);
			}
		}
		
		
		HashMap<String,Double> supportList=new HashMap<String,Double>();
		
		HashMap<String,Double> supportItmMap=new HashMap<String,Double>();
		printItem("\n1 Itemset with support values,");
		List<String> uniqueItems=new ArrayList<String>();
		for(String key:oneItemset.keySet()){
			double support= ((double)oneItemset.get(key)/transactions.size());
			if(support>=min_support){
				supportList.put(key,support);
				supportItmMap.put(key,support);
				uniqueItems.add(key);
			}
			printItem(key+" "+support*transactions.size());
		}
		
		printItem("\n1 Itemset meeting minimum support of "+min_support*transactions.size());
		if(uniqueItems.size()==0)
			printItem("None");
		for(String itm:uniqueItems)
			printItem(itm);
		
		printItem("\n2 Itemset with support values,");
		if(uniqueItems.size()<2)
			printItem("None");
		TreeMap<String,Double> twoItemSupportList=new TreeMap<String,Double>();
		for(int i=0;i<uniqueItems.size();i++){
			for(int j=i+1;j<uniqueItems.size();j++){
				String itemset=uniqueItems.get(i)+","+uniqueItems.get(j);
				double support= (double)getItemsetCount(transactions, itemset)/transactions.size();
				if(support>=min_support){
					twoItemSupportList.put(itemset,support);
					supportItmMap.put(itemset, support);
				}
				printItem(itemset+" "+support*transactions.size());
			}
		}
		
		String[] twoItems=new String[twoItemSupportList.size()];
		twoItemSupportList.keySet().toArray(twoItems);
		
		
		printItem("\n2 Itemset meeting minimum support of "+min_support*transactions.size());
		if(twoItems.length==0)
			printItem("None");
		
		List<String> supportItems=new ArrayList<String>();
		List<String> supportItemList=new ArrayList<String>();
		
		for(String itm:twoItems){
			printItem(itm);
			supportItems.add(itm);
			supportItemList.add(itm);
		}
			
		
		List<String> tempItemList=new ArrayList<String>();
		int idx1,idx2;
		
		int itmSetIdx=3;
		
		while(true){
			printItem("\n"+itmSetIdx+" Itemset with support values,");
			tempItemList.clear();
			
			for(int i=0;i<supportItemList.size()-1;i++){
				idx1=supportItemList.get(i).lastIndexOf(",");
				for(int j=i+1;j<supportItemList.size();j++){
					idx2=supportItemList.get(j).lastIndexOf(",");
					if(supportItemList.get(i).substring(0,idx1).equals(supportItemList.get(j).substring(0, idx2))){
						String newItem=supportItemList.get(i)+supportItemList.get(j).substring(idx2);
						double support= (double)getItemsetCount(transactions, newItem)/transactions.size();
						if(support>=min_support){
							supportItems.add(newItem);
							supportItmMap.put(newItem, support);
							tempItemList.add(newItem);
						}
						printItem(newItem+" "+support*transactions.size());
					}
				}
			}
			
			if(supportItemList.size()==0)
				printItem("None");
			
			printItem("\n"+itmSetIdx+" Itemset meeting minimum support of "+min_support*transactions.size());
			if(tempItemList.size()<1)
				printItem("None");
			for(String itm:tempItemList)
				printItem(itm);
			
			if(tempItemList.size()<=1)
				break;
			else{
				supportItemList.clear();
				supportItemList.addAll(tempItemList);
			}
			
			itmSetIdx++;
		}
		
		printItem("\nFrequent itemsets meeting minimum support of "+min_support*transactions.size());
		if(supportItems.size()==0)
			printItem("None");
		for(String itm:supportItems)
			printItem(itm);
		
		TreeSet<String> ruleSet=new TreeSet<String>();
		
		for(String itmset:supportItems){
			double support= supportItmMap.get(itmset);
			List<String> itmList=Arrays.asList(itmset.split(","));
			List<List<String>> itmCombo=new ArrayList<List<String>>();
			
			Permutation(itmCombo,Arrays.<String>asList(), itmList);
			
			for(List<String> itm:itmCombo){
				for(int i=0;i<itm.size()-1;i++){
					
					TreeSet<String> left=new TreeSet<String>();
					TreeSet<String> right=new TreeSet<String>();
					
					for(int j=0;j<itm.size();j++){
						if(j<=i)
							left.add(itm.get(j));
						else
							right.add(itm.get(j));
					}
					
					String leftItm=left.toString().replace("[", "").replace("]", "");
					
					
					double leftSupport;
					if(supportItmMap.get(leftItm)!=null)
						leftSupport= supportItmMap.get(leftItm);
					else
						leftSupport= (double)getItemsetCount(transactions, leftItm)/transactions.size();
					
					double confdnce=support/leftSupport;
					if(confdnce>=min_confidence)
						ruleSet.add(left.toString()+"->"+right.toString());
				}
			}
		}
		
		printItem("\nAssociation rules meeting minimum confidence of "+min_confidence);
		if(ruleSet.size()==0)
			printItem("None");
		for(String rule:ruleSet)
			printItem(rule);
		if(writer!=null){
			System.out.println("\nPlease check the output file "+op_fileName+" for the steps");
			writer.close();
		}
		
	}
	
	/**
	 * Read the file with all transactions
	 * @param fileName name of the file
	 */
	public void readTransactions(String fileName){
		List<String> trnsctns=new ArrayList<String>();
		File file=new File(fileName);
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line=reader.readLine();
			while(line!=null){
				trnsctns.add(line);
				line=reader.readLine();
			}
			setTransactions(trnsctns);
		} catch (IOException e) {
			System.out.println("Unable to read file");
		}
	}
	
	/**
	 * Logs the information in console as well as in a separate file
	 * @param itm
	 */
	public void printItem(String itm){
		System.out.println(itm);
		writer.println(itm);
	}
	
	/**
	 * Function that generates all 2^n combination of itemsets 
	 * @param result
	 * @param prefix
	 * @param str
	 */
	public void Permutation(List<List<String>>  result,List<String> prefix, List<String> str){
		int n=str.size();
		if(n==0){
			result.add(prefix);
		}
		else{
			for(int i=0;i<n;i++){
				ArrayList<String> newPrefix=new ArrayList<String>();
				newPrefix.addAll(prefix);
				newPrefix.add(str.get(i));
				
				ArrayList<String> newStr=new ArrayList<String>();
				newStr.addAll(str);
				newStr.remove(i);
				
				Permutation(result,newPrefix,newStr);
			}
		}
	}
	
	/**
	 * Calculates the support of the given itemset
	 * @param transactions
	 * @param itemset
	 * @return
	 */
	public int getItemsetCount(List<String> transactions, String itemset){
		int itemsetCount=0;
		String[] items=itemset.split(",");
		boolean allOccurring=true;
		for(String transaction:transactions){
			allOccurring=true;
			for(int i=0;i<items.length;i++){
				if(!transaction.contains(items[i].trim())){
					allOccurring=false;
					break;
				}
			}
			if(allOccurring)
				itemsetCount++;
		}
		return itemsetCount;
	}
}
