package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anand on 1/17/2015.
 */
public class UpdateRemove {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB database = client.getDB("course");
        DBCollection collection = database.getCollection("UpdateRemove");

        collection.drop();
        List<String> names = Arrays.asList("Anand", "Dante", "Kirtan", "Vergil", "Nisu");
        for (String name : names) {
            collection.insert(new BasicDBObject("_id", name));
        }

        // update that replaces whole collection
        //.............start...............
        collection.update(new BasicDBObject("_id", "Dante"), new BasicDBObject("age", 22));
        collection.update(new BasicDBObject("_id", "Dante"), new BasicDBObject("Language", "Devil"));
        // age get replace by language
        //..............Ends...............

        // update that does not replaces whole collection
        //.............start...............
        collection.update(new BasicDBObject("_id", "Anand"), new BasicDBObject("age", 22));
        collection.update(new BasicDBObject("_id", "Anand"),
                new BasicDBObject("$set", new BasicDBObject("Language", "Devil")));
        //..............Ends...............

        // update collection if not there then insert document
        //.............start...............
        collection.update(new BasicDBObject("_id", "Mongo"), new BasicDBObject("age", 22), true, false);
        collection.update(new BasicDBObject("_id", "Mongo"),
                new BasicDBObject("$set", new BasicDBObject("Language", "Devil")));
        // third parameter ture is for upsert
        //..............Ends...............

        // update all document in collection
        //.............start...............
        collection.update(new BasicDBObject(),
                new BasicDBObject("$set", new BasicDBObject("title", "Gamer")), false, true);
        // fourth parameter ture is for update all
        //..............Ends...............

        // remove document that matches parameter
        collection.remove(new BasicDBObject("_id", "Mongo"));
        // collection.remove(new BasicDBObject()); removes all the document

        QueryBuilder builder = new QueryBuilder().start();
        DBCursor cursor = collection.find(builder.get());

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
