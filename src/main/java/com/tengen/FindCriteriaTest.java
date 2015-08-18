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

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;

public class FindCriteriaTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        MongoDatabase mongoDatabase = client.getDatabase("student");
        MongoCollection<Document> collection = mongoDatabase.getCollection("grades");


     //   collection.drop();
        // insert 10 documents with two random integers
//        for (int i = 0; i < 10; i++) {
//            collection.insertOne(
//                    new Document("x", new Random().nextInt(2))
//                            .append("y", new Random().nextInt(100)));
//        }

//        QueryBuilder builder = QueryBuilder.start("x").is(0)
//                .and("y").greaterThan(10).lessThan(70);
//        DBObject query = new BasicDBObject("x", 0)
//                .append("y", new BasicDBObject("$gt", 10).append("$lt", 90));
//        Bson filter = and(eq("x", 0),gt("y",10));
//
        Bson filter = eq("type", "homework");
        Bson sort = and(eq("student_id",1), eq("score", -1));



        System.out.println("\nFind all: ");
        List<Document> all = collection.find(filter).sort(sort).into(new ArrayList<Document>());

        System.out.println("Size all: " + all.size());

        int lastStudentId=-1;
        Document lastCur = null;

        for(Document cur : all) {
        //    System.out.println(cur.getInteger("student_id"));
            System.out.println("BEFORE DELETE cur.getInteger student_id:" + cur.getInteger("student_id") + " lastStudentId: " + lastStudentId);
            if(cur.getInteger("student_id") != lastStudentId && lastStudentId != -1) {
                //remove lastId
                //Bson filterDelete = and(eq("student_id", lastStudentId), eq("type", "homework"));
                System.out.println("DELETE        cur.getInteger student_id:" + cur.getInteger("student_id") + " lastStudentId: " + lastStudentId);
                collection.deleteOne(lastCur);

            }
           // lastId = cur.getString("_id");
            lastStudentId = cur.getInteger("student_id");
            lastCur = cur;

        }
        collection.deleteOne(lastCur);

        System.out.println("\nCount:");
        long count = collection.count(filter);
        System.out.println(count);

    }
}
