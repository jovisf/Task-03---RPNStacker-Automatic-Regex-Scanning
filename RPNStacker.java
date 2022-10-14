
import java.util.Scanner;
import java.util.Stack;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class RPNStacker {

    private static Stack<String> stack = new Stack<>();
    private static Boolean debugging = true;

    public static String parseOperation(String operation, Stack<String> stack, Map<String,String> hashTable) throws Exception {
        String result = (stack.empty()) ? "0" : stack.pop();

        if (!stack.empty()) {
            result = Integer.toString(calculate(operation, stack.pop(), result, hashTable)) ;
        }

        return result;
    }

    public static int calculate(String operation, String leftOperator, String rightOperator, Map<String,String> hashTable) throws Exception {
    	 
    	
    	TokenType fstOperatingTokenType = TokenType.EOF;
    	TokenType sndOperatingTokenType = TokenType.EOF;
    	
    	int fstOperatingValue = 0;
    	int sndOperatingValue = 0;
    	
    	if(isId(leftOperator)) {
    		fstOperatingTokenType = TokenType.ID;
    		fstOperatingValue = mapHash(hashTable, leftOperator);
    	}
    	else {
    		fstOperatingTokenType = TokenType.NUM;
    		fstOperatingValue = Integer.parseInt(leftOperator);
    	}
    	
    	if(isId(rightOperator)) {
    		sndOperatingTokenType = TokenType.ID;
    		sndOperatingValue = mapHash(hashTable, rightOperator);
    	}
    	else {
    		sndOperatingTokenType = TokenType.NUM;
    		sndOperatingValue = Integer.parseInt(rightOperator);
    	}
    	
    	if(debugging) {
    		System.out.println(new Token(fstOperatingTokenType, leftOperator));
    		System.out.println(new Token(sndOperatingTokenType, rightOperator));
    	}
    	
    	TokenType tokenType = TokenType.EOF;
    	int value = 0;
    	
    	switch (operation) {
        case "+":
        	tokenType = TokenType.PLUS;
            value = fstOperatingValue + sndOperatingValue;
            break;
        case "-":
        	tokenType = TokenType.MINUS;
        	value = fstOperatingValue - sndOperatingValue;
            break;
        case "*":
        	tokenType = TokenType.STAR;
        	value = fstOperatingValue * sndOperatingValue;
            break;
        case "/": {
        	tokenType = TokenType.SLASH;
        	value = fstOperatingValue / sndOperatingValue;
        	if (sndOperatingValue == 0) {
        		throw new Exception("Impossivel dividir por 0");
        	}
            break;
        }
        default:
            value = fstOperatingValue;
        }
    	
    	if(debugging) {
    		System.out.println(new Token(tokenType, operation));
    	}
    	
    	return value;
    }

    // check if current input is an integer or an operation

    public static boolean isInteger(String input) {
        if (input == null)
            return false;

        return input.matches("(\\d)+");
    }


    public static boolean isOperation(String input) {
        if (input == null)
            return false;

        return input.matches("(\\+|-|\\*|/)");
    }
    
    public static boolean isId(String input) {
        if (input == null)
            return false;

        return input.matches("([A-Za-z])");
    }
    
    public static int mapHash (Map<String,String> hashTable, String key) throws Exception {
    	 if (hashTable.containsKey(key)) {
    		 return Integer.parseInt(hashTable.get(key)) ;
    	 }
    	 else {
    		 throw new Exception(key + " cannot be resolved");
    	 }
    }

    public static void main(String[] args) throws Exception {
    	
    	Map<String,String> hashTable = new HashMap<String,String>();
    	hashTable.put("y", new String("10"));

        String filePath = "C:/Users/jovis/OneDrive/Documentos/Calc1.stk";
        
        try {
	        File file = new File(filePath);
	        Scanner reader = new Scanner(file);
	
	        while (reader.hasNextLine()) {
	            String line = reader.nextLine();
	
	            if (isInteger(line)) {
	                stack.push(line);
	            } else if (isOperation(line)) {
	                String current = parseOperation(line, stack, hashTable);          
	                if (stack.size() == 0) {
	                    System.out.println();
	                }
	
	                stack.push(current);
	            } 
	            else if (isId(line)) {
	            	stack.push(line);
	            }
	            
	            else {
	                System.out.println("Error: Unexpected character: " + line);
	                throw new Exception("Character not allowed");
	            }
	
	        }
	        System.out.print("Saída = " + stack.pop());
	
	        reader.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("Error when read the file...");
	        e.printStackTrace();
	    }
    }

}