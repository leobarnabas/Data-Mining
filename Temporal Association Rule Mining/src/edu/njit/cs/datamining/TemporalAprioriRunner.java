package edu.njit.cs.datamining;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TemporalAprioriRunner {

	private static void runCommand(String command) throws Exception {
		Process process = Runtime.getRuntime().exec(command);
		printLines(command + " stdout:", process.getInputStream());
		printLines(command + " stderr:", process.getErrorStream());
		process.waitFor();
		System.out.println(command + " exitValue() " + process.exitValue());
	}

	private static void printLines(String name, InputStream ins)
			throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			System.out.println(name + " " + line);
		}
	}

	public static void main(String[] args) {
		try {
			TransactionGenerator.start(new String[]{""});
			TemporalDataCleaner.start(new String[] {"","nodefailure.txt"});
			Apriori apriori =new Apriori();
			apriori.start(new String[]{"","nodefailure_cleaned.txt","0.7","0.7"});
			/*System.out.println("Working Directory = "
					+ System.getProperty("user.dir"));
			runCommand("javac src/edu/njit/cs/datamining/TransactionGenerator.java");
			runCommand("java -cp src/edu/njit/cs/datamining TransactionGenerator");
			runCommand("javac TemporalDataCleaner.java");
			runCommand("java edu.njit.cs.datamining.TemporalDataCleaner nodefailure.txt");
			runCommand("javac Apriori.java");
			runCommand("java edu.njit.cs.datamining.Apriori nodefailure_cleaned.txt 0.7 0.5");*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
