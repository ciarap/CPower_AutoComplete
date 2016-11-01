/**
* @author Ciara Power
* @version 01/11/16
*/

package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.BruteAutoComplete;
import models.QuickAutoComplete;
import models.Term;

public class AutoCompleteDriver {

	private Scanner input = new Scanner(System.in);
	private static BruteAutoComplete brute;
	private static QuickAutoComplete quick;
	private int autoCompleteMethod;   // brute or quick

	public static void main(String[] args) throws Exception {
		AutoCompleteDriver driver = new AutoCompleteDriver();
		brute=new BruteAutoComplete();
		quick=new QuickAutoComplete();
		readUrl();   // calls method which reads in terms and creates lists in brute and quick classes
		driver.run(); // calls run method to start program

	}
	public AutoCompleteDriver() {
		input = new Scanner(System.in);
	}

	/**
	 * run() - This method gets a choice of program from the end user, and runs either bruteAutoComplete
	 *  or QuickAutoComplete,or exits the program.
	 */
	private void run() throws Exception {
		autoCompleteMethod=completeMenu();  // calls menu for program choice and puts value in autoCompleteMethod field
		while(autoCompleteMethod!=3){    // while EXIT is not chosen
			switch (autoCompleteMethod){
			case 1: 
                 brute.bruteRun(getSuggestionNo());     // run brute program, pass through number of suggestions required (gotten from method call)
				break;
			case 2: quick.quickRun(getSuggestionNo());    // run quick program, pass through number of suggestions required(gotten from method call)
				break; 
			case 3: System.exit(0);
			input.close();
			break;
			}
			autoCompleteMethod=completeMenu();  // get choice again and rerun menu while EXIT wasnt chosen
		}
	}
	
	/**
	 * completeMenu() - This method gets a choice of program from the end user,and checks if input is valid.
	 * @return the integer choice of program 
	 */
	private int completeMenu() {
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
			autoCompleteMethod = completeMenu();   //recurse back to menu again
		}
		return autoCompleteMethod;
	}
	
	/**
	 * getSuggestionNo() - This method gets the number of suitable word suggestions the user wants the program to display.
	 * @return integer for number of suggestions
	 */
	public int getSuggestionNo(){
		System.out.println("Please enter how many suggestions you want returned in this program:"  );
		int suggestionsNumber;   //local variable for number of suggestions
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
			suggestionsNumber=getSuggestionNo();   //recurse back to get another suggestion number
		}
		return suggestionsNumber;
	}
	
	/**
	 * readUrl() - This method reads in the weights and words from the specified URL, creates Terms using these values, and adds the values 
	 * to the terms Lists in both QuickAutoComplete and BruteAutoComplete, ready for use depending on the user's choice of program.
	 */
	public static  void readUrl() throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://wit-computing.github.io/algorithms-2016/topic04/book-2/data/wiktionary.txt").openStream()));
		Scanner inTerm = new Scanner(in);
		String delims = "[	]";//each field in the file is separated(delimited) by a tab.
		while (inTerm.hasNextLine()) {
			// get word and weight from data source
			String termDetails = inTerm.nextLine();
			// parse term details string
			String[] termTokens = termDetails.split(delims);// split the line of data into a String[] by the tab divider
			if (termTokens.length == 2) { // if both weight and word present
				brute.createTerm(termTokens[0],termTokens[1].toLowerCase()); // create the Term in BruteAutoComplete, using lowercase
				quick.createTerm(termTokens[0],termTokens[1].toLowerCase()); // create the Term in QuickAutoComplete, using lowercase
			}
			else //if length is anything but 2
			{
				if(termTokens.length!=1){    //first line in url has "10000" ,so program only goes through here and throws exception when data token number is not equal to 1
					inTerm.close();
					throw new Exception("Invalid member length: "+termTokens.length);
				}
			}
		}
		inTerm.close();
	}

}
