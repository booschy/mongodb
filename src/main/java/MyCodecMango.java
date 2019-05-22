import DAO.Address;
import DAO.User;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.not;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MyCodecMango {
    private MongoCollection collection;
    private Block<User> printBlock;

    public MyCodecMango(){
        CodecRegistry pogoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = new MongoClient("localhost",
                MongoClientOptions.builder().codecRegistry(pogoCodecRegistry).build());
        MongoDatabase database = mongoClient.getDatabase("user_db");
        database = database.withCodecRegistry(pogoCodecRegistry);

        collection = database.getCollection("user", User.class);
        collection = collection.withCodecRegistry(pogoCodecRegistry);

        printBlock = new Block<User>() {
            public void apply(User user) {
                System.out.println(user);
            }
        };
    }

    public void insertPojo(){
        User user = new User("M",3, new Address("S1","TM","123"));
        collection.insertOne(user);
    }
    public void insertList(){
        List users = asList(
                            new User("u1",2,new Address("s1","TM","123")),
                            new User("u2",5,new Address("s2","CJ","CJ1")),
                            new User("u3",18,new Address("s3","B",null))
        );
        collection.insertMany(users);

    }

    public void queryCollection(){
        collection.find().forEach(printBlock);

        System.out.println("User din TM: ");
        Object obj = collection.find(eq("address.city","TM")).first();
        System.out.println(obj);

        System.out.println("Users with age>5: ");
        collection.find(gt("age",5)).forEach(printBlock);
    }

    public void updateDoc(){

        collection.updateOne(eq("name","M"),combine(set("age",29),set("name","M S")));
        collection.find().forEach(printBlock);
    }

    public void updateDocs(){
        UpdateResult updateResult = collection.updateMany(not(eq("address.zip",null)),set("address.zip", null));
        System.out.println(updateResult.getModifiedCount());
        collection.find().forEach(printBlock);
    }

    public void replaceObj(){
        Object obj = collection.find(eq("name","M S")).first();
        collection.updateOne(eq("name","M S"), set("name","Martinica"));
        collection.find().forEach(printBlock);
        collection.replaceOne(eq("name","Martinica"),obj);
        System.out.println("After replace: ");
        collection.find().forEach(printBlock);
    }

    public void deleteDoc(){
        DeleteResult deleteResult = collection.deleteOne(eq("address.city","B"));
        System.out.println(deleteResult.getDeletedCount());
        collection.find().forEach(printBlock);
    }
    public void deleteMany(){
        collection.deleteMany(eq("address.city","CJ"));
        collection.find().forEach(printBlock);
    }

}
