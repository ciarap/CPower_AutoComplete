# CPower_AutoComplete

To set up, the program reads in words and associated weights from a URL.

This program then begins by allowing the user choose between Quick AutoComplete, Brute Force AutoComplete, or exit.

The Brute Force program will allow a user enter a prefix, and will return the top weighted terms that begin with this prefix.This program uses time consuming searches such as iterating through full lists.There is an option in the bruteforce menu to exit back onto the main menu.

The Quick program will have the same functionality, however it uses selection sorts and binary searches to improve on speed and efficiency. There is an option in the quick menu to exit back onto the main menu.

For Tests,each model class in the project is tested extensively. 
Every method in every model class is tested, for various values and arguments.
For example, the very limit values are used in testing a search for weight of a term method,to ensure the outer values are not being cut off.
Also, in creation methods, invalid values are tested to enusre the desired corrections take place.
Each possibility was tested for every method written., to ensure no run time errors or undesired results.

TERM TEST SPECIFICATIONS.

 1.  Having created valid and invalid terms in setUp(), check the word and weight of one valid term and one invalid term ( to ensure the correct procedures took place when dealing with invalid initialisations). Check that null arguments throw an exception.
 
 2. Check that the getWeight() method returns the expected weight for any of the terms. Use setWeight() to change the weight for that same term,using a valid weight. Use getWeight() again to check that the weight of the term was changed accordingly. Then try setWeight() using an invalid negative weight, and check that an exception is thrown,and the weight has not changed.
 
 3. Check that the getWord() method returns the expected word for any of the terms. Use setWord() to change the word for that same term,using a valid word. Use getWord() again to check that the word of the term was changed accordingly. Then try setWord() using an invalid null argument,or empty string argument, and check that an exception is thrown.
 
 4. Test the toString() method to ensure the output is as expected for any of the terms.

BRUTE TEST SPECIFICATIONS.

1. Read in the URL file in setUp(), this should populate the terms list. Test the size of this list is as expected (10000).Create a new shorter list of Terms, and use setTerms() to set the terms field as this new list. Test the new size of the list is the correct size of new list.Use setWeight(),but pass through an empty List, then check that the terms list size did not change. Test that setTerms() with a null argument throws an exception.

2. Call weightOf() method for the first term in the list, and also call it for the last term in the list, and ensure both work correctly.Call the weightOf() method with an argument that is not found in the list, to ensure the weight remains 0.0 . Test a null argument throws an exception.

3. Call bestMatch() method for two different arguments that are known to be in the array, and test the result is as expected (the correct value was manually chosen by looking at weights for that certain prefix argument).  Test the bestMatch() method for a prefix that is not present in the list - there are no best matches. This should return "none". Test that a null argument throws an exception. 

4. Call test matches for a prefix that requires a smaller list of suggestions than there are matches. The method should only return the required amount. Test a prefix that has a small list of matches, and set the required amount to the max possible amount (10000),this should only return the full small list of matchees. Test that a prefix with no matches returns an empty list. Test that if a prefix argument is null, or number required matches is negative, an exception is thrown.

5. Create one term and check it was created successfully - check size of list and getWord(). Create another term and ensure the list successsfully contains this second term also. Try create an identical term to the second term, this should not create the term or add it to the list. Test the list size hasn't changed. Test any null arguments throw an exception.

QUICK TEST SPECIFICATIONS.

1. Read in the URL file,and sort the terms list alphabetically in setUp(), this should populate and sort the terms list by word. Test the size of this list is as expected (10000).Create a new shorter list of Terms, and use setTerms() to set the terms field as this new list. Test the new size of the list is the correct size of new list.Use setWeight(),but pass through an empty List, then check that the terms list size did not change. Test that setTerms() with a null argument throws an exception.

2. Call weightOf() method for the first term in the list, and also call it for the last term in the list, and ensure both work correctly.Call the weightOf() method with an argument that is not found in the list, to ensure the weight remains 0.0 . Test a null argument throws an exception.

3. Call bestMatch() method for two different arguments that are known to be in the array, and test the result is as expected (the correct value was manually chosen by looking at weights for that certain prefix argument).  Test the bestMatch() method for a prefix that is not present in the list - there are no best matches. This should return "none". Test that a null argument throws an exception. 

4. Call test matches for a prefix that requires a smaller list of suggestions than there are matches. The method should only return the required amount. Test a prefix that has a small list of matches, and set the required amount to the max possible amount (10000),this should only return the full small list of matchees. Test that a prefix with no matches returns an empty list. Test that if a prefix argument is null, or number required matches is negative, an exception is thrown.

5. Create one term and check it was created successfully - check size of list and getWord(). Create another term and ensure the list successsfully contains this second term also. Try create an identical term to the second term, this should not create the term or add it to the list. Test the list size hasn't changed. Test any null arguments throw an exception.

6. Test that the sortWeight() method works to sort a list by descending order of weight. Create a short list that is not in order of weight, and check these are unsorted. Call the sortWeight() method , and then check the elements are now sorted by weight. Null arguments should throw an exception.

7. Test that the sortAlphabetically() method works to sort a list by alphabetical order. Create a short list that is not in alphabetical order of each word, and check these are unsorted. Call the sortWord() method , and then check the elements are now alphabetically sorted by word. Null arguments should throw an exception.

8. Test the binarySearchForBoxOfTerms() method by searching for a prefix that has a known match range of 2. This should return a list of size 2. Also call this method for a prefix that has no matches - should return null value. Null arguments should throw an exception.
