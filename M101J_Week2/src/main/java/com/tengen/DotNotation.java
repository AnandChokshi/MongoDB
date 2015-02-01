package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created by Anand on 1/17/2015.
 */
public class DotNotation {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB database = client.getDB("course");
        DBCollection collection = database.getCollection("DotNotationTest");

        Random rand = new Random();

        collection.drop();
        for (int i = 0; i < 10; i++) {
            collection.insert(
                    new BasicDBObject("_id", i)
                            .append("Start",
                                    new BasicDBObject("x", rand.nextInt(90) + 10)
                                            .append("y", rand.nextInt(90) + 10))
                            .append("End",
                                    new BasicDBObject("x", rand.nextInt(90) + 10)
                                            .append("y", rand.nextInt(90) + 10))
            );
        }

        QueryBuilder query = new QueryBuilder().start("Start.x").greaterThan(50);
        DBCursor cursor = collection.find(query.get(),
                new BasicDBObject("Start.y", true));

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
