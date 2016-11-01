# CPower_AutoComplete

To set up, the program reads in words and associated weights from a URL.

This program then begins by allowing the user choose between Quick AutoComplete, Brute Force AutoComplete, or exit.

The Brute Force program will allow a user enter a prefix, and will return the top weighted terms that begin with this prefix.This program uses time consuming searches such as iterating through full lists.There is an option in the bruteforce menu to exit back onto the main menu.

The Quick program will have the same functionality, however it uses selection sorts and binary searches to improve on speed and efficiency. There is an option in the quick menu to exit back onto the main menu.

For Tests,each model class in the project is tested extensively. 
Every method in every model class is tested, for various values and arguments.
For example, the very limit values are used in testing a search for weight of a term method,to ensure the outer values are not being cut off.
Also, in creation methods, invalid values are tested  to enusre the desired corrections take place.
Each possibility was tested for every method written., to ensure no run time errors or undesired results
