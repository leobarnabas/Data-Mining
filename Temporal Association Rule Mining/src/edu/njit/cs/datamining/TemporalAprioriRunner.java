package edu.njit.cs.datamining;

public class TemporalAprioriRunner {
	public static void main(String[] args) {
		try {
			TransactionGenerator.start(new String[] { "" });
			TemporalDataCleaner.start(new String[] { "", "nodefailure.txt", "06-10-2016 00:00:05", "10-10-2016 10:05:04" });
			Apriori.start(new String[] { "", "nodefailure_cleaned.txt", "0.7",
					"0.7" });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
