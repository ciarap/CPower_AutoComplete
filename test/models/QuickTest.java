package models;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import algorithms.BruteAutoComplete;
import algorithms.QuickAutoComplete;

public class QuickTest {

	private QuickAutoComplete quick;

	@Before
	public void setUp() throws Exception {
		quick=new QuickAutoComplete();
		readUrl();
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
