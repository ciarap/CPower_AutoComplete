package algorithms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class QuickAutoComplete implements AutoComplete{

	/*private Map<String,Double> terms=new HashMap<String,Double>(10000);
	private TreeMap<String,Double> sortedTerms;
	private  Scanner input= new Scanner(System.in);

	public QuickAutoComplete() throws Exception {
		readUrl();
		sortedTerms=new TreeMap<String,Double>(terms);

	}
	public void quickRun(int suggestionNo) throws Exception{
		System.out.println(sortedTerms.keySet());
		matches("th",3);
	}

	@Override
	public double weightOf(String term) {
		return sortedTerms.get(term).doubleValue();
	}

	@Override
	public String bestMatch(String prefix) {

		return null;
	}

	@Override
	public Iterable<String> matches(String prefix, int k) {
		Iterator<String> matches=startBinarySearch(prefix).iterator();
        while(matches.hasNext()){
        	System.out.println(matches.next());
        }


	    return null;
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
				terms.put(termTokens[1].toLowerCase(),Double.parseDouble(termTokens[0].trim()));
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
	 public List<String> startBinarySearch(String key) 
	     {
		     List<String> keyList=new ArrayList<String>(sortedTerms.keySet());
	          int low = 0;
	          int high = keyList.size() - 1;

	         while(high >= low) {
	              int middle = (low + high) / 2;
				if(keyList.get(middle).startsWith(key)) {
					int findStartBoxIndex=middle-1;
					while(keyList.get(findStartBoxIndex).startsWith(key)){
						findStartBoxIndex-=1;

					}
					int findEndBoxIndex=middle-1;
					while(keyList.get(findEndBoxIndex).startsWith(key)){
						findEndBoxIndex+=1;

					}
	                  return keyList.subList(findStartBoxIndex+1,findEndBoxIndex);
	              }
	              if(keyList.get(middle).substring(0, key.length()).compareTo(key)<0) {
	                  low = middle + 1;
	              }
	              if(keyList.get(middle).substring(0, key.length()).compareTo(key)>0) {
	                  high = middle - 1;
	              }
	         }
	        return keyList;
	    }*/


	private List<Term> terms=new ArrayList<Term>();
	private List<Term> matches=new ArrayList<Term>();
	private  Scanner input= new Scanner(System.in);

	public QuickAutoComplete() throws Exception {

	}
	public void quickRun(int suggestionNo) throws Exception{
		sort(terms);
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
				System.out.println(weightOf(prefix));
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
		double weight=0.0;
		for (Term termA:binarySearchForBoxOfTerms(term)){
			if (termA.getWord().compareTo(term)==0)
			{
				weight= termA.getWeight();
			}
		}
		return weight;
	}

	@Override
	public String bestMatch(String prefix) {
		Iterator<String> itermatches=matches(prefix,1).iterator();
		return itermatches.next();

	}

	@Override
	public Iterable<String> matches(String prefix, int k) {
		matches=binarySearchForBoxOfTerms(prefix);
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

	

	public  void sortWeight(List<Term> terms2){
		int N=terms2.size();
		for (int i=0;i<N;i++){
			int min = i;
			for (int j=i+1;j<N;j++)
				if (terms2.get(min).getWeight()-terms2.get(j).getWeight()<0)
					min=j;
			exch(terms2,i,min);

		}
	}


	public  void sort(List<Term> terms2){
		int N=terms2.size();
		for (int i=0;i<N;i++){
			int min = i;
			for (int j=i+1;j<N;j++)
				if (less(terms2.get(j).getWord(),terms2.get(min).getWord()))
					min=j;
			exch(terms2,i,min);

		}
	}

	private  boolean less(String v,String w)
	{ return v.compareTo(w)<0;}

	private  void exch(List<Term> terms2,int i,int j){
		Term swap=terms2.get(i);
		terms2.set(i,terms2.get(j));
		terms2.set(j,swap);
	}


	public List<Term> binarySearchForBoxOfTerms(String key) 
	{
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
		return terms;
	}
	public void createTerm(String weight,String word){
		Term term= new Term(weight,word);
		terms.add(term);
	}

}
