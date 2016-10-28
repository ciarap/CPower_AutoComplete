package models;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import algorithms.Term;

public class TermTest
{
	Term term1;
	Term term2;

	@Before
	public void setUp() throws Exception {

		term1 = new Term ("10","word1");
		term2 = new Term ("20","word2");
	}


	@Test
	public void testCreateAndGetters()
	{
		assertEquals (10.0, term1.getWeight(),0.001);
		assertEquals ("word1",term1.getWord());

		Term invalidTerm3= new Term("-3","word3");
		assertEquals (00, invalidTerm3.getWeight(),0.001);
		assertEquals ("word3",invalidTerm3.getWord()); 
	}

	@Test 
	public void testGetSetWeight(){
		assertEquals(20.0, term2.getWeight(),0.001);
		term2.setWeight(25.0);
		assertEquals(25.0, term2.getWeight(),0.001);
		
		term2.setWeight(-1.0);
		assertEquals(25.0, term2.getWeight(),0.001);
	}
	@Test 
	public void testGetSetWord(){
		assertEquals("word2", term2.getWord());
		term2.setWord("word2Replaced");
		assertEquals("word2Replaced", term2.getWord());
		
		term2.setWord("");
		assertEquals("word2Replaced", term2.getWord());
	}


	@Test
	public void testToString()
	{
		assertEquals ("Term [weight=10.0, word=word1]", term1.toString());
	}
}
