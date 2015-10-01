import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
/*
 * Purpose: Design and Analysis of Algorithms Assignment 2 Problem 1
 * Status: Complete and thoroughly Incomplete
 * Last update: 03/7/15
 * Submitted:  03/7/15
 * Comment: test suite and sample run attached
 * @author: Alex Greco
 * @version: 2015.03.07
 */
public class Driver {

	private static BufferedReader stdin = new BufferedReader(
			new InputStreamReader(System.in));
	private static boolean run = true;
	private static int[] maximum;
	private static int[] maxJobNum;
	private static int[] temp;
	private static int[][] matrix;
	private static int jobCount = 0;
	private static int n = 0;
	private static int solutionsFound = 0;
	private static int totalMax = 0;

	/**
	 * main method that calls run to excute the program
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		run();

	}

	/**
	 * drives the program and asks for user input When the size of the matrix is
	 * determined, call method to populate a 2d array.
	 * 
	 * @throws IOException
	 */
	public static void run() throws IOException {
		while (run == true) {
			System.out.println("Enter matrix size, or type 'exit' and submit:");
			String userInput = stdin.readLine();
			if (userInput == "exit") {
				run = false;
			} else {
				n = Integer.parseInt(userInput);
				populateMatrix();
			}
		}

	}// end run()

	/**
	 * This method will take input in and populate a 2d matrix
	 * 
	 * @param n
	 *            how big the n x n matrix should be
	 * @throws IOException
	 */
	public static void populateMatrix() throws IOException {
		matrix = new int[n][n];
		int[] tempPop = new int[(n * n)];
		temp = new int[n];
		maximum = new int[n];
		maxJobNum = new int[n];
		// until you input everything, grab the next thing with the buffered
		// reader
		for (int i = 0; i < (n * n); i++) {
			String input = stdin.readLine();
			int num = Integer.parseInt(input);
			tempPop[i] = num;
		}
		int loopCount = 0;
		for (int j = 0; j < n; j++) {
			for (int k = 0; k < n; k++) {
				matrix[j][k] = tempPop[loopCount];
				loopCount++;
			}
		}
		printMatrix();
		checkJobs();
		printCheapest();
		System.out.println("maximum Job Cost: " + totalMax);

	}// end populateMatrix

	/**
	 * simply prints a 2d representation of the matrix array
	 */
	public static void printMatrix() {
		for (int j = 0; j < n; j++) {
			for (int k = 0; k < n; k++) {
				System.out.print(matrix[j][k] + " ");
			}
			System.out.println("");
		}
	}

	/**
	 * prints all the values from the maximum array
	 */
	public static void printCheapest() {
		for (int i = 0; i < n; i++) {
			System.out.println("Person " + i + " assigned job "+ maxJobNum[i]);
		}
	}

	/**
	 * jobCount is basically a marker for the rows, and i is a marker for the
	 * columns in that row This method is complicated but basically just checks
	 * to see if it should get iterating through the rows, or back track and try
	 * new values.
	 */
	public static void checkJobs() {
		clearTemp();
		for (int col = 0; col < n; col++) {
			// check if the current row has a valid spot
			if (checkValidSpot(col)) {
				// assign the
				temp[jobCount] = col;
				// jobCount++;
				if (jobCount == n - 1) {
					// all n jobs are assigned, so compare to the current lowest
					compareCosts();
					solutionsFound++;
					if (col == n - 1 && jobCount == n - 1) {
						jobCount--;
						col = temp[jobCount];
					}
				} else {
					jobCount++;
					col = -1;
				}
			} else {
				if (col == n - 1) {
					temp[jobCount] = -1;
					jobCount--;
					// temp[jobCount] = -1;
					// if the current job were looking at is at the end of the
					// row
					if (temp[jobCount] == n - 1) {
						if (jobCount == 0 && col == n - 1) {
							// the last possible top row is trying to back track
							// end the search here
							break;
						} else {
							jobCount--;
						}
					}
					col = temp[jobCount];
				}
			}
		}
		System.out.println("num Solutions: " + solutionsFound);
		solutionsFound = 0;
	}

	/**
	 * Check to see if there are any chosen jobs in the rows above the current
	 * one
	 * 
	 * @param i
	 *            the position of the job in question
	 * @return false if there is a job picked in that column
	 */
	public static boolean checkValidSpot(int i) {
		for (int j = 0; j <= jobCount; j++) {
			if (i == temp[j]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * if it is the first solution, set it to maximum by default. For every
	 * solution, check against maximum to see if it is cheaper
	 */
	public static void compareCosts() {
		if (solutionsFound == 0) {
			for (int i = 0; i < n; i++) {
				maximum[i] = temp[i];
				maxJobNum[i]=temp[i];
			}
			for (int j = 0; j < n; j++) {
				maximum[j] = matrix[j][maximum[j]];
			}
			for (int k = 0; k < n; k++) {
				totalMax += maximum[k];
			}
		} else {
			int tempCost = 0;
			for(int x = 0; x < n; x++){
				tempCost += matrix[x][temp[x]];
			}
			if(tempCost > totalMax){
				for (int i = 0; i < n; i++) {
					maximum[i] = temp[i];
					maxJobNum[i]=temp[i];
				}
				for (int j = 0; j < n; j++) {
					maximum[j] = matrix[j][maximum[j]];
				}
				totalMax = 0;
				for (int k = 0; k < n; k++) {
					totalMax += maximum[k];
				}
			}
		}
	}

	/**
	 * Set the temp array so the first value does not have a conflict. It makes
	 * every entry -1 so that any value of the rows that are zero dont treat it
	 * like its a legit value
	 */
	public static void clearTemp() {
		temp = new int[n];
		// set all temp entries to -1 so it wont have a weird conflict
		for (int z = 0; z < n; z++) {
			temp[z] = -1;
		}
	}
}

