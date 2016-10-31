package models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class QuickAutoComplete implements AutoComplete{

	private List<Term> terms=new ArrayList<Term>();
	private  Scanner input= new Scanner(System.in);

	public QuickAutoComplete() throws Exception {

	}
	public List<Term> getTerms() {
		return terms;
	}
	public void setTerms(List<Term> terms) {
		 if (terms==null) {
		        throw new NullPointerException("Terms is null");
		    }
		if (terms.size()!=0){
			this.terms = terms;
		}
	}

	public void quickRun(int suggestionNo) throws Exception{
		sortAlphabetically(terms);
		int option=mainMenu();
		while(option!=2){
			switch (option){
			case 1: 
				Iterator<String> matches = null;
				System.out.println("Please enter a prefix to search for:"  );
				input.nextLine();
				String prefix=input.nextLine().toLowerCase();
				if (matches(prefix,suggestionNo)!=null){
				matches=matches(prefix,suggestionNo).iterator();
				
				int counter=1;
				while(matches.hasNext()){
					if (counter==1) {
						System.out.println("Did you mean..");
					}
				System.out.println(counter+") "+matches.next());
					counter++;
				}
				}
				System.out.println("---------\nBest Match: "+bestMatch(prefix));
				System.out.println("\n    ---Press any key to continue---");
				input.nextLine();
				option = mainMenu();
				break;
			case 2: System.exit(0);
			input.close();
			break;
			}
		}
	}
	
	public int mainMenu(){
		System.out.println(" QUICK AUTOCOMPLETE PROGRAM \n---------------------------");
		System.out.println(" Please choose an option: \n  1) Search a prefix\n  2) Exit to main menu");
		int option;
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
			option = mainMenu();
		}
		return option;
	}



	@Override
	public double weightOf(String term) {
		 if (term==null) {
		        throw new NullPointerException("Term is null");
		    }
		double weight=0.0;
		List<Term> termList=binarySearchForBoxOfTerms(term);
		if(termList!=null){
		for (Term termA:termList){
			if (termA.getWord().compareTo(term)==0)
			{
				weight= termA.getWeight();
			}
		}
		}
		return weight;		
	}

	@Override
	public String bestMatch(String prefix) {
		 if (prefix==null) {
		        throw new NullPointerException("Term is null");
		    }
		
		if (matches(prefix,1)==null){
			return "none";
		}
		else{
		Iterator<String> itermatches=matches(prefix,1).iterator();
		return itermatches.next();
		}

	}

	@Override
	public Iterable<String> matches(String prefix, int k) {
		 if (prefix==null) {
		        throw new NullPointerException("Term is null");
		    }
		  if (k < 0) {
		        throw new IllegalArgumentException("Number of suggestions should be positive");
		    }
		List<Term> matches=binarySearchForBoxOfTerms(prefix);
		if(matches!=null){
		sortWeight(matches);
		if(matches.size()>k ){
			matches=matches.subList(0, k);
		}
		List<String> matchesStrings=new ArrayList<String>();
		for(Term term:matches){
			matchesStrings.add(term.getWord());
		}
		Iterable<String> matchesIter=matchesStrings;
		return matchesIter;
		}
		else{
			return null;
		}
	}

	

	public  void sortWeight(List<Term> terms2){
		if (terms2==null) {
	        throw new NullPointerException("Terms array is null");
	    }
		int N=terms2.size();
		for (int i=0;i<N;i++){
			int min = i;
			for (int j=i+1;j<N;j++)
				if (terms2.get(min).getWeight()-terms2.get(j).getWeight()<0)
					min=j;
			exch(terms2,i,min);

		}
	}


	public  void sortAlphabetically(List<Term> terms2){
		if (terms2==null) {
	        throw new NullPointerException("Terms array is null");
	    }
		int N=terms2.size();
		for (int i=0;i<N;i++){
			int min = i;
			for (int j=i+1;j<N;j++)
				if (less(terms2.get(j).getWord(),terms2.get(min).getWord()))
					min=j;
			exch(terms2,i,min);

		}
	}

	private  boolean less(String v,String w){
	if (v==null || w==null) {
        throw new NullPointerException("Arguments are null");
    }
	 return v.compareTo(w)<0;}

	private  void exch(List<Term> terms2,int i,int j){
		if (terms2==null) {
	        throw new NullPointerException("Terms array is null");
	    }
		Term swap=terms2.get(i);
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
	public void createTerm(String weight,String word){
		if (weight==null || word==null) {
	        throw new NullPointerException("Strings are null");
	    }
		boolean same=false;
		for(Term termA:terms){
			if(termA.getWord()==word){
				same=true;
			}
		}
		if(!same){
				Term term= new Term(weight,word);
				terms.add(term);	
		}
	}
}
