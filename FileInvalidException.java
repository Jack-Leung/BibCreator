// ----------------------------------------------------------
// Assignment# 3
// Question: 1
// Written by: Jack Leung 40061019
// ----------------------------------------------------------

//This class is called when there is an empty field within the file

/**
 * FileInvalidException class is called when there is an invalid/empty field in the article
 * @author Jack
 * @version 1.0
 * @see BibCreator
 * @see BibCreator_2
 */

public class FileInvalidException extends Exception{

	/**
	 * Default constructor for the FileInvalidException class that extends Exception
	 */
	public FileInvalidException() { //Default constructor of FileInvalidException that will call the super parameterized constructor and input the string below
		super("Error: Input file cannot be parsed due to missing information (i.e. month={}, title={}, etc.)");
	}
	
	/**
	 * Parameterized constructor for the FileInvalidException class that extends Exception
	 * @param s a String value for the FileInvalidException parameterized constructor
	 */
	public FileInvalidException(String s) { //Parameterized constructor that call the super parameterized constructor
		super(s);
	}
	
	/**
	 * getMessage() method
	 * @return the super.getMessage() from the Exception class, which is a string
	 */
	public String getMessage() { //getMessage method that returns the string as an argument to the constructor
		return super.getMessage();
	}
}
