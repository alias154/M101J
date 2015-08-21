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

package tengen;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class HomeWork31 {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        Morphia morphia = new Morphia();
        Datastore ds = new Morphia().createDatastore(client, "school");

        morphia.map(Students.class);

        List<Students> all = ds.find(Students.class).asList();
        for(Students cur : all) {
            ArrayList<Scores> scores = cur.getScores();
            scores = removeScore(scores);
            ds.delete(cur);
            ds.save(new Students(cur.getId(), cur.getName(), scores));
        }


    }

    private static ArrayList<Scores> removeScore(ArrayList<Scores> st) {

        double temScore = -1;
        Scores tempSc = null;

        for(Scores sc : st) {
            if(sc.getType().equals("homework")) {
                if(temScore == -1) {
                    temScore = sc.getScore();
                    tempSc = sc;
                } else if(temScore > sc.getScore()) {
                    tempSc = sc;
                }

            }
        }
        st.remove(tempSc);
        return st;
    }
}

@Entity("students")
class Students {

    @Id
    private int id;

    private String name;

    private ArrayList<Scores> scores;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    public ArrayList<Scores> getScores() {
        return scores;
    }
    public Students() {}

    public Students(int id, String name, ArrayList<Scores> translations) {
        this.id = id;
        this.name = name;
        this.scores = translations;
    }


}

@Entity
class Scores {

    private String type;

    private double score;

    public Scores() {}

    public Scores(String type, double score) {
        this.type = type;
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "type='" + type + '\'' +
                ", score=" + score +
                '}';
    }
}




