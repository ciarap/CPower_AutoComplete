package algorithms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BruteAutoComplete  implements AutoComplete {

	private List<Term> terms=new ArrayList<Term>();
	private  Scanner input= new Scanner(System.in);
	
	public List<Term> getTerms() {
		return terms;
	}

	public void setTerms(List<Term> terms) {
		if (terms.size()!=0){
			this.terms = terms;
		}
	}

	public BruteAutoComplete() throws Exception {
		
	}
	
	public void bruteRun(int suggestionNo) throws Exception{
		Iterator<String> matches;
		int option=mainMenu();
		while(option!=2){
			switch (option){
			case 1: 
				System.out.println("Please enter a prefix to search for:"  );
				input.nextLine();
				String prefix=input.nextLine().toLowerCase();
				matches=matches(prefix,suggestionNo).iterator();
				int counter=1;
				while(matches.hasNext()){
					if (counter==1) {
						System.out.println("Did you mean..");
					}
					System.out.println(counter+") "+matches.next());
					counter++;
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
		System.out.println(" BRUTE FORCE AUTOCOMPLETE PROGRAM \n---------------------------");
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
		double weight=0.0;
		for (Term termA:terms){
			if (termA.getWord().compareTo(term)==0)
					{
				weight= termA.getWeight();
			}
		}
		return weight;
	}

	@Override
	public String bestMatch(String prefix) {
		String bestmatch="none";
		for (Term term: terms){
			if (term.getWord().startsWith(prefix)){
				bestmatch=term.getWord();
			return bestmatch;
			}
		}
		return bestmatch;	
		
	}

	@Override
	public Iterable<String> matches(String prefix, int k){
		List<String> matches=new ArrayList<String>();
		for (Term term: terms){
			if (term.getWord().startsWith(prefix)){
				matches.add(term.getWord());
			}
		}

		if(matches.size()>k){
			matches=matches.subList(0, k);
		}
		else if (matches.size()==0){
			System.out.println("There are no matches for this prefix");
			
		}
		Iterable<String> iterMatches=matches;
		return iterMatches;
		
	}
	public void createTerm(String weight,String word){
		Term term= new Term(weight,word);
		terms.add(term);
	}
}
