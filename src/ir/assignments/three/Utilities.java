package ir.assignments.three;


import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
                ArrayList<String> tokenList = new ArrayList<String>();
                
                try
                {
                    FileReader reader = new FileReader(input);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    
                    for(String line = bufferedReader.readLine(); 
                        line != null; 
                        line = bufferedReader.readLine()) {
                        
                        String[] lineArray = line.split("[\\W]+");
                        
                        for(String token : lineArray){
                        	if(token.compareTo("") != 0) 
                        	{
                        		tokenList.add(token.toLowerCase());
                        	}
                        }
                    }
                    
                }
                catch(Exception e){
                    
                };
                
		return tokenList;
	}
	
	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 * 
	 * Example one:
	 * 
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total item count: 6
	 * Unique item count: 5
	 * 
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 * 
	 * 
	 * Example two:
	 * 
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 * 
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 * 
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies) {
            int uniqueCount = frequencies.size();
            int totalCount = 0;
            
            for(Frequency freq : frequencies){
                totalCount = totalCount + freq.getFrequency();
            }
            
            if(frequencies != null && frequencies.size() != 0)
            {
                int spc = frequencies.get(0).getText().indexOf(' ');

                if(spc == -1)
                {
                     System.out.print("Total item count: " + totalCount);
                     System.out.print("\nUnique item count: " + uniqueCount);
                }
                else
                {
                    System.out.print("Total 2-gram count: " + totalCount);
                    System.out.print("\nUnique 2-gram count: " + uniqueCount);
                }

                System.out.print("\n\n");

                for(Frequency freq : frequencies){
                    System.out.print(freq.getText() + '\t' + freq.getFrequency() + '\n');
                }

                System.out.print('\n');
            }
            
            
	}
}