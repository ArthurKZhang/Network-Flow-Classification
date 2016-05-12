package arthur.dao.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import arthur.bean.data.IPPacket;

public class IPPacketCodec implements Codec<IPPacket> {

	@Override
	public void encode(BsonWriter writer, IPPacket v, EncoderContext encoderContext) {
		Document d = new Document();
		d.append("time_s", v.getTime_s())
		.append("time_ms", v.getTime_ms())
		.append("size", v.getSize())
		.append("sourceAddress", v.get_5tuple().getSouAddress())
		.append("destinAddress", v.get_5tuple().getDesAddress())
		.append("sourcePort", v.get_5tuple().getSouPort())
		.append("destinPort", v.get_5tuple().getDesPort())
		.append("protocol", v.get_5tuple().getProtocol());
		
		writer.writeString(d.toJson());
	}

	@Override
	public Class<IPPacket> getEncoderClass() {
		return IPPacket.class;
	}

	@Override
	public IPPacket decode(BsonReader reader, DecoderContext decoderContext) {
		String json = reader.readString();
		return new IPPacket(Document.parse(json));
	}

}
