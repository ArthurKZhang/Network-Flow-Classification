package arthur.dao.Mongo;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import arthur.bean.data.BoF;
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
}
