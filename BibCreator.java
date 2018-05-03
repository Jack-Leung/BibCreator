// -----------------------------------------------------
// Assignment 3
// Question: 1
// Written by: Jack Leung - 40061019
// -----------------------------------------------------

//The program consists of reading 10 files provided
//It reads the content and take the specific field that needs to be written in another file
//It will verify if the file is valid or not and will try/throw/catch the exceptions

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.StringTokenizer;

/**
 * BibCreator class contains 
 * @author Jack
 * @version 1.0
 * @see FileInvalidException
 */

public class BibCreator {
	
	/**
	 * displayFileContents() method that display the content of a file
	 * @param inFileStreamName a BufferedReader value
	 * @throws IOException throws the exception IOException
	 */
	public static void displayFileContents(BufferedReader inFileStreamName) throws IOException
	{
		int x;
		
		x = inFileStreamName.read(); //start reading the file by character
		while(x != -1) //while the character isn't -1 which is EOF
		{
			System.out.print((char)x);		// MUST CAST; otherwise all what is read will be shown as integers
			x = inFileStreamName.read();		
		}
		// Must close the file 
		inFileStreamName.close();
	}
	
	public static int validCounter=0; //static so that the entire class can call it
	public static int invalidCounter=0; //static so that the entire class can call it
	
