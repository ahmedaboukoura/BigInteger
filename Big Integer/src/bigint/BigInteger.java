package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		
		/* IMPLEMENT THIS METHOD */
		
		// following line is a placeholder for compilation
		BigInteger int1 = new BigInteger(); 
	       if(integer.contains("-")){
	    	int1.negative = true;
	       }
		String Modified = int1.IntModifited(integer);
		   if(!Modified.matches("^[0-9]+$")){
	       throw new IllegalArgumentException("input is incorrectly formatted");
		   }
		   if(Modified == "0"||Modified =="+0"||Modified=="-0"){
			return int1;
		   }
		for(int i = 0;i < Modified.length();i++){
		int1.front = new DigitNode(Character.getNumericValue(Modified.charAt(i)), int1.front);
		int1.numDigits ++;
		}
		return int1; 
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
		
		/* IMPLEMENT THIS METHOD */
		
		// following line is a placeholder for compilation
		BigInteger result = new BigInteger();
		DigitNode firstNum = first.front;
		DigitNode secondNum = second.front;
		DigitNode tail = null;
		if((first.negative && !second.negative)||(!first.negative && second.negative) ){
			DigitNode subtrahendNode;
			DigitNode minuendNode;
			if(first.numDigits>=second.numDigits){
				 minuendNode = first.front;
				 subtrahendNode = second.front;
			}else{
				 subtrahendNode = first.front;
				 minuendNode = second.front;
			}
			if(first.numDigits > second.numDigits && first.negative ){
				result.negative = true;
			}if(second.numDigits > first.numDigits && second.negative ){
				result.negative = true;
			}
			if(first.numDigits == second.numDigits){
				DigitNode firstNumForSignLoop = first.front;
				DigitNode SecondNumForSignLoop = second.front;
				boolean isFirstBigger = false;
				for(int i =0;i <first.numDigits;i++){
					if(firstNumForSignLoop.digit >SecondNumForSignLoop.digit){
						isFirstBigger = true;
					}else{
						isFirstBigger = false;
					}
					firstNumForSignLoop = firstNumForSignLoop.next;
					SecondNumForSignLoop = SecondNumForSignLoop.next;
				}
				if(isFirstBigger && first.negative){
					result.negative = true;
				}
				if(!isFirstBigger && second.negative){
					result.negative = true;
				}
			}

			boolean borrow = false;
			while(minuendNode != null || subtrahendNode != null ){
			int subtract = 0; 
			if(borrow){
				if(minuendNode.digit == 0){
					minuendNode.digit = 9;
					borrow = true;
				}else{
					minuendNode.digit = minuendNode.digit - 1;
					borrow = false;
				}
			}
			if(subtrahendNode != null){
			     if(minuendNode.digit >= subtrahendNode.digit){
				    subtract =  (minuendNode.digit - subtrahendNode.digit) ;
			     }else{
					subtract = (10 - subtrahendNode.digit) + minuendNode.digit ;
				    borrow = true;
			}}else{
				
				subtract =subtract + minuendNode.digit ;
				
			}
			DigitNode resultNode = new DigitNode(subtract,null);
			if(result.front == null){
				result.front = resultNode;
				tail = resultNode;
				
			}else{
				tail.next = resultNode;
				tail = resultNode;
			}
			if(first != null){
			minuendNode = minuendNode.next;
			}
			if(subtrahendNode!= null){
			subtrahendNode = subtrahendNode.next;
			}
			}
		 return result;
		}else{
		int carry = 0; 
		while(carry != 0 || firstNum != null|| secondNum != null){
			int sum = 0;
			int Firstnumdigit = 0;
			int Secondnumdigit = 0;
			if(firstNum != null){
			Firstnumdigit = firstNum.digit; 
			}
			if(secondNum != null){
			Secondnumdigit = secondNum.digit;
			}
			sum = Firstnumdigit + Secondnumdigit + carry;
			if(sum >= 10){
				sum = sum -10;
				carry = 1;
			}else{
				carry = 0;
			}
			DigitNode resultNode = new DigitNode(sum,null);
			if(result.front == null){
				result.front = resultNode;
				tail = resultNode;
				
			}else{
				tail.next = resultNode;
				tail = resultNode;
			}
            if ( firstNum != null ) {
            	firstNum = firstNum.next;
            }

            if ( secondNum != null ) {
            	secondNum = secondNum.next;
            }
		}
        if(first.negative && second.negative){
        	result.negative = true;
        }
        return result;
		}
	}
	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
       BigInteger firstNum = first;
       BigInteger SecondNum = second;
       BigInteger NumberNegativeOne = BigInteger.parse("-1");
       BigInteger result = new BigInteger();
       while(firstNum.toString() != "0") {
    	   result = BigInteger.add(result, SecondNum);
    	   firstNum = BigInteger.add(firstNum, NumberNegativeOne);
       }
       return result;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	private String IntModifited(String s){
		String NewString = s.trim();
		if(NewString.charAt(0)=='-'||NewString.charAt(0)=='+'){
			return NewString.substring(1).replaceFirst("^0+", "");
		}else{
			
		return NewString.replaceFirst("^0+", "");
		}
	}
}
