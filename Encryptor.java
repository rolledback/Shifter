import java.util.*;
import java.io.*;

public class Encryptor {

    public static void main(String args[]) throws IOException {
    	int statusFlag = 1; 	
    	while(statusFlag == 1) {
    		Scanner userInput = new Scanner(System.in);
    		System.out.print("String to encrypt: ");
    		String toEncrypt = userInput.nextLine();
    		System.out.print("How many places to shift: ");
    		int shiftTotal = userInput.nextInt();
    		while(shiftTotal > 26)
    			shiftTotal -= 26;
    		System.out.println(toEncrypt + " -> " + Decrypter.shiftString(toEncrypt, shiftTotal));
    		
    		System.out.print("Would you like to decode another string y\\n: ");    		
    		String answer = userInput.next();
    		
    		if(answer.equals("y") || answer.equals("Y") || answer.equals("yes") || answer.equals("Yes"))
    			statusFlag = 1;
    		else
    			statusFlag = 0;   
    	}
    }    
}