package data.alg.arthur;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dao.databean.arthur.IPPacket;
import dao.databean.arthur.NetworkFlow;

public class DataTransAlgorithm {
	
	
	
	
	
	
	
	
	public static Map<Integer, NetworkFlow> packets2NetworkFlow(List<IPPacket> packets) {
		Map<Integer, NetworkFlow> map = new HashMap<Integer, NetworkFlow>();
		for (IPPacket p : packets) {
			int hashcode = p.get_5tuple().hashCode();
			NetworkFlow flow = getNetworkFlowBy5THsah(hashcode, map);
			if (flow == null) {
				flow = new NetworkFlow();
				flow.set_5tuple(p.get_5tuple());
				LinkedList<IPPacket> l = new LinkedList<IPPacket>();
				l.add(p);
				flow.setPacketList(l);
				map.put(new Integer(hashcode), flow);
			}
		}
		return map;
	}

	public static List<NetworkFlow> packets2NetworkFlowl(List<IPPacket> packets) {
		List<NetworkFlow> flowList = new LinkedList<NetworkFlow>();
		for (IPPacket p : packets) {
			int hashcode = p.get_5tuple().hashCode();
			NetworkFlow flow = getNetworkFlowBy5THsah(hashcode, flowList);
			if (flow == null) {
				flow = new NetworkFlow();
				flow.set_5tuple(p.get_5tuple());
				LinkedList<IPPacket> l = new LinkedList<IPPacket>();
				l.add(p);
				flow.setPacketList(l);
				flowList.add(flow);
			}
		}
		return flowList;
	}

	private static NetworkFlow getNetworkFlowBy5THsah(int hashcode, Map<Integer, NetworkFlow> flowMap) {
		if (flowMap.size() == 0 && !flowMap.containsKey(new Integer(hashcode))) {
			return null;
		}
		NetworkFlow nf = flowMap.get(new Integer(hashcode));
		return nf;
	}

	private static NetworkFlow getNetworkFlowBy5THsah(int hashcode, List<NetworkFlow> flowList) {
		if (flowList.size() == 0) {
			return null;
		}
		for (NetworkFlow flow : flowList) {
			if (flow.get_5tuple().hashCode() == hashcode) {
				return flow;
			}
		}
		return null;
	}
}