	/**
	 * processFilesForValidation() method that reads multiple files and output them into a .json file with different reference format
	 * It also check if the file being read is valid or not
	 * @param input a Scanner value
	 * @param json a PrintWriter value
	 * @param acm a PrintWriter value
	 * @param nj a PrintWriter value
	 * @throws FileInvalidException throws the exception FileInvalidException
	 */
	public static void processFilesForValidation(Scanner input, PrintWriter json, PrintWriter acm, PrintWriter nj) throws FileInvalidException {
		
		String s="";
		int numArticle =0;
		int tokenCounter=0;
		
		StringTokenizer stkn = null; //initialize the variable to null so that compiler will be happy
		String[] token;
		
		String IEEE=""; //the final variable that will be output into the ieee.json file
		String ACM=""; //the final variable that will be output into the acm.json file
		String NJ=""; //the final variable that will be output into the nj.json file
		
		//creating the variables for different formats
		String storeAuthor =""; 
		String IEEEauthor="";
		String ACMauthor="";
		String NJauthor="";
		
		String storeTitle="";		
		String IEEEtitle="";

		String storeJournal="";
		String IEEEjournal="";

		String storeVolume ="";
		String IEEEvolume="";
		
		String storeNumber="";
		String IEEEnumber="";
				
		String storePage="";
		String IEEEpage="";

		
		String storeMonth="";
		String IEEEmonth="";
		
		String storeYear="";
		String IEEEyear="";
	
		String DOI ="";
		
		while (input.hasNextLine()) { //read through the file until EOF
			s= input.nextLine();
			
			if (s.contains("={}")){ //if there is an empty field which is {}
				invalidCounter++; //increment the invalidCounter
				input.close(); //close all opened files so that there won't be an error
				json.close();
				acm.close();
				nj.close();
				throw new FileInvalidException("File Invalid. Field \"" + s.substring(0, s.indexOf("=")) + "\" is Empty. Processing stopped at this point. Other empty fields may be present as well!");
				//throws the exception when it is an invalid file
			}
			
			else {
			if (s.contains("@ARTICLE{")) { //when there is this article string, it counts the amount of article
				numArticle++;
			}
			
			if (s.contains("author={")) { //when it reaches the field author, it copies the content of it and assign the its respected variables
				storeAuthor = s.substring(s.indexOf("{")+1, s.indexOf("}"));	

				IEEEauthor = storeAuthor.replaceAll(" and", ",") + ".";
			
				NJauthor = storeAuthor.replaceAll("and", "&");
				
				stkn = new StringTokenizer(storeAuthor, " "); //Tokenize the lengthy authors
				tokenCounter = stkn.countTokens();
				token = new String[tokenCounter];
				for(int i=0; i<tokenCounter; i++){
					token[i] = stkn.nextToken(); //Assign the separated words into token[]
				}
				
				storeAuthor = token[0] + " " + token[1];
				ACMauthor = storeAuthor + " et al";		
			}
			
			if (s.contains("title={")) { //when it reaches the field title, it copies the content of it and assign the its respected variables
				storeTitle = s.substring(s.indexOf("{")+1, s.indexOf("}"));	
				IEEEtitle= "\""+storeTitle+"\"";
			}
			
			if (s.contains("journal={")) { //when it reaches the field journal, it copies the content of it and assign the its respected variables
				storeJournal = s.substring(s.indexOf("{")+1, s.indexOf("}")); 
				IEEEjournal = storeJournal;	
			}
			
			if (s.contains("volume={")){ //when it reaches the field volume, it copies the content of it and assign the its respected variables
				storeVolume = s.substring(s.indexOf("{")+1, s.indexOf("}")); 
				IEEEvolume = "vol. " + storeVolume;	
			}
			
			if (s.contains("number={")) { //when it reaches the field number, it copies the content of it and assign the its respected variables
				storeNumber = s.substring(s.indexOf("{")+1, s.indexOf("}")); 
				IEEEnumber = "no. " +storeNumber;
			}
			
			if (s.contains("pages={")) { //when it reaches the field pages, it copies the content of it and assign the its respected variables
				storePage = s.substring(s.indexOf("{")+1, s.indexOf("}")); 
				IEEEpage = "p. " + storePage;
			}
			
			if (s.contains("month={")){ //when it reaches the field month, it copies the content of it and assign the its respected variables
				storeMonth = s.substring(s.indexOf("{")+1, s.indexOf("}")); 
				IEEEmonth = storeMonth;	
			}
			
			if (s.contains("year={")){ //when it reaches the field year, it copies the content of it and assign the its respected variables
				storeYear = s.substring(s.indexOf("{")+1, s.indexOf("}")); 
				IEEEyear = storeYear;	
			}			
			
			if (s.contains("doi={")){ //when it reaches the field doi, it copies the content of it and assign the its respected variables
				DOI = s.substring(s.indexOf("{")+1, s.length()-3);
				DOI = "DOI:https://doi.org/" + DOI;
			}
			
			
			if (s.equals("}")){ //once it reaches the end of the article, it combines each field into one single output so that it can be written properly into the respective file and format
				IEEE = IEEEauthor +" " + IEEEtitle +", " + IEEEjournal +", " + IEEEvolume +", " + IEEEnumber +", " + IEEEpage +", " + IEEEmonth + " " + IEEEyear +".";
				json.println(IEEE);
				json.println();
				
				ACM = "["+numArticle+"] "+ ACMauthor + ". " + storeYear +". " + storeTitle + ". " + storeJournal + ". " + storeVolume + ", " + storeNumber + " (" + storeYear + "), " + storePage +". " + DOI +".";
				acm.println(ACM);
				acm.println();
				
				NJ = NJauthor + ". " + storeTitle + ". " + storeJournal + ". " + storeVolume + ", " + storePage + " (" + storeYear + ").";
				nj.println(NJ);
				nj.println();
			}
			
			}//end of else
			
			if (input.hasNextLine() == false) //when it reaches the EOF, increment the validCounter
				validCounter++;
		}//end of while
		//close the opened files so that it will be perfectly fine
		input.close();
		json.close();
		acm.close();
		nj.close();
	}//end of processFilesForValidation
	
	/**
	 * main() method that creates the necessary objects and try and catch the exceptions
	 * @param args a String array value
	 */
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		
		//Initialize the variable sc so that compiler is happy
		Scanner sc = null; //for the Scanner to read the files
		
		PrintWriter json = null; //for the PrintWriter to write the files
		PrintWriter acm = null;
		PrintWriter nj = null;
		
		int numAttempt =0;
		
		BufferedReader br = null; //for the BufferedReader to read the files created
		String readFile =""; //name of the file being read by BufferedReader
		
