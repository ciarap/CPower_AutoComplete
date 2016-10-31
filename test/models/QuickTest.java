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
		readUrl();
		quick.sortAlphabetically(quick.getTerms());
	}
	
	@Test
	public void getSetTerms(){
		assertEquals(10000,quick.getTerms().size());
		
		List<Term> termsShorter=new ArrayList<Term>(Arrays.asList(new Term("10","First"),new Term("20","Second"),new Term("30","Third")));
		quick.setTerms(termsShorter);
		assertEquals(3,quick.getTerms().size());
		
		quick.setTerms(new ArrayList<Term>());
		assertEquals(3,quick.getTerms().size());
	}
	@Test
	public void testWeightOf(){
		assertEquals(5.6271872E9,quick.weightOf("the"),0.001);
		assertEquals(392323.0,quick.weightOf("calves"),0.001);
		
		assertEquals(0.0,quick.weightOf("nonexistentTerm"),0.001);
		
	}
	@Test
	public void testBestMatch(){
		assertEquals("eleven",quick.bestMatch("elev"));
		assertEquals("def",quick.bestMatch("de"));
		assertEquals("none",quick.bestMatch("abcde"));
		
		
	}
	@Test
	public void testMatches(){
		assertEquals(Arrays.asList("eleven","elevation"),quick.matches("elev", 2));  //tests sublist only contains the required amount of matches
		assertEquals(Arrays.asList("eleven","elevation","elevated","eleventh"),quick.matches("elev", 10000)); //checks all matches are returned if we ask for more to be returned than there are matches
		assertEquals(null,quick.matches("abcde", 2));
	}
	
	
	@Test
	public void testCreateTerm(){
		quick.getTerms().clear();
		quick.createTerm("10", "first");
		assertEquals(1,quick.getTerms().size());
		assertEquals("first",quick.getTerms().get(0).getWord());
		
		quick.createTerm("20", "second");
		assertEquals(2,quick.getTerms().size());
		assertEquals("second",quick.getTerms().get(1).getWord());
		
		quick.createTerm("20", "second");
		assertEquals(2,quick.getTerms().size());
		
	}
	
	@Test
	public void testSortWeight(){
		List<Term> weightTerms=new ArrayList<Term>(Arrays.asList(new Term("30","Third"),new Term("10","First"),new Term("20","Second")));
		assertEquals(30.0,weightTerms.get(0).getWeight(),0.001);
		assertEquals(10.0,weightTerms.get(1).getWeight(),0.001);
		assertEquals(20.0,weightTerms.get(2).getWeight(),0.001);
		quick.sortWeight(weightTerms);
		assertEquals(30.0,weightTerms.get(0).getWeight(),0.001);
		assertEquals(20.0,weightTerms.get(1).getWeight(),0.001);
		assertEquals(10.0,weightTerms.get(2).getWeight(),0.001);
	}
	
	@Test
	public void testSortAlphabetically(){
		List<Term> weightTerms=new ArrayList<Term>(Arrays.asList(new Term("1","c"),new Term("2","a"),new Term("3","b")));
		assertEquals("c",weightTerms.get(0).getWord());
		assertEquals("a",weightTerms.get(1).getWord());
		assertEquals("b",weightTerms.get(2).getWord());
		quick.sortAlphabetically(weightTerms);
		assertEquals("a",weightTerms.get(0).getWord());
		assertEquals("b",weightTerms.get(1).getWord());
		assertEquals("c",weightTerms.get(2).getWord());
	}
	
	@Test
	public void testBinarySearch(){
		assertEquals(2,quick.binarySearchForBoxOfTerms("thereo").size(),0.001);
		assertEquals(null,quick.binarySearchForBoxOfTerms("abcde"));
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
