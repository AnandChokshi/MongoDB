package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created by Anand on 1/17/2015.
 */
public class SortLimitSkip {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB database = client.getDB("course");
        DBCollection collection = database.getCollection("SortLimitSkip");

        Random rand = new Random();

        collection.drop();
        for (int i = 0; i < 10; i++) {
            collection.insert(
                    new BasicDBObject("_id", i)
                            .append("Start",
                                    new BasicDBObject("x", rand.nextInt(2))
                                            .append("y", rand.nextInt(90) + 10))
                            .append("End",
                                    new BasicDBObject("x", rand.nextInt(2))
                                            .append("y", rand.nextInt(90) + 10))
            );
        }

        QueryBuilder query = new QueryBuilder().start();
        DBCursor cursor = collection.find()
                .sort(new BasicDBObject("Start.x", 1).append("Start.y", -1)).skip(2).limit(7);

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
