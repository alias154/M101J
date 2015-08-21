/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tengen;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        MongoDatabase mongoDatabase = client.getDatabase("course");
        MongoCollection<Document> collection = mongoDatabase.getCollection("findTest");


        collection.drop();

        // insert 10 documents with a random integer as the value of field "x"
        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document("x", new Random().nextInt(100)));
        }

        System.out.println("Find one:");
        Document one = collection.find().first();
        System.out.println(one);

        System.out.println("\nFind all: ");
        List<Document> all = collection.find().into(new ArrayList<Document>());
        System.out.println(all);

        System.out.println("\nFind all cursor: ");
        MongoCursor<Document> cursor = collection.find().iterator();
        try {

            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }

        } finally {
            cursor.close();
        }


//        System.out.println("\nFind all: ");
//        DBCursor cursor = collection.find();
//        try {
//          while (cursor.hasNext()) {
//              DBObject cur = cursor.next();
//              System.out.println(cur);
//          }
//        } finally {
//            cursor.close();
//        }
//
        System.out.println("\nCount:");
        long count = collection.count();
        System.out.println(count);
    }
}
