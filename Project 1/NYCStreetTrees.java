/**
 * @author Disha Gupta
 * 
 * This program analyzes a data file from NYCOpenData about registered trees throughout the city.
 * The user can enter the name of a specific tree and the program will give them data about that tree for the entire city and 
 * all five boroughs.
 */

package edu.nyu.cs.dg2703;

// Import necessary packages.
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class NYCStreetTrees {

	// Instantiate a new TreeList object to create an ArrayList of Tree objects that will be populated later.
	static TreeList myTreeList = new TreeList();
	
	/**
	 * Main method.
	 * Open's the file containing the tree data and splits it line by line. 
	 * Populates an ArrayList of type TreeList with Tree objects from each line in the file.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		// Check to see if the file was passed as a command line argument.
		// If it wasn't, print out an error message.
		if (args.length == 0) {
			System.err.println("Usage Error: No arguments were passed.");
		}
		// Check if the file is null.
		// Print out an error message.
		else if (args[0] == null) {
			System.err.println("Error: The file cannot be opened.");
		}
		// If it was, start analyzing the data.
		else if (args.length > 0) {
			
			// Store the file in a new Scanner variable.
			Scanner file = new Scanner(new File(args[0]));
			
			// Loop through the lines in the file.
			while (file.hasNext()) {
				// Store each line.
				String line = file.nextLine();
				// Add each line to a String ArrayList and call the splitCSVLine() with the line as a parameter to split up the data.
				ArrayList<String> splitLine = splitCSVLine(line);
				// Make sure to skip the first line of the text file (the first line is just the headers).
				// To do this, check if the first element of the first line is NOT equal to the String "tree_id" (if it is not, then 
				// the if statement will continue and Tree objects will be created).
				// Make sure to skip blank/empty lines by checking to see if the first element is null/empty.
				if ( (!splitLine.get(0).equals("tree_id")) && (splitLine.get(0) != null) && (!(splitLine.get(0).isEmpty())) ) {
					// Create a new Tree object for each line.
					// Grab the data from the specified columns from the data set (i.e.: tree_id is column 1 so that's 0).
					Tree myTree = new Tree( Integer.parseInt(splitLine.get(0)), Integer.parseInt(splitLine.get(3)), splitLine.get(6), 
									splitLine.get(7), splitLine.get(9), Integer.parseInt(splitLine.get(25)), splitLine.get(29), 
									Double.parseDouble(splitLine.get(39)), Double.parseDouble(splitLine.get(40)));
					// Add the Tree object to the myTreeList ArrayList that will store all of the Trees.
					myTreeList.add(myTree);
				}
			} // while loop
			
			// Set up a new Scanner that will be used to get the users input.
			Scanner input = new Scanner(System.in);
			// Ask the user to enter a tree name to learn more about it or quit the program.
			System.out.println("Enter the tree species to learn more about it: (\"quit\" to stop): ");
			// Store the users input.
			String userInput = input.nextLine();
			
			// Set up a while loop that terminates when the user enters "quit".
			while (!(userInput.equalsIgnoreCase("quit"))) {
				System.out.println();
				
				System.out.println("All matching species: ");
				// Set up an ArrayList that stores the matching tree species specified by the user.
				ArrayList<String> speciesMatches = myTreeList.getMatchingSpecies(userInput);
				// As long as the ArrayList has elements in it, loop through the list and print them out.
				if ( !(speciesMatches.size() == 0) ) {
					for (String match : speciesMatches) {
						System.out.println("\t" + " " + match);
					}
				}
				else {
					// If the ArrayList is empty, tell the user that no matches could be found.
					System.out.println("There are no records of " + userInput + " trees on NYC streets.");
					System.out.println();
					// Reprompt the user.
					System.out.println("Enter the tree species to learn more about it: (\"quit\" to stop): ");
					// Store their new input.
					userInput = input.nextLine();
					// Use "continue" to start the loop from the beginning and search for matching species.
					continue;
				}

				System.out.println();
				// Print out the popularity of the tree in the entire city and the five boroughs.
				System.out.println("Popularity in the city: ");
				
				// Set up a new DecimalFormat variable that will format the percentage of a specific tree in a specific borough to only have two decimal places.
				DecimalFormat df = new DecimalFormat("##.##");
			
				// Get the number of matching trees (using the users input) in NYC.
				int specificNYC = myTreeList.getCountByTreeSpecies(userInput);
				// Check to see if the myTreeList size is NOT 0.
				// This will prevent a divide by 0 error.
				if (myTreeList.size() != 0) {
					// Calculate the percentage of the specified tree in NYC.
					double calculationNYC = ( (((double)(myTreeList.getCountByTreeSpecies(userInput)))/(myTreeList.size())) * (100) );
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "NYC", ": ");
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificNYC) + " (" + String.format("%,d", myTreeList.size()) + ") " + df.format(calculationNYC) + "%");
				}
				// If myTreeList equals 0, print out an error message.
				else if (myTreeList.size() == 0) {
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "NYC", ": ");
					// Set the percentage of the specified tree in NYC to 0.
					double calculationNYC = 0;
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificNYC) + " (" + String.format("%,d", myTreeList.size()) + ") " + df.format(calculationNYC) + "%");
				}
				System.out.println();
			
				// Get the number of matching trees (using the users input) in Manhattan.
				int specificManhattan = myTreeList.getCountByTreeSpeciesBorough(userInput, "Manhattan");
				// Get the number of total trees in Manhattan.
				int totalManhattan = myTreeList.getCountByBorough("Manhattan");
				// Check to see if the totalManhattan size is NOT 0.
				// This will prevent a divide by 0 error.
				if (totalManhattan != 0) {
					// Calculate the percentage of the specified tree in Manhattan.
					double calculationManhattan = ( (((double)(specificManhattan))/totalManhattan) * (100) );
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Manhattan", ": ");
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificManhattan) + " (" + String.format("%,d", totalManhattan) + ") " + df.format(calculationManhattan) + "%");
				}	
				// If totalManhattan equals 0, print out an error message.
				else if (totalManhattan == 0) {
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Manhattan", ": ");
					// Set the percentage of the specified tree in Manhattan to 0.
					double calculationManhattan = 0;
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificManhattan) + " (" + String.format("%,d", totalManhattan) + ") " + df.format(calculationManhattan) + "%");
				}
				System.out.println();
				
				// Get the number of matching trees (using the users input) in the Bronx.
				int specificBronx = myTreeList.getCountByTreeSpeciesBorough(userInput, "Bronx");
				// Get the number of total trees in the Bronx.
				int totalBronx = myTreeList.getCountByBorough("Bronx");
				// Check to see if the totalBronx size is NOT 0.
				// This will prevent a divide by 0 error.
				if (totalBronx != 0) {
					// Calculate the percentage of the specified tree in the Bronx.
					double calculationBronx = ( (((double)(specificBronx))/totalBronx) * (100) );
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Bronx", ": ");
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificBronx) + " (" + String.format("%,d", totalBronx) + ") " + df.format(calculationBronx) + "%");
				}
				// If totalBronx equals 0, print out an error message.
				else if (totalBronx == 0) {
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Bronx", ": ");
					// Set the percentage of the specified tree in the Bronx to 0.
					double calculationBronx = 0;
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificBronx) + " (" + String.format("%,d", totalBronx) + ") " + df.format(calculationBronx) + "%");
				}
				System.out.println();
				
				// Get the number of matching trees (using the users input) in Brooklyn.
				int specificBrooklyn = myTreeList.getCountByTreeSpeciesBorough(userInput, "Brooklyn");
				// Get the number of total trees in Brooklyn.
				int totalBrooklyn = myTreeList.getCountByBorough("Brooklyn");
				// Check to see if the totalBrooklyn size is NOT 0.
				// This will prevent a divide by 0 error.
				if (totalBrooklyn != 0) {
					// Calculate the percentage of the specified tree in Brooklyn.
					double calculationBrooklyn = ( (((double)(specificBrooklyn))/totalBrooklyn) * (100) );
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Brooklyn", ": ");
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificBrooklyn) + " (" + String.format("%,d", totalBrooklyn) + ") " + df.format(calculationBrooklyn) + "%");
				}
				// If totalBrooklyn equals 0, print out an error message.
				else if (totalBrooklyn == 0) {
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Brooklyn", ": ");
					// Set the percentage of the specified tree in Brooklyn to 0.
					double calculationBrooklyn = 0;
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificBrooklyn) + " (" + String.format("%,d", totalBrooklyn) + ") " + df.format(calculationBrooklyn) + "%");
				}
				System.out.println();
				
				// Get the number of matching trees (using the users input) in Queens.
				int specificQueens = myTreeList.getCountByTreeSpeciesBorough(userInput, "Queens");
				// Get the number of total trees in Queens.
				int totalQueens = myTreeList.getCountByBorough("Queens");
				// Check to see if the totalQueens size is NOT 0.
				// This will prevent a divide by 0 error.
				if (totalQueens != 0) {
					// Calculate the percentage of the specified tree in Queens.
					double calculationQueens = ( (((double)(specificQueens))/totalQueens) * (100) );
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Queens", ": ");
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificQueens) + " (" + String.format("%,d", totalQueens) + ") " + df.format(calculationQueens) + "%");
				}	
				// If totalQueens equals 0, print out an error message.
				else if (totalQueens == 0) {
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Queens", ": ");
					// Set the percentage of the specified tree in Queens to 0.
					double calculationQueens = 0;
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificQueens) + " (" + String.format("%,d", totalQueens) + ") " + df.format(calculationQueens) + "%");
				}
				System.out.println();
				
				// Get the number of matching trees (using the users input) in Staten Island.
				int specificSI = myTreeList.getCountByTreeSpeciesBorough(userInput, "Staten Island");
				// Get the number of total trees in Staten Island.
				int totalSI = myTreeList.getCountByBorough("Staten Island");
				// Check to see if the totalSI size is NOT 0.
				// This will prevent a divide by 0 error.
				if (totalSI != 0) {
					// Calculate the percentage of the specified tree in Staten Island.
					double calculationSI = ( (((double)(specificSI))/totalSI) * (100) );
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Staten Island", ": ");
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificSI) + " (" + String.format("%,d", totalSI) + ") " + df.format(calculationSI) + "%");
				}
				// If totalSI equals 0, print out an error message.
				else if (totalSI == 0) {
					// Format the output using printf so the data prints out in column format.
					System.out.printf("\t %-18s %s", "Staten Island", ": ");
					// Set the percentage of the specified tree in Staten Island to 0.
					double calculationSI = 0;
					// Format the integers using printf and String.format to get commas in the numbers and the right number of significant figures for the percentage.
					System.out.print(String.format("%,d", specificSI) + " (" + String.format("%,d", totalSI) + ") " + df.format(calculationSI) + "%");
				}
				System.out.println();
				
				System.out.println();
			
				// Reprompt the user.
				System.out.println("Enter the tree species to learn more about it: (\"quit\" to stop): ");
				// Store their new input.
				userInput = input.nextLine();
			}
			
			// Close the file scanner.
			file.close();
			// Close the input scanner.
			input.close();
			
		} // else block
		
	} // main method
	
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround  multi-word entries that may contain commas). 
	 * 
	 * @param textLine  line of text to be parsed
	 * @return an ArrayList object containing all individual entries/tokens
	 *         found on the line.
	 */
	public static ArrayList<String> splitCSVLine(String textLine) {
		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;

		// Iterate over all of the characters in the textLine.
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			
			// Handle smart quotes as well as regular quotes.
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') { 
				// Change insideQuotes flag when nextChar is a quote.
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false; 
				}
				else {
					insideQuotes = true; 
					insideEntry = true; 
				}
			}
			
			else if (Character.isWhitespace(nextChar)) {
				if  ( insideQuotes || insideEntry ) {
					// Add it to the current entry.
					nextWord.append( nextChar );
				}
				// Skip all spaces between entries.
				else  {
					continue;
				}
			}
			
			else if ( nextChar == ',') {
				// Comma inside an entry
				if (insideQuotes)
					nextWord.append(nextChar);
				// End of entry found
				else {
					insideEntry = false; 
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			}
			
			else {
				// Add all other characters to the nextWord.
				nextWord.append(nextChar);
				insideEntry = true; 
			}

		}
		
		// Add the last word (assuming not empty).
		// Trim the white space before adding to the list.
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
		
	} // splitCSVLine method
	
} // class
