/**
* @author Ciara Power
* @version 01/11/16
*/
package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BruteAutoComplete  implements AutoComplete {

	private List<Term> terms=new ArrayList<Term>();
	private  Scanner input= new Scanner(System.in);
	
	public BruteAutoComplete()  {
		
	}
	
	/**
	 * getTerms() - This method is a getter for the terms field.
	 * @return List of Terms
	 */
	public List<Term> getTerms() {
		return terms;
	}
	
	/**
	 * setTerms() - This method is a setter for the terms field.
	 * 
	 */
	public void setTerms(List<Term> terms) {
		if (terms==null) {  //throw exception if argument is null
	        throw new NullPointerException("Terms array is null");
	    }
		if (terms.size()!=0){    // if the argument list isn't empty, set the terms field as the argument
			this.terms = terms;
		}
	}

	/**
	 * bruteRun() - This method runs the program for the bruteforce AutoComplete method.
	 * 
	 */
	public void bruteRun(int suggestionNo) throws Exception{
		Iterator<String> matches;    //local variable for the matches
		int option=mainMenu();   //local variable for menu choice is set to the value returned from mainMenu() method
		String prefix="";
		while(option!=2){   //while EXIT wasnt chosen
			switch (option){
			case 1: 
				System.out.println("Please enter a prefix to search for:"  );
				input.nextLine();
				prefix=input.nextLine().toLowerCase();   // set input to lower case
				while(prefix.equals("") || prefix==null){   // if nothing is entered 
					System.out.println("Please enter a prefix!");   //loops until prefix entered
					System.out.print("==>>");
					prefix=input.nextLine().toLowerCase();   
				}
				matches=matches(prefix,suggestionNo).iterator();   //calls matches() method to get all matches and put into local variable iterator
				int counter=1;   // counter for printing out matches
				if (matches.hasNext()) System.out.println("Matches found for chosen prefix \""+prefix+"\" ..."); // if there are matches
				while(matches.hasNext()){   // while another match in data collection 
					System.out.println(counter+") "+matches.next());   //prints each match as it loops around
					counter++;
					}
				System.out.println("---------\nBest Match: "+bestMatch(prefix));    //prints the return value of bestMatch() method
				System.out.println("\n    ---Press any key to continue---");
				input.nextLine();
				option = mainMenu();   //loops back to mainMenu
				break;
			case 2: System.exit(0);    
			input.close();
			break;
			}
		}
		
	}

	/**
	 * mainMenu() - This method runs the menu for the brute auto complete program, giving a choice to search for prefix or exit
	 * @return an integer value for the user menu choice
	 */
	public int mainMenu(){
		System.out.println(" BRUTE FORCE AUTOCOMPLETE PROGRAM \n---------------------------");
		System.out.println(" Please choose an option: \n  1) Search a prefix\n  2) Exit to main menu");
		int option;    //local variable for choice
		try {
			option = input.nextInt();
			while (option < 1 || option > 2) {   // must get option in the menu range
				System.out.println("Invalid choice,please choose again!");
				System.out.print("==>>");
				option = input.nextInt();   
			}
		} catch (Exception e) {      // if input other than integer is entered
			System.out.println("---Invalid choice,please choose again!---");
			System.out.println("    ---Press any key to continue---");
			input.nextLine();
			input.nextLine();
			option = mainMenu();    //recursion on mainMenu method
		}
		return option;
	}

	
	/**
	 * weightOf() - This method searches for a term in the list , and gets its associated weight
	 * @return a double for the weight of the term
	 */
	@Override
	public double weightOf(String term) {
		if (term==null) {
	        throw new NullPointerException("String is null");
	    }
		double weight=0.0;
		for (Term termA:terms){   // run through each term in list
			if (termA.getWord().compareTo(term)==0)   // if the term word is equal to the wanted term 
					{
				weight= termA.getWeight();   //gets the associated weight and puts in local variable weight
			}
		}
		return weight;   //returns 0 if no found word 
	}

	/**
	 * bestMatch() - This method searches for the best matched  term for a given term, based on highest weight
	 * @return a String for the term word
	 */
	@Override
	public String bestMatch(String prefix) {
		if (prefix==null) {
	        throw new NullPointerException("Term is null");
	    }
		String bestmatch="none";   //default if none found
		for (Term term: terms){
			if (term.getWord().startsWith(prefix)){   // terms are already sorted by weight high to low- sorted in URL ,so just get first match and its the highest weight
				bestmatch=term.getWord();
			return bestmatch;   //returns the first word found rather than keep the loop going
			}
		}
		return bestmatch;	
		
	}
	
	/**
	 * matches() - This method searches for the k matches for a given prefix, based on highest weight
	 * @return an iterable of type String for the matches
	 */
	@Override
	public Iterable<String> matches(String prefix, int k){
		 if (prefix==null) {
		        throw new NullPointerException("Term is null");
		    }
		 if (k < 0) {
		        throw new IllegalArgumentException("Number of suggestions should be positive");
		    }
		List<String> matches=new ArrayList<String>();
		for (Term term: terms){   //iterates through and compares every term
			if (term.getWord().startsWith(prefix)){
				matches.add(term.getWord());      //adds word if there is a match
			}
		}

		if(matches.size()>k){     // if more matches than we want, cut it to a sublist
			matches=matches.subList(0, k);
		}
		else if (matches.size()==0){    // if no matches were found
			System.out.println("There are no matches for this prefix");
			
		}
		Iterable<String> iterMatches=matches;   //convert to iterable 
		return iterMatches;
		
	}
	
	/**
	 * createTerm() - This method creates terms and adds them to the terms list
	 * 
	 */
	public void createTerm(String weight,String word){
		if (weight==null || word==null) {
	        throw new NullPointerException("Strings are null");
	    }
		boolean same=false;  // boolean for if any other term is the same 
		for(Term termA:terms){
			if(termA.getWord()==word){  // if word already in list
				same=true;
			}
		}
		if(!same){  // if unique word, then create and add to list
				Term term= new Term(weight,word);
				terms.add(term);	
		}
	}

	
}
