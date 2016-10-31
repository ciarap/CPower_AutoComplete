package models;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import algorithms.AutoCompleteDriver;
import algorithms.BruteAutoComplete;
import algorithms.Term;


public class BruteTest {

	private BruteAutoComplete brute;

	@Before
	public void setUp() throws Exception {
		brute=new BruteAutoComplete();
		readUrl();
	}
	
	@Test
	public void getSetTerms(){
		assertEquals(10000,brute.getTerms().size());
		
		List<Term> termsShorter=new ArrayList<Term>(Arrays.asList(new Term("10","First"),new Term("20","Second"),new Term("30","Third")));
		brute.setTerms(termsShorter);
		assertEquals(3,brute.getTerms().size());
		
		brute.setTerms(new ArrayList<Term>());
		assertEquals(3,brute.getTerms().size());
	}
	


	@Test
	public void testWeightOf(){
		assertEquals(5.6271872E9,brute.weightOf("the"),0.001);
		assertEquals(392323.0,brute.weightOf("calves"),0.001);
		
		assertEquals(0.0,brute.weightOf("nonexistentTerm"),0.001);
		
	}
	
	@Test
	public void testBestMatch(){
		assertEquals("eleven",brute.bestMatch("elev"));
		assertEquals("def",brute.bestMatch("de"));
		assertEquals("none",brute.bestMatch("abcde"));
		
		
	}

	@Test
	public void testMatches(){
		assertEquals(Arrays.asList("eleven","elevation"),brute.matches("elev", 2));  //tests sublist only contains the required amount of matches
		assertEquals(Arrays.asList("eleven","elevation","elevated","eleventh"),brute.matches("elev", 10000)); //checks all matches are returned if we ask for more to be returned than there are matches
		assertEquals(Arrays.asList(),brute.matches("abcde", 2));
	}
	
	@Test
	public void testCreateTerm(){
		brute.getTerms().clear();
		brute.createTerm("10", "first");
		assertEquals(1,brute.getTerms().size());
		assertEquals("first",brute.getTerms().get(0).getWord());
		
		brute.createTerm("20", "second");
		assertEquals(2,brute.getTerms().size());
		assertEquals("second",brute.getTerms().get(1).getWord());
		
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
