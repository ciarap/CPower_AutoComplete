package algorithms;

import java.util.List;
import java.util.Scanner;

public class AutoCompleteAPI {

	private Scanner input = new Scanner(System.in);
	private static BruteAutoComplete brute;
	private int autoCompleteMethod;

	public static void main(String[] args) throws Exception {
		AutoCompleteAPI api = new AutoCompleteAPI();
		brute=new BruteAutoComplete();
		api.run();

	}
	public AutoCompleteAPI() {
		input = new Scanner(System.in);


	}

	private void run() throws Exception {
		autoCompleteMethod=CompleteMenu();
		while(autoCompleteMethod!=3){
			switch (autoCompleteMethod){
			case 1: 
                 brute.bruteRun(getSuggestionNo());
				break;
			case 2: System.out.println("Quick method");
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


}
