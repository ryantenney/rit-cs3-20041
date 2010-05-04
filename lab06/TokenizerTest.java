/*
 * TokenizerTest.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

import java.io.*;

/**
 * A class that shows how to use a StreamTokenizer
 * to tokenize Boolean expressions.
 *
 * @author     Paul Tymann
 */

class TokenizerTest {

    /**
     * Use a stream tokenizer to read a boolean expression from
     * standard input.
     *
     * @param args[] command line arguments (ignored)
     */

    public static void main( String args[] ) {

	// Create a StreamTokenizer attached to the keyboard

        StreamTokenizer lex = 
                new StreamTokenizer( new InputStreamReader( System.in ) );

	// We need a try block because the nextToken() method
	// may throw an IOException

        try {
	    // Get the next token from the keyboard

            while ( lex.nextToken() != StreamTokenizer.TT_EOF ) {

		// Use the ttype member in the tokenizer to find out
		// what type of token was read.
		//
		// If it was an operator, then ttype will have a copy of
		// the operator
		//
		// If it was a variable, the tokenizer will treat that as
		// a word and ttype will be equal to TT_WORD
		//
		// Anything else is not part of a simple boolean expression

    	        switch ( lex.ttype ) {
                case '&':
	            System.out.println( "AND" );
	            break;
	        case '|':
	            System.out.println( "OR" );
	            break;
	        case '!':
	            System.out.println( "NOT" );
	            break;
                case StreamTokenizer.TT_WORD:
	            System.out.println( "VARIABLE " + lex.sval );
	            break;
                default:
	            System.out.println( "UNEXPECTED TOKEN " + lex.ttype );
	            break;
                }
            }
        }
        catch  ( IOException e ) {
            System.err.println( e.getMessage() );
        }
   }

}  // TokenizerTest

