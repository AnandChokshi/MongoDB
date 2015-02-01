import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by Anand on 1/19/2015.
 */
public class RemoveHomeWork2 {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB database = client.getDB("students");
        DBCollection collection = database.getCollection("grades");

        DBCursor cursor = collection.find(new BasicDBObject("type","homework"))
                .sort(new BasicDBObject("student_id", 1).append("score", 1));

        while(cursor.hasNext()){
            DBObject doc = cursor.next();
            Object id = doc.get("_id");
            collection.remove(new BasicDBObject("_id",id));
            cursor.next();
        }
    }
}
