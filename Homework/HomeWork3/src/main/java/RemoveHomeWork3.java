import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Anand on 1/26/2015.
 */
public class RemoveHomeWork3 {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DBCollection collection = client.getDB("school").getCollection("students");

        DBCursor cursor = collection.find(new BasicDBObject(),
                new BasicDBObject("scores", true));

        try {
            while (cursor.hasNext()) {
                BasicDBObject cur = (BasicDBObject) cursor.next();
                ArrayList<BasicDBObject> scores = (ArrayList<BasicDBObject>) cur.get("scores");
                int lowHomwWork = 0;
                int index = 0, removeIndex = 0;

                for (BasicDBObject score : scores) {
                    if ("homework".equals(score.get("type"))) {
                        if (lowHomwWork == 0) {
                            removeIndex = index;
                            lowHomwWork = score.getInt("score");
                        }
                        if (score.getInt("score") < lowHomwWork) {
                            removeIndex = index;
                            lowHomwWork = score.getInt("score");
                        }
                    }
                    index++;
                }
                scores.remove(removeIndex);
                collection.update(new BasicDBObject("_id",cur.getInt("_id")),
                        new BasicDBObject("$set",new BasicDBObject("scores",scores)));
            }
        } finally {
            cursor.close();
        }
    }
}
