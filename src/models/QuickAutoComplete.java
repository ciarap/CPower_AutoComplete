/**
* @author Ciara Power
* @version 01/11/16
*/
package models;

import java.util.*;

public class QuickAutoComplete implements AutoComplete{

	private List<Term> terms=new ArrayList<Term>();
	private  Scanner input= new Scanner(System.in);

	public QuickAutoComplete() throws Exception {

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
		        throw new NullPointerException("Terms is null");
		    }
		if (terms.size()!=0){ // if the argument list isn't empty, set the terms field as the argument
			this.terms = terms;
		}
	}

	/**
	 * quickRun() - This method runs the program for the Quick AutoComplete method.
	 * 
	 */
	public void quickRun(int suggestionNo) throws Exception{
		int option=mainMenu(); //local variable for menu choice is set to the value returned from mainMenu() method
		String prefix="";
		while(option!=2){  //while EXIT wasnt chosen
			sortAlphabetically(terms); //sort the terms alphabetically in method 
			switch (option){
			case 1: 
				Iterator<String> matches = null;
				System.out.println("Please enter a prefix to search for:"  );
				input.nextLine();
				prefix=input.nextLine().toLowerCase(); // set input to lower case
				while(prefix.equals("") || prefix==null){   // if nothing is entered 
					System.out.println("Please enter a prefix!"); //loops until prefix entered  
					System.out.print("==>>");
					prefix=input.nextLine().toLowerCase();
				}
				if (matches(prefix,suggestionNo)!=null){
				matches=matches(prefix,suggestionNo).iterator(); //calls matches() method to get all matches and put into local variable iterator
				int counter=1; // counter for printing out matches
				if (matches.hasNext()) System.out.println("Matches found for chosen prefix \""+prefix+"\" ..."); // if there are matches
				while(matches.hasNext()){  // while another match in data collection
				System.out.println(counter+") "+matches.next()); //prints each match as it loops around
					counter++;
				}
				}
				else{   //if matches is null
					System.out.println("There are no matches for this prefix");
				}
				System.out.println("---------\nBest Match: "+bestMatch(prefix));    //prints the return value of bestMatch() method
				System.out.println("\n    ---Press any key to continue---");
				input.nextLine();
				option = mainMenu();  //loops back to mainMenu
				break;
			case 2: System.exit(0);
			input.close();
			break;
			}
		}
	}
	
	/**
	 * mainMenu() - This method runs the menu for the quick auto complete program, giving a choice to search for prefix or exit
	 * @return an integer value for the user menu choice
	 */
	public int mainMenu(){
		System.out.println(" QUICK AUTOCOMPLETE PROGRAM \n---------------------------");
		System.out.println(" Please choose an option: \n  1) Search a prefix\n  2) Exit to main menu");
		int option;  //local variable for choice
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
			option = mainMenu();  //recursion on mainMenu method
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
		        throw new NullPointerException("Term is null");
		    }
		double weight=0.0;
		List<Term> termList=binarySearchForBoxOfTerms(term);  // get matching term list using binary search method
		if(termList!=null){
		for (Term termA:termList){
			if (termA.getWord().compareTo(term)==0)  // for each matching term 
			{
				weight= termA.getWeight();
			}
		}
		}
		return weight;		
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
		
		if (matches(prefix,1)==null){   //if matches is empty 
			return "none";
		}
		else{
		Iterator<String> itermatches=matches(prefix,1).iterator();
		return itermatches.next();  //returns first element of iterator
		}

	}

	/**
	 * matches() - This method searches for the k matches for a given prefix, based on highest weight
	 * @return an iterable of type String for the matches
	 */
	@Override
	public Iterable<String> matches(String prefix, int k) {
		 if (prefix==null) {
		        throw new NullPointerException("Term is null");
		    }
		  if (k < 0) {
		        throw new IllegalArgumentException("Number of suggestions should be positive");
		    }
		List<Term> matches=binarySearchForBoxOfTerms(prefix);  //uses binary search method to get list of matches
		if(matches!=null){
		sortWeight(matches);  //calls method to sort based on weight high to low
		if(matches.size()>k ){  // if more matches than we want, cut it to a sublist
			matches=matches.subList(0, k);
		}
		List<String> matchesStrings=new ArrayList<String>();
		for(Term term:matches){
			matchesStrings.add(term.getWord());   //adds each terms word to a list of string 
		}
		Iterable<String> matchesIter=matchesStrings;
		return matchesIter;
		}
		else{
			return null;
		}
	}

	
	/**
	 * sortWeight() - This method sorts a list by selection sort based on numerical weights
	 *
	 */
	public  void sortWeight(List<Term> terms2){  //selection sort
		if (terms2==null) {
	        throw new NullPointerException("Terms array is null");
	    }
		int N=terms2.size();
		for (int i=0;i<N;i++){
			int min = i;
			for (int j=i+1;j<N;j++)
				if (terms2.get(min).getWeight()-terms2.get(j).getWeight()<0)//if first is smaller then min is set to j
					min=j;
			exch(terms2,i,min);   //exch method called

		}
	}

	/**
	 * sortAlphabetically() - This method sorts a list by selection sort based on alphabetical order
	 * 
	 */
	public  void sortAlphabetically(List<Term> terms2){  
		if (terms2==null) {
	        throw new NullPointerException("Terms array is null");
	    }
		int N=terms2.size();
		for (int i=0;i<N;i++){
			int min = i;
			for (int j=i+1;j<N;j++)
				if (less(terms2.get(j).getWord(),terms2.get(min).getWord())) //calls less() method to test if one less than the other
					min=j;
			exch(terms2,i,min); //calls exch method to exchange terms

		}
	}

	/**
	 * less() - This method compares the two arguments to see if one is less than the other
	 * @returns boolean if comparison<0
	 */
	private  boolean less(String v,String w){
	if (v==null || w==null) {
        throw new NullPointerException("Arguments are null");
    }
	 return v.compareTo(w)<0;   
	 }

	/**
	 * exch() - This method swaps the two terms in the list
	 * 
	 */
	private  void exch(List<Term> terms2,int i,int j){
		if (terms2==null) {
	        throw new NullPointerException("Terms array is null");
	    }
		Term swap=terms2.get(i);   //temp variable
		terms2.set(i,terms2.get(j));   
		terms2.set(j,swap);
	}


	public List<Term> binarySearchForBoxOfTerms(String key) 
	{
		if (key==null) {
	        throw new NullPointerException("Prefix is null");
	    }
		int low = 0;
		int high = terms.size() - 1;

		while(high >= low) {
			int middle = (low + high) / 2;
			if(terms.get(middle).getWord().startsWith(key)) {
				int findStartBoxIndex=middle;
				while(terms.get(findStartBoxIndex).getWord().startsWith(key)){
					findStartBoxIndex-=1;

				}
				int findEndBoxIndex=middle;
				while(terms.get(findEndBoxIndex).getWord().startsWith(key)){
					findEndBoxIndex+=1;

				}
				return terms.subList(findStartBoxIndex+1,findEndBoxIndex);
			}
			if(terms.get(middle).getWord().length()>=key.length()){
				if(terms.get(middle).getWord().substring(0, key.length()).compareTo(key)<0) {
					low = middle + 1;
				}
				if(terms.get(middle).getWord().substring(0, key.length()).compareTo(key)>0) {
					high = middle - 1;
				}
			}
			else{
				if(terms.get(middle).getWord().compareTo(key)<0) {
					low = middle + 1;
				}
				if(terms.get(middle).getWord().compareTo(key)>0) {
					high = middle - 1;
				}
			}
		}
		return null;
	}
	
	/**
	 * createTerm() - This method creates terms and adds them to the terms list
	 * 
	 */
	public void createTerm(String weight,String word){
		if (weight==null || word==null) {
	        throw new NullPointerException("Strings are null");
	    }
		boolean same=false; // boolean for if any other term is the same 
		for(Term termA:terms){
			if(termA.getWord()==word){ // if word already in list
				same=true;
			}
		}
		if(!same){  // if unique word, then create and add to list
				Term term= new Term(weight,word);
				terms.add(term);	
		}
	}
}
