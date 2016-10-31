package algorithms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutoCompleteDriver {

	private Scanner input = new Scanner(System.in);
	private static List<Term> terms=new ArrayList<Term>();
	private static BruteAutoComplete brute;
	private static QuickAutoComplete quick;
	private int autoCompleteMethod;

	public static void main(String[] args) throws Exception {
		AutoCompleteDriver driver = new AutoCompleteDriver();
		brute=new BruteAutoComplete();
		quick=new QuickAutoComplete();
		readUrl();
		driver.run();

	}
	public AutoCompleteDriver() {
		input = new Scanner(System.in);
	}

	private void run() throws Exception {
		
		autoCompleteMethod=CompleteMenu();
		while(autoCompleteMethod!=3){
			switch (autoCompleteMethod){
			case 1: 
                 brute.bruteRun(getSuggestionNo());
				break;
			case 2: quick.quickRun(getSuggestionNo());
				break;
			case 3: System.exit(0);
			input.close();
			break;
			}
			autoCompleteMethod=CompleteMenu();
		}
	}
	private int CompleteMenu() {
		System.out.println(" AUTOCOMPLETE PROGRAMS \n---------------------------");
		System.out.println(" Please choose an option: \n  1) Brute Force AutoComplete \n  2) Quick AutoComplete \n  3) Exit");
		try {
			autoCompleteMethod = input.nextInt();
			while (autoCompleteMethod < 1 || autoCompleteMethod > 3) {   // must get option in the menu range
				System.out.println("Invalid choice,please choose again!");
				System.out.print("==>>");
				autoCompleteMethod = input.nextInt();
			}
		} catch (Exception e) {      // if input other than integer is entered
			System.out.println("---Invalid choice,please choose again!---");
			System.out.println("    ---Press any key to continue---");
			input.nextLine();
			input.nextLine();
			autoCompleteMethod = CompleteMenu();
		}
		return autoCompleteMethod;
	}
	public int getSuggestionNo(){
		System.out.println("Please enter how many suggestions you want returned in this program:"  );
		int suggestionsNumber;
		try {
			suggestionsNumber=input.nextInt();
			while (suggestionsNumber <=0) {   // must get positive value
				System.out.println("---Invalid choice,please choose again!---");
				System.out.print("==>>");
				suggestionsNumber=input.nextInt();
			}
		} catch (Exception e) {      // if input other than integer is entered
			System.out.println("---Invalid choice,please choose again!---");
			System.out.println("    ---Press any key to continue---");
			input.nextLine();
			input.nextLine();
			suggestionsNumber=getSuggestionNo();
		}
		return suggestionsNumber;
	}
	public static  void readUrl() throws Exception{
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
				brute.createTerm(termTokens[0],termTokens[1].toLowerCase());
				quick.createTerm(termTokens[0],termTokens[1].toLowerCase());
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

}
