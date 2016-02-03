package ir.assignments.three;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 */
	public static ArrayList<String> tokenizeFile(File input) {
		// TODO Write body!
		ArrayList<String> result = new ArrayList<String>();
		if(input == null || input.length() == 0){
			return result;
		}
		Scanner scanner = null;
		try {
			scanner = new Scanner(input);
			String line = null;
			while(scanner.hasNextLine()){
				line = scanner.nextLine();
				if(!line.isEmpty()){
					line = line.replaceAll("[^a-zA-Z]+", " ").toLowerCase();
					Collections.addAll(result, line.trim().split(" "));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(scanner!= null){
				scanner.close();
			}
		}
		return result;

	}
	
	public static ArrayList<String> tokenizeIgnoreUrl(File input) {
		// TODO Write body!
		ArrayList<String> result = new ArrayList<String>();
		if(input == null || input.length() == 0){
			return result;
		}
		Scanner scanner = null;
		try {
			scanner = new Scanner(input);
			String line = null;
			
			//Ignore the first line since its a url we added to index files later
			if(scanner.hasNextLine()) {
				line = scanner.nextLine();
			}
			
			while(scanner.hasNextLine()){
				line = scanner.nextLine();
				if(!line.isEmpty()){
					line = line.replaceAll("[^a-zA-Z]+", " ").toLowerCase();
					Collections.addAll(result, line.trim().split(" "));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(scanner!= null){
				scanner.close();
			}
		}
		return result;

	}
}