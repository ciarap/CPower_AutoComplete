/**
* @author Ciara Power
* @version 01/11/16
*/
package models;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class QuickTest {

	private QuickAutoComplete quick;

	@Before
	public void setUp() throws Exception {
		quick=new QuickAutoComplete();
		readUrl();   //reads in terms
		quick.sortAlphabetically(quick.getTerms());  //sorts terms
	}
	
	/**
	 * getSetTerms() - This method tests the getters and setters for terms 
	 */
	@Test
	public void getSetTerms(){
		assertEquals(10000,quick.getTerms().size());   //check size is 10000 as in URL
		
		List<Term> termsShorter=new ArrayList<Term>(Arrays.asList(new Term("10","First"),new Term("20","Second"),new Term("30","Third")));  //new list
		quick.setTerms(termsShorter);  //set quick list as new list
		assertEquals(3,quick.getTerms().size());  //check the size corresponds to new list size
		
		quick.setTerms(new ArrayList<Term>());  //set as empty list
		assertEquals(3,quick.getTerms().size());  //check it didnt change 
	}
	
	
	/**
	 * testWeightOf() - This method tests the weightOf() method 
	 */
	@Test
	public void testWeightOf(){
		assertEquals(5.6271872E9,quick.weightOf("the"),0.001);//tests first term in list
		assertEquals(392323.0,quick.weightOf("calves"),0.001);  //tests last term in list
		
		assertEquals(0.0,quick.weightOf("nonexistentTerm"),0.001); //tests for term that doesnt exist 
		
	}
	
	/**
	 * testBestMatch() - This method tests the bestmatch() method 
	 */
	@Test
	public void testBestMatch(){    //tests different prefixes, to the known best match
		assertEquals("eleven",quick.bestMatch("elev"));
		assertEquals("def",quick.bestMatch("de"));
		assertEquals("none",quick.bestMatch("abcde"));  // tests non existent term
		
		
	}
	
	/**
	 * testMatches() - This method tests the matches() method 
	 */

	@Test
	public void testMatches(){
		assertEquals(Arrays.asList("eleven","elevation"),quick.matches("elev", 2));  //tests sublist only contains the required amount of matches
		assertEquals(Arrays.asList("eleven","elevation","elevated","eleventh"),quick.matches("elev", 10000)); //checks all matches are returned if we ask for more to be returned than there are matches
		assertEquals(null,quick.matches("abcde", 2));  //tests empty array if no matches
	}
	
	/**
	 * testCreateTerm() - This method tests the createTerm() method 
	 */
	@Test
	public void testCreateTerm(){
		quick.getTerms().clear();
		quick.createTerm("10", "first");   //create term
		assertEquals(1,quick.getTerms().size()); //check size
		assertEquals("first",quick.getTerms().get(0).getWord());    // check first term has expected word value
		
		quick.createTerm("20", "second");  //create another term
		assertEquals(2,quick.getTerms().size()); //check size
		assertEquals("second",quick.getTerms().get(1).getWord());  // check second term has expected word value
		
		quick.createTerm("20", "second");  //create term that is the same as term already in list
		assertEquals(2,quick.getTerms().size());//the new term shouldnt be created and added to list, as no duplicates wanted
		
	}
	
	/**
	 * testsortWeight() - This method tests the sortWeight() method 
	 */
	@Test
	public void testSortWeight(){
		List<Term> weightTerms=new ArrayList<Term>(Arrays.asList(new Term("30","Third"),new Term("10","First"),new Term("20","Second")));  //create unsorted numerically list
		assertEquals(30.0,weightTerms.get(0).getWeight(),0.001);   //check all values unsorted by weight
		assertEquals(10.0,weightTerms.get(1).getWeight(),0.001);
		assertEquals(20.0,weightTerms.get(2).getWeight(),0.001);
		quick.sortWeight(weightTerms);   //sort
		assertEquals(30.0,weightTerms.get(0).getWeight(),0.001);  //check all values now sorted high to low
		assertEquals(20.0,weightTerms.get(1).getWeight(),0.001);
		assertEquals(10.0,weightTerms.get(2).getWeight(),0.001);
	}
	
	/**
	 * testsortAlphabetically() - This method tests the sortAlphabetically() method 
	 */
	@Test
	public void testSortAlphabetically(){
		List<Term> wordTerms=new ArrayList<Term>(Arrays.asList(new Term("1","c"),new Term("2","a"),new Term("3","b"))); //create unsorted alphabetically list
		assertEquals("c",wordTerms.get(0).getWord()); //check all values unsorted by word
		assertEquals("a",wordTerms.get(1).getWord());
		assertEquals("b",wordTerms.get(2).getWord());
		quick.sortAlphabetically(wordTerms);  //sort 
		assertEquals("a",wordTerms.get(0).getWord());  //check all values now sorted alphabetically
		assertEquals("b",wordTerms.get(1).getWord());
		assertEquals("c",wordTerms.get(2).getWord());
	}
	
	/**
	 * testBinarySearch() - This method tests the binarySearchForBoxOfTerms() method 
	 */
	@Test
	public void testBinarySearch(){
		assertEquals(2,quick.binarySearchForBoxOfTerms("thereo").size(),0.001);  //check list returned size with matches
		assertEquals(null,quick.binarySearchForBoxOfTerms("abcde"));   //check for no matches
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
