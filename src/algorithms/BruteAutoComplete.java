package algorithms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BruteAutoComplete  implements AutoComplete {

	private List<Term> terms=new ArrayList<Term>();
	private  List<String> matches=new ArrayList<String>();
	private  int option=0;
	private  String prefix;
	private  Scanner input= new Scanner(System.in);
	
	public List<Term> getTerms() {
		return terms;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

	public List<String> getMatches() {
		return matches;
	}

	public void setMatches(List<String> matches) {
		this.matches = matches;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	
	public BruteAutoComplete() throws Exception {
		readUrl();
	}
	
	public void bruteRun(int suggestionNo) throws Exception{
		option=mainMenu();
		while(option!=2){
			switch (option){
			case 1: 
				System.out.println("Please enter a prefix to search for:"  );
				input.nextLine();
				prefix=input.nextLine().toLowerCase();
				matches=(List<String>) matches(prefix,suggestionNo);
				if(matches.size()!=0){
					System.out.println("Did you mean..");
					int counter=1;
					for (String word:matches){
						System.out.println(counter+") "+word);
						counter++;
					}
					System.out.println("---------\nBest Match: "+bestMatch(prefix));
				}
				System.out.println("\n    ---Press any key to continue---");
				input.nextLine();
				option = mainMenu();
				matches.clear();
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

	
	public  void readUrl() throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://wit-computing.github.io/algorithms-2016/topic04/book-2/data/wiktionary.txt").openStream()));
		Scanner inTerm = new Scanner(in);
		String delims = "[	]";//each field in the file is separated(delimited) by a space.
		while (inTerm.hasNextLine()) {
			// get user and rating from data source
			String termDetails = inTerm.nextLine();
			// parse user details string
			String[] termTokens = termDetails.split(delims);

			// output user data to console.
			if (termTokens.length == 2) {
				createTerm(termTokens[0],termTokens[1].toLowerCase());
			}
			else
			{
				if(termTokens.length!=1){
					inTerm.close();
					throw new Exception("Invalid member length: "+termTokens.length);
				}
			}
		}
		inTerm.close();
	}
	@Override
	public double weightOf(String term) {
		double weight=0.0;
		for (Term termA:terms){
			if (termA.getWord()==term){
				weight= termA.getWeight();
			}
		}
		return weight;
	}

	@Override
	public String bestMatch(String prefix) {
		return  matches.get(0);
	}

	@Override
	public Iterable<String> matches(String prefix, int k){
		for (Term term: terms){
			if (term.getWord().startsWith(prefix)){
				matches.add(term.getWord());
			}
		}

		if(matches.size()>k){
			return matches.subList(0, k);
		}
		else if (matches.size()==0){
			System.out.println("There are no matches for this prefix");
			return matches;

		}
		else {
			return matches;
		}
	}
	public void createTerm(String weight,String word){
		Term term= new Term(weight,word);
		terms.add(term);
	}
}
