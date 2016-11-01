/**
* @author Ciara Power
* @version 01/11/16
*/
package models;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TermTest
{
	Term term1;
	Term term2;
	Term term3;
	Term term4;
	Term term5;

	@Before
	public void setUp() throws Exception {

		term1 = new Term ("10","word1");
		term2 = new Term ("20","word2");
		term3 = new Term ("-3","word3"); //invalid weight
	}

	/**
	 * testCreate() - This method tests the create method
	 * Test specification 1 
	 */
	@Test
	public void testCreate()
	{
		assertEquals (10.0, term1.getWeight(),0.001); // checks weight
		assertEquals ("word1",term1.getWord()); //checks word
		
		assertEquals (0.0, term3.getWeight(),0.001); // checks weight set to 0
		assertEquals ("word3",term3.getWord()); //checks word
		
		try   // test null argument gives exception
		 {
			term4=new Term(null,"word4");
		 fail("Should throw exception");
		 }
		 catch (Exception e)
		 {
		 assertTrue(true);
		 }
		try   // test null argument gives exception
		 {
			term5=new Term("5",null);
		 fail("Should throw exception");
		 }
		 catch (Exception e)
		 {
		 assertTrue(true);
		 }
 
	}

	/**
	 * testGetSetWeight() - This method tests the getters and setters for the weight field
	 * Test specification 2
	 */
	@Test 
	public void testGetSetWeight(){
		assertEquals(20.0, term2.getWeight(),0.001);   //checks getter
		term2.setWeight(25.0); //sets valid weight
		assertEquals(25.0, term2.getWeight(),0.001);   //checks it changed

		try   // test null argument gives exception
		 {
		 term2.setWeight(-3);//sets invalid weight
		 fail("Should throw exception");
		 }
		 catch (Exception e)
		 {
		 assertTrue(true);
		 }
		assertEquals(25.0, term2.getWeight(),0.001);   //checks it hasnt changed

	}
	
	/**
	 * testGetSetWord() - This method tests the getters and setters for the word field
	 * Test specification 3
	 */
	@Test 
	public void testGetSetWord(){
		assertEquals("word2", term2.getWord());  //checks initial
		term2.setWord("word2Replaced");  //sets new word
		assertEquals("word2Replaced", term2.getWord()); //checks new word
		
		try   // test empty string argument gives exception
		 {
			term1.setWord("");
		 fail("Should throw exception");
		 }
		 catch (Exception e)
		 {
		 assertTrue(true);
		 }
		try   // test null argument gives exception
		 {
			term1.setWord(null);
		 fail("Should throw exception");
		 }
		 catch (Exception e)
		 {
		 assertTrue(true);
		 }
	}

	/**
	 * testToString() - This method tests the toString method for term
	 * Test specification 4
	 */
	@Test
	public void testToString()
	{
		assertEquals ("Term [weight=10.0, word=word1]", term1.toString());
	}
}
