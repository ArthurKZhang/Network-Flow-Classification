package arthur.dao.Mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import arthur.bean.data.FlowFeatureVector;
import arthur.dao.codec.FlowFeatureVectorCodec;

public class DBHelperTrainVector implements ConfigTrainVector {
//	public static 
	
	public static void createCollection() {
		MongoClient mongoClient = new MongoClient(HOST, PORT);
		MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		try {
			db.createCollection(COLLECTION_NAME);
			System.out.println("Collection--" + COLLECTION_NAME + "--created");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			mongoClient.close();
		}
	}
	public static void dropCollection(){
		MongoClient mongoClient = new MongoClient(HOST, PORT);
		MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		db.getCollection(COLLECTION_NAME).drop();
		mongoClient.close();
	}
	public static boolean save(FlowFeatureVector fv, String type) {
//		MongoClient mongoClient = new MongoClient(HOST, PORT);
		CodecRegistry codecRegistry = 
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new FlowFeatureVectorCodec()),
                        MongoClient.getDefaultCodecRegistry());
        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(codecRegistry).build();
		// host[:port]
		MongoClient mongoClient = new MongoClient(HOST+":"+PORT+"", options);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		try {
			MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION_NAME);
			Document fVector = new Document("type", type)
					.append("vector", fv);
			collection.insertOne(fVector);
			return true;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}finally {
			mongoClient.close();
		}
	}
	
	public static Map<String, List<FlowFeatureVector>> queryAll(){
//		MongoClient mongoClient = new MongoClient(HOST, PORT);
		CodecRegistry codecRegistry = 
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new FlowFeatureVectorCodec()),
                        MongoClient.getDefaultCodecRegistry());
        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(codecRegistry).build();
		// host[:port]
		MongoClient mongoClient = new MongoClient(HOST+":"+PORT+"", options);
		MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		FindIterable<Document> findIterable = db.getCollection(COLLECTION_NAME).find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		Map<String, List<FlowFeatureVector>> map = new HashMap<String, List<FlowFeatureVector>>();
		while(mongoCursor.hasNext()){  
            Document d = mongoCursor.next();
            String type = d.getString("type");
            String s = d.get("vector",String.class);
            FlowFeatureVector v = new FlowFeatureVector(Document.parse(s));
//            System.out.println("**"+v.toString());  for test
            if(map.containsKey(type)){
            	map.get(type).add(v);
            }else{
            	List<FlowFeatureVector> ls = new ArrayList<FlowFeatureVector>();
            	ls.add(v);
            	map.put(type, ls);
            }
         } 
		mongoClient.close();
		return map;
	}
}
