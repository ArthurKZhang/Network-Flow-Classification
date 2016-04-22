package dao.databean.arthur;

import java.util.LinkedList;
import java.util.List;

public class NetworkFlow {
	private FiveTuple _5tuple; // can be used as ID

	private List<IPPacket> packetList = new LinkedList<IPPacket>();

	public FiveTuple get_5tuple() {
		return _5tuple;
	}

	public void set_5tuple(FiveTuple _5tuple) {
		this._5tuple = _5tuple;
	}

	public List<IPPacket> getPacketList() {
		return packetList;
	}

	public void setPacketList(List<IPPacket> packetList) {
		this.packetList = packetList;
	}

}
