
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


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

            System.out.println("Distinct tweeter users: " + query.q1(db));
            System.out.println("Most active users? ");
            executeQuery(query.mostActive());
            System.out.println("Most grumpy?");
            executeQuery(query.mostGrumpy());
            System.out.println("Most happy?");
            executeQuery((query.mostHappy()));
            System.out.println("Most mentioned?");
            executeQuery(query.mostMentioned());



        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
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