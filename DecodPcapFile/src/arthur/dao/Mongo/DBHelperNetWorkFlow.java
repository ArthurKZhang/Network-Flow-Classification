package arthur.dao.Mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import arthur.bean.data.IPPacket;
import arthur.bean.data.NetworkFlow;

public class DBHelperNetWorkFlow implements ConfigNetWOrkFlow {
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

	public static boolean save(NetworkFlow nf) {
		MongoClient mongoClient = new MongoClient(HOST, PORT);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		try {
			MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION_NAME);
			Document netflow = new Document("sourAddress", nf.get_5tuple().getSouAddress())
					.append("desAddress", nf.get_5tuple().getDesAddress())
					.append("sourPort", nf.get_5tuple().getSouPort())
					.append("desPort", nf.get_5tuple().getDesPort())
					.append("protocol", nf.get_5tuple().getProtocol())
					.append("packets", nf.getPacketList());
			collection.insertOne(netflow);
			return true;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}finally {
			mongoClient.close();
		}
	}
}
