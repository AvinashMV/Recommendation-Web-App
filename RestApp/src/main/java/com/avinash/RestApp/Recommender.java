package com.avinash.RestApp;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Recommender {
    private void indexDirectory(IndexWriter writer, File dir) throws IOException {
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(writer, f); // recurse
            } else if (f.getName().endsWith(".txt")) {
                // call indexFile to add the title of the txt file to your index (you can also index html)
                indexFile(writer, f);
            }
        }
    }
    private void indexFile(IndexWriter writer, File f) throws IOException {
        System.out.println("Indexing " + f.getName());
        Document doc = new Document();
        doc.add(new TextField("filename", f.getName(), TextField.Store.YES));


        //open each file to index the content
        try{

            FileInputStream is = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while((line = reader.readLine())!=null){
                stringBuffer.append(line).append("\n");
            }
            reader.close();
            doc.add(new TextField("contents", stringBuffer.toString(), TextField.Store.YES));


        }catch (Exception e) {

            System.out.println("something wrong with indexing content of the files");
        }

        writer.addDocument(doc);

    }



    private List<String> getTopics(String querystr)throws ParseException, IOException{
        // index part ----------------------------------------------------------------------

        String path = new File(".").getCanonicalPath();
        path = path+"/ScrapedData2/";

        File dataDir = new File(path);

        // Check whether the directory to be indexed exists
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            throw new IOException(
                    dataDir + " does not exist or is not a directory");
        }
        Directory indexDir = new RAMDirectory();

        // Specify the analyzer for tokenizing text.
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(indexDir, config);

        // call indexDirectory to add to your index
        // the names of the txt files in dataDir
        indexDirectory(writer, dataDir);
        writer.close();

        Query q = new QueryParser( "contents", analyzer).parse(QueryParser.escape(querystr));

        //Query query = parser.parse(QueryParser.escape(parsedReview));
        int hitsPerPage = 10;
        IndexReader reader = null;



        TopScoreDocCollector collector = null;
        IndexSearcher searcher = null;
        reader = DirectoryReader.open(indexDir);
        searcher = new IndexSearcher(reader);
        collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(q, collector);



        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        System.out.println("Found " + hits.length + " hits.");
        System.out.println();

        List<String> topics = new ArrayList<>();

        BufferedReader input;
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d;
            d = searcher.doc(docId);

            String fileName = path+d.get("filename");

            input = new BufferedReader(new FileReader(fileName));
            String line = input.readLine();
            topics.add(line);
           // System.out.println(line);
            input.close();
            //System.out.println((i + 1) + ". " + d.get("filename"));
        }
        reader.close();
        return topics;
    }

    @RequestMapping("/home")
    public Map<String, List<String>> getAllTopics(){
        String query1 = "answer\tOne way to implement deep copy is to add copy constructors to each associated class. A copy constructor takes an instance of 'this' as its single argument and copies all the values from it. Quite some work, but pretty straightforward and safe. EDIT: note that you don't need to use accessor methods to read fields. You can access all fields directly because the source instance is always of the same type as the instance with the copy constructor. Obvious but might be overlooked. Example: Edit: Note that when using copy constructors you need to know the runtime type of the object you are copying. With the above approach you cannot easily copy a mixed list (you might be able to do it with some reflection code). \tpublic class Order {private long number;public Order() {}/** * Copy constructor */public Order(Order source) {number = source.number;}}public class Customer {private String name;private List<Order> orders = new ArrayList<Order>();public Customer() {}/** * Copy constructor */public Customer(Customer source) {name = source.name;for (Order sourceOrder : source.orders) {orders.add(new Order(sourceOrder));}}public String getName() {return name;}public void setName(String name) {this.name = name;}}\t\tClassDefinition PublicClassSpecifier PrivateFieldSpecifier InstanceField LongDataType SimpleVariable ConstructorDefinition PublicConstructorSpecifier FormalMethodParameter SimpleAssignmentExpression StringVariable StringDataType InstanceFieldInitializationStatement java.util.ArrayList GenericObjectCreationStatement ForEachStatement java.util.ArrayList.add ActualMethodParameter ObjectCreationStatement ConstructorCall MethodDefinition PublicMethodSpecifier ReturnStatement VoidDataType InstanceFieldInvocation ThisReference";

        String query2 = "question\tI was presented with this question in an end of module open book exam today and found myself lost. i was reading Head first Javaand both definitions seemed to be exactly the same. i was just wondering what the MAIN difference was for my own piece of mind. i know there are a number of similar questions to this but, none i have seen which provide a definitive answer.Thanks, Darren";

        String query3 = "answer accepted-answer\tInheritance is when a 'class' derives from an existing 'class'.So if you have a Person class, then you have a Student class that extends Person, Student inherits all the things that Person has.There are some details around the access modifiers you put on the fields/methods in Person, but that's the basic idea.For example, if you have a private field on Person, Student won't see it because its private, and private fields are not visible to subclasses.Polymorphism deals with how the program decides which methods it should use, depending on what type of thing it has.If you have a Person, which has a read method, and you have a Student which extends Person, which has its own implementation of read, which method gets called is determined for you by the runtime, depending if you have a Person or a Student.It gets a bit tricky, but if you do something likePerson p = new Student();p.read();the read method on Student gets called.Thats the polymorphism in action.You can do that assignment because a Student is a Person, but the runtime is smart enough to know that the actual type of p is Student.Note that details differ among languages.You can do inheritance in javascript for example, but its completely different than the way it works in Java.";

        String query4 = "answer\tPolymorphism: The ability to treat objects of different types in a similar manner.Example: Giraffe and Crocodile are both Animals, and animals can Move.If you have an instance of an Animal then you can call Move without knowing or caring what type of animal it is.Inheritance: This is one way of achieving both Polymorphism and code reuse at the same time.Other forms of polymorphism:There are other way of achieving polymorphism, such as interfaces, which provide only polymorphism but no code reuse (sometimes the code is quite different, such as Move for a Snake would be quite different from Move for a Dog, in which case an Interface would be the better polymorphic choice in this case.In other dynamic languages polymorphism can be achieved with Duck Typing, which is the classes don't even need to share the same base class or interface, they just need a method with the same name.Or even more dynamic like Javascript, you don't even need classes at all, just an object with the same method name can be used polymorphically.";

        String query5 = "question\tI found out that the above piece of code is perfectly legal in Java. I have the following questions. ThanksAdded one more question regarding Abstract method classes.public class TestClass{public static void main(String[] args) {TestClass t = new TestClass();}private static void testMethod(){abstract class TestMethod{int a;int b;int c;abstract void implementMe();}class DummyClass extends TestMethod{void implementMe(){}}DummyClass dummy = new DummyClass();}}";

        String query6 = "question\tIn java it's a bit difficult to implement a deep object copy function. What steps you take to ensure the original object and the cloned one share no reference?";

        String query7 = "answer\tYou can make a deep copy serialization without creating some files. Copy: Restore:\tByteArrayOutputStream bos = new ByteArrayOutputStream();ObjectOutputStream oos = new ObjectOutputStream(bos);oos.writeObject(object);oos.flush();oos.close();bos.close();byte[] byteData = bos.toByteArray();; ByteArrayInputStream bais = new ByteArrayInputStream(byteData);(Object) object = (Object) new ObjectInputStream(bais).readObject();";

        String query8 = "answer\tJava has the ability to create classes at runtime. These classes are known as Synthetic Classes or Dynamic Proxies. See for more information. Other open-source libraries, such as and also allow you to generate synthetic classes, and are more powerful than the libraries provided with the JRE. Synthetic classes are used by AOP (Aspect Oriented Programming) libraries such as Spring AOP and AspectJ, as well as ORM libraries such as Hibernate.";

        String query9 = "answer\tIn short: the web server issues a unique identifier to on his visit. The visitor must bring back that ID for him to be recognised next time around. This identifier also allows the server to properly segregate objects owned by one session against that of another. If is: If is: Once he's on the service mode and on the groove, the servlet will work on the requests from all other clients.Why isn't it a good idea to have one instance per client? Think about this: Will you hire one pizza guy for every order that came? Do that and you'd be out of business in no time. It comes with a small risk though. Remember: this single guy holds all the order information in his pocket: so if you're not cautious about, he may end up giving the wrong order to a certain client.";

        String query10 = "answer\tA safe way is to serialize the object, then deserialize.This ensures everything is a brand new reference.about how to do this efficiently. Caveats: It's possible for classes to override serialization such that new instances are created, e.g. for singletons.Also this of course doesn't work if your classes aren't Serializable.";


        Map<String, List<String>> myMap = new HashMap<>();
        try{

            myMap.put("post1", getTopics(query1));
            myMap.put("post2", getTopics(query2));
            myMap.put("post3", getTopics(query3));
            myMap.put("post4", getTopics(query4));
            myMap.put("post5", getTopics(query5));
            myMap.put("post6", getTopics(query6));
            myMap.put("post7", getTopics(query7));
            myMap.put("post8", getTopics(query8));
            myMap.put("post9", getTopics(query9));
            myMap.put("post10", getTopics(query10));

        }
        catch (Exception e){
            System.out.println("Exception in getTopics function");
            System.out.println(e);
        }

        return myMap;

    }
}
