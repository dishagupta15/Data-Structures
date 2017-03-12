/**
 * @author Disha Gupta
 * 
 * This program analyzes a data file from NYCOpenData about registered trees throughout the city.
 * The user can enter the name of a specific tree and the program will give them data about that tree for the entire city and 
 * all five boroughs.
 */

package edu.nyu.cs.dg2703;

// Import necessary packages.
import java.util.ArrayList;

public class TreeList extends ArrayList<Tree> {

	/**
	 * TreeList constructor.
	 * Initializes a new empty ArrayList that will hold Tree objects.
	 */
	public TreeList() {
		// The constructor is left empty because when it is called, a new ArrayList of type Tree will be created (since this class
		// inherits from ArrayList<Tree>).
	}
	
	/**
	 * This getter gets the size of the ArrayList containing the Tree objects.
	 * 
	 * @return integer
	 */
	public int getTotalNumberOfTrees() {
		// Return the size of the ArrayList.
		return this.size();
	}
	
	/**
	 * Getter method that loops through an ArrayList of Tree objects and searches for the tree's name to see if their is 
	 * a species match and counts them (i.e.: if speciesName is "oak" a match would be made if the tree's species is "red oak").
	 * 
	 * @param speciesName
	 * @return integer
	 */
	public int getCountByTreeSpecies(String speciesName) {
		// Set up a counter to keep track of the number of species names that match.
		int counter = 0;
		// Loop through the ArrayList and check to see if there are any matches.
		for (int i = 0; i < this.size(); i++) {
			// Get the element in the ArrayList and store it as a Tree object.
			Tree tree = this.get(i);
			// Store the tree's species name by using the getTreeSpcCommon method made in the Tree class.
			String name = tree.getSpecies(tree);
			// Convert both strings to lower case and check to see if speciesName is contained in the tree name.
			if ( name.toLowerCase().contains(speciesName.toLowerCase()) ) {
				// Increment the counter for every match.
				counter++;
			}
		}
		// If the method is called with a non-existent species, return 0.
		if (counter == 0) {
			return 0;
		}
		// Else, return the counter.
		return counter;
	}
	
	/**
	 * Getter method that returns the number of trees in the specified borough.
	 * 
	 * @param boroName
	 * @return integer
	 */
	public int getCountByBorough(String boroName) {
		// Set up a counter to keep track of the number of trees in the borough name specified.
		int counter = 0;
		// Loop through the ArrayList and check to see if there are any matches.
		for (int i = 0; i < this.size(); i++) {
			// Get the element in the ArrayList and store it as a Tree object.
			Tree tree = this.get(i);
			// Store the tree's borough by using the getTreeBoroguh method made in the Tree class.
			String borough = tree.getBorough(tree);
			// Check if the boroughs match using equalsIgnoreCase().
			if ( borough.equalsIgnoreCase(boroName) ) {
				// Increment the counter for every match.
				counter++;
			}
		}
		// If the method is called with a non-existent borough name, return 0.
		if (counter == 0) {
			return 0;
		}
		// Else, return the counter.
		return counter;
	}
	
	/**
	 * Getter that that returns the number of Tree objects in the list whose species matches the speciesName specified by the first 
	 * parameter and which are located in the borough specified by the second parameter.
	 * 
	 * @param speciesName
	 * @param boroName
	 * @return integer
	 */
	public int getCountByTreeSpeciesBorough(String speciesName, String boroName) {
		// Set up a counter to keep track of the number of specified trees in the borough name specified.
		int counter = 0;
		for (int i = 0; i < this.size(); i++) {
			// Get the element in the ArrayList and store it as a Tree object.
			Tree tree = this.get(i);
			// Store the tree's species name by using the getTreeSpcCommon method made in the Tree class.
			String name = tree.getSpecies(tree);
			// Store the tree's borough by using the getTreeBoroguh method made in the Tree class.
			String borough = tree.getBorough(tree);
			// Convert both strings to lower case and check to see if speciesName is contained in the tree name.
			if ( (name.toLowerCase().contains(speciesName.toLowerCase())) && (borough.equalsIgnoreCase(boroName)) ) {
				// Increment the counter for every match.
				counter++;
			}
		}
		// If the method is called with a non-existent species or borough name, return 0.
		if (counter == 0) {
			return 0;
		}
		// Else, return the counter.
		return counter;
	}
	
	/**
	 * Getter method that loops through the ArrayList of Tree objects and returns an ArrayList of Strings that contain 
	 * tree's whose name contained the specified speciesName parameter.
	 * 
	 * @param speciesName
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMatchingSpecies(String speciesName) {
		// Create a new ArrayList of type String to store the matches.
		ArrayList<String> speciesMatches = new ArrayList<String>();	
		// Loop through the ArrayList and check to see if there are any matches.
		for (int i = 0; i < this.size(); i++) {
			// Get the element in the ArrayList and store it as a Tree object.
			Tree tree = this.get(i);
			// Store the tree's species name by using the getTreeSpcCommon method made in the Tree class.
			String name = tree.getSpecies(tree);
			// Convert both strings to lower case and check to see if speciesName is contained in the tree name.
			if ( name.toLowerCase().contains(speciesName.toLowerCase()) ) {
				// If the match is not already in the ArrayList, add it to it.
				if ( !(speciesMatches.contains(name)) ) {
					speciesMatches.add(name);
				}
			}
		}
		// Return the ArrayList.
		return speciesMatches;
	}
		
	/**
	 * Overriding the toString method.
	 * Returns all of the Tree objects stored in the ArrayList by looping through list and adding each element to a StringBuffer.
	 * 
	 * @return ArrayList<Tree>
	 */
	@Override
	public String toString() {
		// Set up a StringBuffer to store the information about the tree objects.
		StringBuffer treeObjects = new StringBuffer("");
		// Loop through elements in the ArrayList.
		for (int i = 0; i < this.size(); i++) {
			// Append them to the StringBuffer.
			treeObjects.append(this.get(i));
		}
		// Return all tree objects.
		return "Tree Objects: " + treeObjects;
	}
	
}
