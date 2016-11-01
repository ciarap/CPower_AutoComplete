/**
 * @author Ciara Power
 * @version 01/11/16
 */
package models;

public class Term implements Comparable {

	private double weight;
	private String word;

	public Term(String weight, String word)throws IllegalArgumentException {
		if (weight==null || word==null){  

			throw new NullPointerException("Strings are null");
		}
		else{double weightDouble=Double.parseDouble(weight.trim());  // change weight string to double , trim to eliminate spaces
		if(weightDouble>=0) 
			this.weight = weightDouble;
		else {   // if weight below 0, then set to 0
			this.weight=0.0;
		}
		this.word = word;
		}

	}
	/**
	 * getWeight() - This method is a getter for the weight field.
	 * @return double weight of term
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * setWeight() - This method is a setter for the weight field.
	 * 
	 */
	public void setWeight(double weight) {
		if (weight<0){  
			throw new IllegalArgumentException("Weight is negative");
		}
		else if(weight>0){   // if weight is above 0 then set it
			this.weight = weight;
		}
	}
	
	/**
	 * getWord() - This method is a getter for the word field.
	 * @return string word of term
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * setWord() - This method is a setter for the word field.
	 * 
	 */
	public void setWord(String word) {
		if(word==null || word==""){
			throw new NullPointerException("String is null");
		}
		else{
			this.word=word;
		}
	}
	
	/**
	 * toString() - This method converts a term to a string
	 * @return string of term fields
	 */
	@Override
	public String toString() {
		return "Term [weight=" + weight + ", word=" + word + "]";
	}
	
	/**
	 * compareTo() - This method is a comparable method for two terms
	 * @return integer - either negative,0 or positive
	 */
	@Override
	public int compareTo(Object that) {
		return (int) ((int) ((Term) that).getWeight()-this.weight);   //compares both by weight
	}





}
