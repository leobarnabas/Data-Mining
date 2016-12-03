package edu.njit.cs.datamining;

public class TemporalAprioriRunner {

	private static void runCommand(String command) throws Exception {
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();
	}

	public static void main(String[] args) {
		try {
			runCommand("java edu.njit.cs.datamining.TransactionGenerator");
			runCommand("java edu.njit.cs.datamining.TemporalDataCleaner");
			runCommand("java edu.njit.cs.datamining.Apriori");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
