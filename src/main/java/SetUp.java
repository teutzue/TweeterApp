/**
 * Created by CosticaTeodor on 30/04/2017.
 */
public class SetUp 
{
	public static void setUp()
	{
		//IMPORT CSV FILE
        Runtime r = Runtime.getRuntime();
        Process p = null;
        String command = "mongoimport -d test -c test --type csv --file " +
                "/Users/CosticaTeodor/Desktop/training.1600000.processed.noemoticon.csv --headerline";
        try {
            p = r.exec(command);
            System.out.println("Reading csv into Database");

        } catch (Exception e){
            System.out.println("Error executing " + command + e.toString());
        }
	}
}
