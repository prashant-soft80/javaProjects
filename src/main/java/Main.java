import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import example.sample.Student;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Yo what's up human! Oh you think you're fancy");
        List<Student> students = new ArrayList<>();
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("example");
        MongoCollection mongoCollection = db.getCollection("poc");

        Bson searchQuery = new BsonDocument("firstName", new BsonString("Prashant"));
        FindIterable cursor = mongoCollection.find(searchQuery);

        for (Object o : cursor) {
            Document doc = (Document) o;
            Student student = new Student.Builder(doc).address(doc).phoneContact(doc).email(doc).build();
            students.add(student);
        }
        students.forEach(System.out::println);
    }
}