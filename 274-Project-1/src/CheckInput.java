import java.util.Scanner;

/** 
 * Static functions used to check console input for validity.
 *
 * Use:	Place CheckInput class in the same project folder as your code.
 *	Call CheckInput functions from your code using "CheckInput."
 *
 * Example:  int num = CheckInput.getInt();
 *
 * @author Shannon Cleary 2018
 */
public class CheckInput {
	
	/**
	 * Checks if the inputted value is an integer.
     * will keep looping until given back a valid input
     * dispalys message to user if input is invalid
	 * @return the valid input
	 */
	public static int getInt() {
		Scanner in = new Scanner( System.in );
		int input = 0;
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextInt() ) {
				input = in.nextInt();
				valid = true;
			} else {
				in.next(); //clear invalid string
				System.out.print( "Please enter an integer: " );
			}
		}
		return input;
	}
	
	/**
	 * Checks if the inputted value is an integer and 
	 * within the specified range (ex: 1-10).
     * will keep looping until valid answer.
     * will tell user if their answer is out of range or invalid
	 * @param low lower bound of the range.
	 * @param high upper bound of the range.
	 * @return the valid input.
	 */
	public static int getIntRange( int low, int high ) {
		Scanner in = new Scanner( System.in );
		int input = 0;
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextInt() ) {
				input = in.nextInt();
				if( input <= high && input >= low ) {
					valid = true;
				} else {
					System.out.print( "Please enter an integer within the range: " );
				}
			} else {
				in.next(); //clear invalid string
				System.out.print( "Please enter an integer: " );
			}
		}
		return input;
	}
	/**
	 * Checks if the inputted value is a non-negative integer.
     * loops til givena  positive int, telling the user if its not
	 * @return the valid input
	 */
	public static int getPositiveInt( ) {
		Scanner in = new Scanner( System.in );
		int input = 0;
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextInt() ) {
				input = in.nextInt();
				if( input >= 0 ) {
					valid = true;
				} else {
					System.out.print( "Please enter a positive integer within the range: " );
				}
			} else {
				in.next(); //clear invalid string
				System.out.print( "Please enter an integer: " );
			}
		}
		return input;
	}

	/**
	 * Checks if the inputted value is a double.
	 * @return the valid input.
	 */
	public static double getDouble() {
		Scanner in = new Scanner( System.in );
		double input = 0;
		boolean valid = false;
		while( !valid ) {
			if( in.hasNextDouble() ) {
				input = in.nextDouble();
				valid = true;
			} else {
				in.next(); //clear invalid string
				System.out.print( "Please enter a valid number: " );
			}
		}
		return input;
	}
	
	/**
	 * Takes in a string from the user.
	 * @return the inputted String.
	 */
	public static String getString() {
		Scanner in = new Scanner( System.in );
		String input = in.nextLine();
		return input;
	}

	/**
	 * Takes in a yes/no from the user.
	 * @return boolean true/false;
	 */
	public static boolean getYesNo(){
		boolean valid = false;
		while( !valid ) {
			String s = getString();
			if( s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y") ) {
				return true;
			} else if( s.equalsIgnoreCase("no") || s.equalsIgnoreCase("n") ) {
				return false;
			} else {
				System.out.print( "Please enter 'yes' or 'no': " );
			}
		}
		return false;
	}	
}
