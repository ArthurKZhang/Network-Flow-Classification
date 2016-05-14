package arthur.dao.Mongo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import arthur.bean.data.BoF;
import arthur.bean.data.FlowFeatureVector;
import arthur.bean.data.ThreeTuple;
import arthur.dao.codec.FlowFeatureVectorCodec;
import arthur.dao.codec.IPPacketCodec;

public class DBHelperBOF implements ConfigBOF {
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
	}
	public static boolean save(BoF bof) {
		CodecRegistry codecRegistry = 
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new FlowFeatureVectorCodec()),
                        MongoClient.getDefaultCodecRegistry());
        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(codecRegistry).build();
		// host[:port]
		MongoClient mongoClient = new MongoClient(HOST+":"+PORT+"", options);//new MongoClient(HOST, PORT);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		try {
			MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION_NAME);
			System.out.println(bof);
			Document b = new Document("desAddress",bof.get_3tuple().getDesAddress())
					.append("desPort", bof.get_3tuple().getDesPort())
					.append("protocol", bof.get_3tuple().getProtocol())
					.append("vectorList", bof.getFlowFeaVectorList());
			collection.insertOne(b);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			mongoClient.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<BoF> queryAll(){
//		MongoClient mongoClient = new MongoClient(HOST, PORT);
		CodecRegistry codecRegistry = 
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new FlowFeatureVectorCodec()),
                        MongoClient.getDefaultCodecRegistry());
        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(codecRegistry).build();
		// host[:port]
		MongoClient mongoClient = new MongoClient(HOST+":"+PORT+"", options);//new MongoClient(HOST, PORT);
		MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		FindIterable<Document> findIterable = db.getCollection(COLLECTION_NAME).find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		List<BoF> bs = new LinkedList<>();
		while(mongoCursor.hasNext()){  
            Document d = mongoCursor.next();
            BoF b = new BoF();
            b.set_3tuple(new ThreeTuple(d.getString("desAddress"), d.getInteger("desPort"), d.getInteger("protocol")));
            Object o = d.get("vectorList");
            if(o instanceof List<?>){
            	List<Object> ols = (List<Object>) d.get("vectorList");
//            	System.out.println("QA-"+ols.get(0).getClass().getName());��String���͵�
            	List<FlowFeatureVector> temp = new ArrayList<FlowFeatureVector>();
            	for(Object s:ols){
            		FlowFeatureVector tempv = new FlowFeatureVector(Document.parse((String)s));
            		temp.add(tempv);
            	}
            	b.setFlowFeaVectorList(temp);
            }else{
            	System.out.println("CAN'T PUT LIST TO BoF");
            	System.out.println(o.getClass().getName());
            }
            bs.add(b);
         } 
		mongoClient.close();
		return bs;
	}
	
}
