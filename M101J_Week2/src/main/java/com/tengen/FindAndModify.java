package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by Anand on 1/18/2015.
 */
public class FindAndModify {
    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = createCollection();
        collection.drop();

        final String counterId = "abc";
        int first, numNeeded;

        numNeeded = 2;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));

        numNeeded = 3;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));

        numNeeded = 10;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));

        System.out.println();
    }

    public static int getRange(String id, int range, DBCollection collection) {
        DBObject doc = collection.findAndModify(
                new BasicDBObject("_id", id), null, null, false,
                new BasicDBObject("$inc", new BasicDBObject("counter", range)), true, true);
        return (Integer) doc.get("counter") - range + 1;
    }

    private static DBCollection createCollection() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB database = client.getDB("course");
        return database.getCollection("FindAndModify");
    }
}
