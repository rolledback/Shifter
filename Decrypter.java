import java.util.*;
import java.io.*;
public class Decrypter {

	static ArrayList<String> dictionary = new ArrayList<String>();
	static int scoreTable[] = new int[26];
    public static void main(String args[]) throws IOException {
    	buildDictionary();  
    	int statusFlag = 1;    		  	    	    	
    	while(statusFlag == 1) {    	
    		Scanner userInput = new Scanner(System.in);	  
    		System.out.print("String to attempt decrpyting: ");
    		String input = userInput.nextLine();
    		searchDictionary(input);
    				
    		int correctShift = decodeLine(input);
    		if(correctShift > -1)
    			System.out.println("Best guess: " + shiftString(input, correctShift));
    		else
    			System.out.println("No good guess available.");
    		System.out.print("Would you like to decode another string y\\n: ");    		
    		String answer = userInput.next();
    		if(answer.equals("y") || answer.equals("Y") || answer.equals("yes") || answer.equals("Yes"))
    			statusFlag = 1;
    		else
    			statusFlag = 0;    		
    	}    	
    }
    public static String shiftString(String str, int shiftLength) {
    	char strArray[] = str.toLowerCase().toCharArray();
    	for(int charIndex = 0; charIndex < str.length(); charIndex++) {
    		if(strArray[charIndex] >= 97 && strArray[charIndex] <= 122)
    			if(strArray[charIndex] + shiftLength <= 122)
    				strArray[charIndex] += shiftLength;
    			else
    				strArray[charIndex] = (char)(97 + (strArray[charIndex] + shiftLength - 123));  		
    	}
    	return new String(strArray);
    } 
    public static void buildDictionary() throws IOException {
    	Scanner in = new Scanner(new File("dictionary.txt"));
    	String word;
    	while(in.hasNext()) {
    		word = in.nextLine();
      		dictionary.add(word.toLowerCase()); 		
    	}    	
    	dictionary = mergeSort(dictionary);
    }
    public static int decodeLine(String input) {
    	Scanner readStrings = new Scanner(input);
    	while(readStrings.hasNext()) {
    		String nextWord = readStrings.next();
		   	for(int x = 0; x < 26; x++)
    			if(searchDictionary(shiftString(nextWord, x)))
    				scoreTable[x] += 1;
    	}
    	int highestScore = 0;
    	int bestIndex = -1;
    	for(int i = 0; i < 26; i++)
    		if(scoreTable[i] > highestScore) {
    			highestScore = scoreTable[i];
    			bestIndex = i;
    		}    		
    	return bestIndex;  	
    }
    
    public static boolean searchDictionary(String word) {
    	String target;
    	int low, mid, high;
    	low = 0;
    	mid = dictionary.size() / 2;
    	high = dictionary.size() - 1;
    	boolean found = false;
    	while(!found && low != mid && high != mid) {  	
    		target = dictionary.get(mid);			
    		if(word.equals(target))
    			found = true;
    		else if(word.compareTo(target) < 0) {
    			high = mid;
    			mid = mid - ((mid - low) / 2);
    		} 	
    		else {
    			low = mid;
    			mid = mid + ((high - mid) / 2);
    		}    	
    	}
		return found;
    }
    
    public static ArrayList<String> mergeSort(ArrayList<String> list) {
    	if(list.size() <= 1 || list == null)
    		return list;
    	int halfWay = list.size() / 2;
    	ArrayList<String>left = new ArrayList<String>(list.subList(0, halfWay));
    	ArrayList<String>right = new ArrayList<String>(list.subList(halfWay, list.size()));
    	return merge(mergeSort(left), mergeSort(right));    	
    }
    
    public static ArrayList<String> merge(ArrayList<String> one, ArrayList<String> two) {
		int iOne = 0; int iTwo = 0;
		ArrayList<String> temp = new ArrayList<String>();
		while(iOne < one.size() && iTwo < two.size()) {
			if(one.get(iOne).compareTo(two.get(iTwo)) < 0) {
				temp.add(one.get(iOne));
				iOne++;
			}
			else if(one.get(iOne).compareTo(two.get(iTwo)) > 0) {
				temp.add(two.get(iTwo));
				iTwo++;
			}		
			else {
				temp.add(two.get(iTwo));
				iTwo++; iOne++;
			}			
		}
		if(iOne == one.size() && iTwo != two.size())
			temp.addAll(new ArrayList<String>(two.subList(iTwo, two.size())));
		if(iOne != one.size() && iTwo == two.size())
			temp.addAll(new ArrayList<String>(one.subList(iOne, one.size())));
		return temp;    	
    }    
}