		String s = ""; //file name being read by the scanner
		String IEEE=""; //file name of IEEE format being written by PrintWriter
		String ACM=""; //file name of ACM format being written by PrintWriter
		String NJ =""; //file name of NJ format being written by PrintWriter
		
		File fileIEEE = null;  //used to delete the invalid files for proper formats
		File fileACM = null;
		File fileNJ = null;
		
		System.out.println("Welcome to BibCreator made by Jack Leung!");
		System.out.println();
		//System.out.println("Enter the name of the .bib file:");
		//s=kb.nextLine();
		
		for (int i=1; i<=10; i++) { //loop 10 times for the 10 latex.bib files
			//assign the names of the files
			s = "Latex"+i+".bib"; 
		
			IEEE = "IEEE"+i+".json";
			ACM = "ACM"+i+".json";
			NJ = "NJ"+i+".json";
		
			try{//try to read and write to IEEE
				sc = new Scanner(new FileInputStream(s));
				json = new PrintWriter(new FileOutputStream(IEEE));
				acm = new PrintWriter(new FileOutputStream(ACM));
				nj = new PrintWriter(new FileOutputStream(NJ));
				fileIEEE = new File(IEEE);
				fileACM = new File(ACM);
				fileNJ = new File(NJ);
				
				processFilesForValidation(sc, json, acm, nj); //call the method to check if valid or not, to read the files and then write the new files
				
			}
			catch (FileNotFoundException e){ //If the file being read/created is not found/exist
				System.out.println("Could not open input file " +s+ " for reading. Please check if file exists!");
				System.out.println("Program will terminate after closing any opened files.");
				System.exit(0); //exit the program
			}
			catch (FileInvalidException e) { //If the file is invalid
				System.out.println("=====CALLING FileInvalidException=====");
				System.out.println("Problem detected with input file: " + s);
				System.out.println(e.getMessage());
				
				System.out.println("\nProceeding to delete file...");
				System.out.println("Deleted files (IEEE, ACM, NJ): " + fileIEEE.delete() +", "+ fileACM.delete() +", "+ fileNJ.delete()); //show whether the files are deleted or not
				System.out.println("Invalid file exist (IEEE, ACM, NJ): " + fileIEEE.exists() +", "+ fileACM.delete() +", "+ fileNJ.delete()); //show whether the files exist in the directory
			}
			
			catch (Exception e){ //Catch any other exception that is not within the exception range of the programmer
				System.out.println("Exception error. Will terminate.");
				System.exit(0);
			}
		
		}//end for loop 10x
		System.out.println("\n\nA total of " + invalidCounter + " files were invalid, and could not be processed. All other " + validCounter + " \"Valid\" files have been created.\n");
		
		while (numAttempt < 2) {//User will have 2 attempts maximum to enter the correct name of the .json file that they want to read the content
			System.out.println("Please enter the name of one of the files that you need to review:");
			readFile = kb.next();
			
			try {
				br = new BufferedReader(new FileReader(readFile));
				System.out.println("Here are the contents of the successfully created Jason File: " + readFile);
				numAttempt =2; //if they successfully entered a correct file name, then the numAttempt=2 so that they exit of the while loop
			}
			catch (FileNotFoundException e) {
				if (numAttempt >= 1) { //if they used more than 2 attempts
					System.out.println("Could not open input file again! Either file does not exist or could not be created.");
					System.out.println("Sorry! I am unable to display your desired files! Program will exit!");
					System.exit(0);
				}
				System.out.println("Could not open input file. File does not exist; possibly it could not be created!");
				System.out.println("\nHowever, you will be allowed another chance to enter another file name.");
				numAttempt++;
				
				
			}
		}//end of while
	
		try {
			displayFileContents(br);
			System.out.println("\n\nThank you for using the program!");
		}
		catch(IOException e) {
			System.out.println("Error occured while reading the file: " + readFile);
			System.out.println("Program will terminate");
			System.exit(0);
		}
	
	}//end of main
	
}//end of class
