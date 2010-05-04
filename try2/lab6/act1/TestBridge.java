import java.util.*;

public class TestBridge {

    public static void main( String[] args ) {
        try {
            //if ( args.length != 2 ) throw new Exception( "" );

            //int num, interval;
            //num = Integer.parseInt( args[ 0 ] );
            //interval = Integer.parseInt( args[ 1 ] );

            //List<Woolie> populi = new ArrayList<Woolie>();
            //for ( int i = 0; i < num; i++ ) {
                
            //    populi.add( 
            //}
            
            //ThreadGroup woolies = new ThreadGroup( "Woolies" );

            Bridge bridge = new Bridge();
            Troll troll = bridge.getTroll();

            Woolie woolie1 = new Woolie( "number1", 6, 
                   "Merctran", bridge );
            Woolie woolie2 = new Woolie( "number2", 3, 
                   "Sicstine", bridge );
            Woolie woolie3 = new Woolie( "number3", 15, 
                   "Sicstine", bridge );
            Woolie woolie4 = new Woolie( "number4", 5, 
                   "Merctran", bridge );
            Woolie woolie5 = new Woolie( "number5", 9, 
                   "Sicstine", bridge );
            Woolie woolie6 = new Woolie( "number6", 7, 
                   "Merctran", bridge );
            Woolie woolie7 = new Woolie( "number7", 11, 
                   "Sicstine", bridge );

            woolie1.start();
            woolie2.start();
            woolie3.start();
            woolie4.start();
            woolie5.start();
            woolie6.start();
            woolie7.start();

        } catch ( Exception ex ) {
            System.err.println("usage: java TestBridge <numWoolies> "
                   + "<interval>" );
            System.exit( 1 );
        }
    }
}
