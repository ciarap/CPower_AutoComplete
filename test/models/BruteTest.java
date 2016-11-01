/**
* @author Ciara Power
* @version 01/11/16
*/
package models;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import controllers.AutoCompleteDriver;


public class BruteTest {

	private BruteAutoComplete brute;

	@Before
	public void setUp() throws Exception {
		brute=new BruteAutoComplete();
		readUrl();   //reads in terms
	}
	
	/**
	 * getSetTerms() - This method tests the getters and setters for terms 
	 */
	@Test
	public void getSetTerms(){
		assertEquals(10000,brute.getTerms().size());  //check size is 10000 as in URL
		
		List<Term> termsShorter=new ArrayList<Term>(Arrays.asList(new Term("10","First"),new Term("20","Second"),new Term("30","Third"))); //new list
		brute.setTerms(termsShorter);    //set brute list as new list
		assertEquals(3,brute.getTerms().size());  //check the size corresponds to new list size
		
		brute.setTerms(new ArrayList<Term>());    //set as empty list
		assertEquals(3,brute.getTerms().size());   //check it didnt change 
	}
	
	/**
	 * testWeightOf() - This method tests the weightOf() method 
	 */
	@Test
	public void testWeightOf(){
		assertEquals(5.6271872E9,brute.weightOf("the"),0.001);    //tests first term in list
		assertEquals(392323.0,brute.weightOf("calves"),0.001);   //tests last term in list
		
		assertEquals(0.0,brute.weightOf("nonexistentTerm"),0.001);   //tests for term that doesnt exist 
		
	}
	
	/**
	 * testBestMatch() - This method tests the bestmatch() method 
	 */
	@Test
	public void testBestMatch(){     //tests different prefixes, to the known best match
		assertEquals("eleven",brute.bestMatch("elev"));   
		assertEquals("def",brute.bestMatch("de"));
		assertEquals("none",brute.bestMatch("abcde"));   // tests non existent term
		
		
	}

	/**
	 * testMatches() - This method tests the matches() method 
	 */

	@Test
	public void testMatches(){
		assertEquals(Arrays.asList("eleven","elevation"),brute.matches("elev", 2));  //tests sublist only contains the required amount of matches
		assertEquals(Arrays.asList("eleven","elevation","elevated","eleventh"),brute.matches("elev", 10000)); //checks all matches are returned if we ask for more to be returned than there are matches
		assertEquals(Arrays.asList(),brute.matches("abcde", 2));   //tests empty array if no matches
	}
	
	/**
	 * testCreateTerm() - This method tests the createTerm() method 
	 */

	@Test
	public void testCreateTerm(){
		brute.getTerms().clear();
		brute.createTerm("10", "first");    //create term
		assertEquals(1,brute.getTerms().size());    //check size 
		assertEquals("first",brute.getTerms().get(0).getWord());    // check first term has expected word value
		
		brute.createTerm("20", "second");   //create another term
		assertEquals(2,brute.getTerms().size());   //check size 
		assertEquals("second",brute.getTerms().get(1).getWord());    // check second term has expected word value
		
		brute.createTerm("20", "second");    //create term that is the same as term already in list
		assertEquals(2,brute.getTerms().size());   //the new term shouldnt be created and added to list, as no duplicates wanted
		
	}
	
	
	/**
	 * readUrl() - This method just used in set up to read in URL data - more comments in BruteAutoComplete class for this method
	 */

	public  void readUrl() throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://wit-computing.github.io/algorithms-2016/topic04/book-2/data/wiktionary.txt").openStream()));
		Scanner inTerm = new Scanner(in);
		String delims = "[	]";//each field in the file is separated(delimited) by a space.
		while (inTerm.hasNextLine()) {
			String termDetails = inTerm.nextLine();
		
			String[] termTokens = termDetails.split(delims);

			if (termTokens.length == 2) {
				brute.createTerm(termTokens[0],termTokens[1].toLowerCase());
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
