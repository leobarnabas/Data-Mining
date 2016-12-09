package edu.njit.cs.datamining;

/**
 * Main file that runs the project
 * @author lm287
 *
 */
public class TemporalAprioriRunner {
	public static void main(String[] args) {
		try {
			// Generates transactions or the dataset
			TransactionGenerator.start(new String[] { "" });
			// Filters the dataset usinf start and end time, time format is mm-dd-yyy hh:mm:ss
			TemporalDataCleaner.start(new String[] { "", "nodefailure.txt", "06-10-2016 00:00:05", "10-10-2016 10:05:04" });
			// Runs apriori algorithm on the filtered dataset
			Apriori.start(new String[] { "", "nodefailure_cleaned.txt", "0.7",
					"0.7" });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
