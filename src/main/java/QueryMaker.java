import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonValue;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CosticaTeodor on 28/04/2017.
 */
public class QueryMaker {

    //How many Twitter users are in our database?
    public Integer q1 (MongoDatabase db)
    {
        ArrayList<BsonValue> count = db.getCollection("test").distinct("user", BsonValue.class).
                filter(new Document("user", new Document("$ne",null))).
                into(new ArrayList<BsonValue>());
        return count.size();
    }

    //Who are the most active Twitter users (top ten)?
    public String mostActive(){
        return "db.test.aggregate(\n" +
                "      [\n" +
                "        {\n" +
                "          $group: {\n" +
                "            _id: {\n" +
                "              user: '$user',\n" +
                "              tweetId: '$id',\n" +
                "            },\n" +
                "          },\n" +
                "        },\n" +
                "        {\n" +
                "          $group: {\n" +
                "            _id: '$_id.user',\n" +
                "            count: { $sum: 1 },\n" +
                "          },\n" +
                "        },\n" +
                "        {\n" +
                "          $sort: { count: -1 },\n" +
                "        },\n" +
                "        {\n" +
                "          $limit: 10,\n" +
                "        },\n" +
                "      ],\n" +
                "      { allowDiskUse: true })";

    }


    //Which Twitter users link the most to other Twitter users? (Provide the top ten.)
    public String getUserLinked(){
        return "0";
    }

    //Who is are the most mentioned Twitter users? (Provide the top five.)

    public String mostMentioned(){
        return "db.test.aggregate([" +
                "         {" +
                "           $match: { " +
                "            text: new RegExp('@\\w+', 'ig'), " +
                "          }," +
                "         }, " +
                "        {           $group: { _id: '$user', tweets: { $sum: 1 } }," +
                "         }," +
                "         {           $sort: { tweets: -1 }," +
                "         },         {           $limit: 5,         }," +
                "       ],       { allowDiskUse: true })";
    }

    //Who are the five most grumpy (most negative tweets)?
    //Gets all the ratings and dsplays them asscending (1)
    public String mostGrumpy(){
        return "db.test.aggregate( [    " +
                "{     $group: {       _id: '$user'," +
                "       average_polarity: { $avg: '$polarity' }," +
                "     }," +
                "   }," +
                "   {     " +
                "$sort: { average_polarity: 1 },  " +
                " }," +
                "   {     " +
                "$limit: 5," +
                "   }," +
                "]," +
                "{ allowDiskUse: true })";
    }

    //Most happy?
    //Gets all the ratings and dsplays them descending (-1)
    public String mostHappy(){
        return "db.test.aggregate( [" +
                "    {     $group: {       _id: '$user'," +
                "       average_polarity: { $avg: '$polarity' }," +
                "     }," +
                "   }, " +
                "  {     $sort: { average_polarity: -1 }," +
                "   }," +
                "   {     $limit: 5,   }" +
                ",]" +
                ",{ allowDiskUse: true })";
    }

}
