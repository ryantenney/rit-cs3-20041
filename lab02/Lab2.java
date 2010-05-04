/*
 * $Id: Lab2.java,v 1.4 2004/09/18 21:48:02 rwt5629 Exp $
 *
 * $Log: Lab2.java,v $
 * Revision 1.4  2004/09/18 21:48:02  rwt5629
 * updated per lab2-4
 *
 * Revision 1.3  2004/09/18 21:30:32  rwt5629
 * nocomment
 *
 * Revision 1.2  2004/09/18 20:36:24  rwt5629
 * No comment-
 *
 * Revision 1.1  2004/09/14 15:30:45  rwt5629
 * Testing stringinput
 *
 */

import icss236.StringInput;

/**
 * Class <code>Lab2</code>:
 * 
 * 
 *
 * @author <a href="mailto:rwt5629@cs.rit.edu">Ryan W Tenney</a>
 * @version 1.0
 */
public class Lab2
{
    /**
     * <code>main</code>
     *
     * @param args a <code>String[]</code> value
     */
    public static void main(String[] args)
    {
        double sideOne = 0, sideTwo = 0;
        double hypotenuse = 0, diameter = 0;
        double angleA = 0, angleB = 0, angleC = 0;
        double lawOfSines = 0;
    
        // prompt user to input the length of side one of the triangle
	    System.out.print("Enter side one: ");
        sideOne = Double.parseDouble(StringInput.getString());
        
        // prompt user to input the length of side two of the triangle
	    System.out.print("Enter side two: ");
        sideTwo = Double.parseDouble(StringInput.getString());
        
        // compute and output length of the hypotenuse
        hypotenuse = Math.sqrt(Math.pow(sideOne, 2) + Math.pow(sideTwo, 2));
        System.out.print("Hypotenuse = ");
        System.out.println(hypotenuse);
        
        // compute and output angles
        angleA = Math.PI / 2;       // angle opposite the hypotenuse is 90 degrees
        lawOfSines = Math.sin(Math.PI / 2) / hypotenuse;
        angleB = Math.asin(lawOfSines * sideOne) * (180 / Math.PI);
        angleC = Math.asin(lawOfSines * sideTwo) * (180 / Math.PI);
        System.out.print("Angles = ");
        if (angleB < angleC)
        {
            System.out.print(angleB);
            System.out.print(" ");
            System.out.println(angleC);
        }
        else
        {
            System.out.print(angleC);
            System.out.print(" ");
            System.out.println(angleB);
        }
                
        // prompt user to input the diameter of a circle, and compute circumference and area
        System.out.print("Enter the diameter of a circle: ");
        diameter = Double.parseDouble(StringInput.getString());
        System.out.print("Circumference = ");
        System.out.print(diameter * Math.PI);
        System.out.print(" area = ");
        System.out.println(Math.pow(diameter / 2, 2) * Math.PI);
    }
	
}