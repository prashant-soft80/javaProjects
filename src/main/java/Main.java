import com.mongodb.client.FindIterable;
import example.LoadModule;
import example.sample.Student;
import example.services.MongoDBService;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends LoadModule {
    public Main() {
        init();
    }

    public static void main(String[] args) {
        System.out.println("Yo what's up human! Oh you think you're fancy");
        final List<Student> students = new ArrayList<>();
        final Bson searchQuery = new BsonDocument("firstName", new BsonString("Prashant"));
        final MongoDBService mongoDBService = new Main().getInjector().getInstance(MongoDBService.class);
        final FindIterable cursor = mongoDBService.getCollection("poc").find(searchQuery);

        for (Object o : cursor) {
            Document doc = (Document) o;
            AtomicReference<Student.Builder> studentBuilder = new AtomicReference<>(new Student.Builder(doc));
            studentBuilder.get().address(doc);
            studentBuilder.get().phoneContact(doc);
            studentBuilder.get().email(doc);
            final Student student = studentBuilder.get().buildStudent();
            students.add(student);
        }
        students.forEach(System.out::println);
    }
}