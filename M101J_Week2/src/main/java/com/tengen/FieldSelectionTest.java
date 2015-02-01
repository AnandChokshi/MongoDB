package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created by Anand on 1/15/2015.
 */
public class FieldSelectionTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB database = client.getDB("course");
        DBCollection collection = database.getCollection("fieldSelectionTest");

        collection.drop();
        for (int i = 0; i < 10; i++) {
            collection.insert(new BasicDBObject("x", new Random().nextInt(2))
                    .append("y", new Random().nextInt(100))
                    .append("z", new Random().nextInt(1000)));
        }

        QueryBuilder builder = new QueryBuilder().start("x").is(0)
                .and("y").greaterThan(10).lessThan(70);

        DBObject query = new BasicDBObject("x", 0)
                .append("y", new BasicDBObject("$gt", 10).append("$lt", 90));

        System.out.println("Count:");
        long count = collection.count(builder.get());
        System.out.println(count);

        System.out.println("\nFind All:");
        DBCursor cursor = collection.find(builder.get(), new BasicDBObject("y", true)
                .append("_id", false));
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
    }
}
