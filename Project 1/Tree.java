/**
 * @author Disha Gupta
 * 
 * This program analyzes a data file from NYCOpenData about registered trees throughout the city.
 * The user can enter the name of a specific tree and the program will give them data about that tree for the entire city and 
 * all five boroughs.
 */

package edu.nyu.cs.dg2703;

public class Tree implements Comparable<Tree> {
	
	// Create all of the necessary data fields that a Tree object will need.
	private int tree_id;
	private int tree_dbh;
	private String status;
	private String health;
	private String spc_common;
	private int zipcode;
	private String boroname;
	private double x_sp;
	private double y_sp;
	
	/**
	 * Tree Constructor.
	 * This constructor takes the tree's data fields (id, diam, status, health, spc, zip, boro, x, y) as parameters and validates them.
	 * If the parameters are valid then we assign the values to the data fields.
	 * 
	 * @param id
	 * @param diam
	 * @param status
	 * @param health
	 * @param spc
	 * @param zip
	 * @param boro
	 * @param x
	 * @param y
	 * @throws IllegalArgumentException
	 */
	public Tree (int id, int diam, String status, String health, String spc, 
				int zip, String boro, double x, double y) throws IllegalArgumentException {
		// tree id exception
		if (id < 0.0)
			throw new IllegalArgumentException("The tree id number must be a non-negative integer.");
		// Set the tree's id if it is valid.
		else 
			this.tree_id = id;
		// tree diameter exception
		if (diam < 0.0)
			throw new IllegalArgumentException("The tree diameter must be a non-negative integer.");
		// Set the tree's diameter if it is valid.
		else
			this.tree_dbh = diam;
		// Set the tree's status if it is valid.
		if ( (status.equalsIgnoreCase("Alive")) || (status.equalsIgnoreCase("Dead")) || (status.equalsIgnoreCase("Stump")) 
			 || (status.isEmpty()) || (status == null) )
			this.status = status;
		// tree status exception
		else
			throw new IllegalArgumentException("Not a valid tree status.");
		// Set the tree's health if it is valid.
		if ( (health.equalsIgnoreCase("Good")) || (health.equalsIgnoreCase("Fair")) || (health.equalsIgnoreCase("Poor")) 
				 || (health.isEmpty()) || (health == null) )
			this.health = health;
		// tree health exception
		else 
			throw new IllegalArgumentException ("Not a valid tree health.");
		// tree species exception
		if (spc == null )
			throw new IllegalArgumentException("Not a valid tree species.");
		// Set the tree's species if it is valid.
		else
			this.spc_common = spc;
		// Check to see if the zip code is 5 characters long. 
		// If it's not, add leading 0's to the zip code to fix it.
		String stringZip = String.valueOf(zip);
		if (stringZip.length() != 5) {
			stringZip = ("00000" + stringZip).substring(stringZip.length());
			this.zipcode = Integer.parseInt(stringZip);
		}
		// Set the tree's zip code if it is valid.
		else 
			this.zipcode = Integer.parseInt(stringZip);
		// Set the tree's borough if it is valid.
		if ( (boro.equalsIgnoreCase("Manhattan")) || (boro.equalsIgnoreCase("Bronx")) || 
			 (boro.equalsIgnoreCase("Queens")) || (boro.equalsIgnoreCase("Staten Island")) || (boro.equalsIgnoreCase("Brooklyn")) )
			this.boroname = boro;
		// tree borough exception
		else 
			throw new IllegalArgumentException ("Not a valid borough name.");
		// x exception
		if (x != ((double)x) )
			throw new IllegalArgumentException("Not a valid value for x.");
		// Set the x value of the tree if it is valid.
		else
			this.x_sp = x;
		// y exception
		if (y != ((double)y) )
			throw new IllegalArgumentException("Not a valid value for y.");
		// Set the y value of the tree if it is valid.
		else 
			this.y_sp = y;
		
	} // tree constructor
	
	/**
	 * Overriding the Object's class' equals method.
	 * This method compares two Tree objects and checks to see if their ID and species name are the same.
	 * If the ID's are the same but the species names are not, an illegal argument exception is thrown.
	 * 
	 * @param obj
	 * @return boolean value 
	 * @throws IllegalArgumentException
	 */
	@Override
	public boolean equals(Object obj) throws IllegalArgumentException {
		// Check to see if obj is an instance of Tree.
		if (obj instanceof Tree) {
			// Cast the obj of type Object to type Tree.
			// This way we can use the getter methods on obj.
			Tree tree = (Tree) obj;
			
			// Check to see if the tree's ID's and species names match.
			// If they do return true.
			if ( (this.getID(this) == (getID(tree))) && (this.getSpecies(this).equalsIgnoreCase(getSpecies(tree))) ) {
				return true;
			}
			// If the tree's have the same ID but different species names throw an illegal argument exception error.
			if ( (this.getID(this) == getID(tree) ) && (!(this.getSpecies(this).equalsIgnoreCase(getSpecies(tree)))) ) {
				throw new IllegalArgumentException("These tree's have the same ID but different species names which is illegal.");
			}
		}
		// Else return false.
		return false;
	} // equals method
	
	/**
	 * This is a getter that gets the tree's id.
	 * 
	 * @param tree
	 * @return Tree object's id
	 */
	public int getID(Tree tree) {
		return this.tree_id;
	}
	
	/**
	 * This is a getter that gets the tree's species name.
	 * 
	 * @param tree
	 * @return Tree object's species name
	 */
	public String getSpecies(Tree tree) {
		return this.spc_common;
	} 
	
	/**
	 * This is a getter that gets the tree's borough.
	 * 
	 * @param tree
	 * @return Tree object's borough
	 */
	public String getBorough(Tree Tree) {
		return this.boroname;
	}

	/**
	 * Overriding the compareTo method.
	 * This method compares two species names and then two id's.
	 * 
	 * @param tree
	 * @return integer
	 */
	@Override
	public int compareTo(Tree tree) {
		int speciesDifference = this.spc_common.compareToIgnoreCase(tree.spc_common);
		if (speciesDifference != 0) {
			return speciesDifference;
		}
		if (speciesDifference == 0) {
			int idDifference = Integer.compare(this.tree_id, tree.tree_id);
			if (idDifference != 0) {
				return idDifference;
			}
		}
		return 0;
	} // compareTo method
	
	/**
	 * This method overrides the toString method.
	 * It takes all of the Tree's data fields (id, diam, status, health, spc, zip, boro, x, y) and returns them as a String.
	 * 
	 * @return String representation of all of the Tree object's data fields.
	 */
	@Override
	public String toString() {
		return "Tree ID: " + this.tree_id + " Tree Diameter: " + this.tree_dbh + " Tree Status: " + this.status + 
			   " Tree Health: " + this.health + " Tree Species: " + this.spc_common + " Tree Zip Code: " + String.format("%05d", this.zipcode) + 
			   " Tree Borough: " + this.boroname + " Tree x_sp: " + this.x_sp + " Tree y_sp: " + this.y_sp;
	}
	
} // class 