/*
 * These are special tags for the source code control that you will use to
 * track changes to your program files.  You will see these in more detail
 * in next week's lab.
 *
 * $Id:$
 *
 * $Log:$
 */

/* As you see above comments in a Java file look like C++ comments.  There
 * are multi-line comments that start with /* and end with */

// You can also have single line comments that begin with two slashes

/*
 * Note that when you edit with emacs the different types of fields are
 * coded with different colors.  This can sometimes help you find syntax
 * errors in your programs.
 *
 * Emacs also does auto-indentation for you which is very useful for
 * keeping consistent formatting in your code.
 */
 
// The import statement is similar to the C++ include statement.
// It specifies that this program file uses classes that are found in
// other locations known as packages.  We will talk about these more
// next week.
import icss236.StringInput;

/**
 *  A first Java program to work with.  It demonstrates different methods
 *  for doing input and output from your program.  There are several
 *  errors in the source code that must be fixed for it to compile and
 *  run correctly.
 *
 *  @version	$Revision:$
 *
 *  @author	Jim Vallino
 */
public class Lab1Act5
{
    /**
     * Just like a C or C++ program your Java program starts running in
     * a method called main.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args)
    {
	/*
	 * Java has a number of primitive data types: int, byte,
	 * short, char, long, float, double, boolean.
	 *
	 * These are all simply defined with the same syntax as C++,
	 * namely, the data type followed by a variable name and
	 * terminated with a ;
	 */
	int i;
	float f;

	// You can declare multiple variables in a single statement.
	int j1, j2, j3;
	double d1, d2, d3;

	// Variables can be initialized as part of the declaration
	int k = 5;

	/*
	 * Strings can be defined as sequences of characters between
	 * double quotes.  The String variable s below is *not* a 
	 * primitive data type.  It is actually an object reference.  We
	 * will talk a lot more about this in the second week of lecture.
	 *
	 * For now loosely consider this similar to the primitive
	 * variables that you saw previously.
	 */
	String s = "A string of characters";
	
	/*
	 * To print values out we make use of the out member of the System
         * class.  This is referred to as System.out.  The syntax is the
	 * same as C++ member access.  The methods you use most often for
	 * printing are print and println.
	 *
	 * Executing a method (function) on an object has syntax like in
	 * C++ where you have
	 *
	 *                 object.method(parameters);
	 *
	 * In the examples below the value to be printed is the parameter.
	 *
	 */
	System.out.println(k);
	System.out.println("k was on a line by itself");

	System.out.print(k);
	System.out.println(" or you can have it share the line");

	// You can concatenate (add) strings including with numeric values
	System.out.println("Put " + s + " in the middle");
	System.out.println("Put " + k + " in the middle");

        // Let's set and then print the value of a float variable.
        // There will be a problem here because real constants are
        // considered double values.  You will need to force this to be
        // a float value.  There are two ways to do this that are
        // exactly the same as in C++.
	
        f = 12.3f;
	System.out.println(f);

	/*
	 * Doing input in java is complicated by the need to handle
	 * exceptions.  We will not deal with that topic until later in
	 * the term.  Until then you can use the getString method in the
	 * StringInput class to get a string from the keyboard.  There
	 * are two versions: one with a prompt string and one without.
	 */
	String num1 = "0", num2 = "0";

	num1 = StringInput.getString("Enter an integer: ");
	
	/*
	 * For generate the "no prompt" message and use
	 * StringInput.getString for the input operation.
	 *

	System.out.println("There will be no prompt for the next integer);
	num2 = StringInput.getString();

	/*
	 * To convert from a String to an int you have to use parseInt in
	 * the Integer class.
	 */
	j1 = Integer.parseInt(num1);
	j2 = Integer.parseInt(num2);
	j3 = j1 + j2;
	
	// Print these strings to System.out.
	String reply = "The sum of " + j1 + " and " + j2 + " is " + j3;
	System.out.println(reply);
    }
}
