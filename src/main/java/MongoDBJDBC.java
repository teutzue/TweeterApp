
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class MongoDBJDBC {

    static QueryMaker query = new QueryMaker();

    public static void main( String args[] ) {

        try{

            // To connect to mongodb server
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // Now connect to your databases
            MongoDatabase db  = mongoClient.getDatabase( "test" );
            System.out.println("Connect to database successfully!");
            String dbName = db.getName();
            System.out.println("Connected to mongodb without credentials");
            System.out.println("Authentication on database name: "+dbName);

            //db.createCollection("test");
            //System.out.println("Collection created successfully");

            //IMPORT CSV FILE
            /*Runtime r = Runtime.getRuntime();
            Process p = null;
            String command = "mongoimport -d test -c test --type csv --file " +
                    "/Users/CosticaTeodor/Desktop/training.1600000.processed.noemoticon.csv --headerline";
            try {
                p = r.exec(command);
                System.out.println("Reading csv into Database");

            } catch (Exception e){
                System.out.println("Error executing " + command + e.toString());
            }*/

            //findUsersThatLink(); 5th question
            Scanner sc = new Scanner(System.in);
            Thread.sleep(500);
            int input;
            do
            {
            	menu();
            	input = sc.nextInt();
            	if(input==1)
            	{
            		System.out.println("Distinct tweeter users: " + query.q1(db));
            	}
            	else if (input==2) 
            	{
            		executeQuery(query.q2());
				}
            	else if (input==3) 
            	{
            		System.out.println("Most mentioned?");
                    executeQuery(query.q3());
				}
            	else if (input==4) 
            	{
            		System.out.println("Most active users? ");
                    executeQuery(query.q4());
            	}
            	else if (input==5) 
            	{
            		System.out.println("Most grumpy?");
                    executeQuery(query.q5a());
            	}
            	else if (input==6) 
            	{
            		System.out.println("Most happy?");
                    executeQuery((query.q5b()));
            	}
            	else if (input==7) 
            	{
            		break;
            	}
            	Thread.sleep(3000);
            	
            } while(true);
            
            mongoClient.close();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    private static void menu()
    {
    	System.out.println("---------------------------------------------------------");
    	System.out.println("Please enter number from 1 to 7 to execute following:");
        System.out.println("1. Number of Twitter users in database.");
        System.out.println("2. User that links to most of Twitter users (Top 10).");
        System.out.println("3. Most mentioned user(Top 5).");
        System.out.println("4. Most active users (Top 10).");
        System.out.println("5. Five most negative tweets.");
        System.out.println("6. Five most happy tweets.");
        System.out.println("7. Terminate application.");
        System.out.println("---------------------------------------------------------");
    }

    public static void executeQuery(String q) {
        // implement the logic
        Runtime rt = Runtime.getRuntime();
        try {

            // the query you want to run in mongo, you can get it
            // from a file using a FileReader


            // the database name you need to use
            String db = "test";

            // run a command from terminal. this line is equivalent to
            // mongo database --eval "db.col.find()"
            // it calls the mongo binary and execute the javascript you
            // passed in eval parameter. It should work for both unix and
            // windows
            Process pr = rt.exec(new String[]{"mongo", db, "--eval", q});
            // read the output of the command
            InputStream in = pr.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            int countLines = 1;
            while ((line = reader.readLine()) != null) {
                if (countLines > 2) {
                    out.append("\n" + line);
                }
                countLines++;
            }

            // print the command and close outputstream reader
            System.out.println(out.toString());
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}

     /*String query = "db.test.aggregate([\n" +
                    "      { $match: { polarity: 0 } },\n" +
                    "      { $group: { _id: '$user', count: { $sum: 1 } } },\n" +
                    "      { $sort: { count: -1 } },\n" +
                    "      { $limit: 5 }\n" +
                    "  ]);";*/