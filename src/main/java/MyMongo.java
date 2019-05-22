import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;

public class MyMongo {

    private MongoCollection collection;
    public MyMongo(){
        MongoClient mongoClient = new MongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("courses_db");
        collection = mongoDatabase.getCollection("courses_collection");
    }

    public void insertOneDoc(){
        Document doc = new Document("name","MongoDB").append("type","database").append("versions", Arrays.asList("v1","v2","v3"));
        doc.append("info", new Document("x",1).append("y",2));
        collection.insertOne(doc);
    }

    public void insertManyDocs(){
        List<Document> docs = new ArrayList<Document>();
        for (int i=0;i<100;i++){
            docs.add(new Document("i",i));
        }
        collection.insertMany(docs);
    }
    public void queryCollection(){
        Document doc = (Document) collection.find().first();
        System.out.println(doc.toJson());

    }
    public void findAllDocs(){
        MongoCursor<Document> cursor = collection.find().iterator(); //pentru iterare in lista
        try{
            while(cursor.hasNext()){
                System.out.println(cursor.next().toJson());
            }
        }finally {
            cursor.close();
        }
    }
    public void queryFilter(){
        Document doc = (Document) collection.find(eq("i",3)).first();
        System.out.println(doc.toJson());
        Block<Document> printBlock = new Block<Document>() {
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        };
        collection.find(and(gt("i",50),lte("i",60))).forEach(printBlock);
    }
    public void updateDoc(){
        UpdateResult result = collection.updateOne(eq("i",10),new Document("$set",new Document("i",110)));
        System.out.println(result.getModifiedCount());

    }
    public void updateDocs(){
        UpdateResult result = collection.updateMany(lt("i",100), inc("i",100));
        System.out.println(result.getModifiedCount());
    }
    public void deleteDoc(){
        collection.deleteOne(eq("i",110));
        DeleteResult deleteResult = collection.deleteMany(gte("i",100));
        System.out.println(deleteResult.getDeletedCount());

    }
}
