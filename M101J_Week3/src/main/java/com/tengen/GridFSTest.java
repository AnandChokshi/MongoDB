package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Anand on 1/25/2015.
 */
public class GridFSTest {
    public static void main(String[] args) throws IOException {
        MongoClient client = new MongoClient();
        DB database = client.getDB("course");
        FileInputStream inputStream = null;

        GridFS videos = new GridFS(database, "videos"); // just create a object which let you manipulate GirdFS
        // collection name videos is created in database

        try {
            inputStream = new FileInputStream("video.mp4");
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file");
            System.exit(1);
        }

        GridFSInputFile video = videos.createFile(inputStream, "video.mp4"); // filename is provided here

        // Create metadata for file
        BasicDBObject meta = new BasicDBObject("description", "suits episode");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("Harvey");
        tags.add("Mike");
        tags.add("sccotie");
        meta.append("tags", tags);

        video.setMetaData(meta);
        video.save();

        System.out.println("Object ID in file Collection : " + video.get("_id"));

        System.out.println("File saved to database");
        System.out.println("Read file from database");

        GridFSDBFile gridFile = videos.findOne(new BasicDBObject("filename", "video.mp4"));
        FileOutputStream outputStream = new FileOutputStream("video_copy.mp4");
        gridFile.writeTo(outputStream);

        System.out.println("File is read from database");
    }
}
