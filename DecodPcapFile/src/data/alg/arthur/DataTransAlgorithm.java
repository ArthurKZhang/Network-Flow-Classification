package data.alg.arthur;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dao.databean.arthur.BoF;
import dao.databean.arthur.FiveTuple;
import dao.databean.arthur.FlowFeatureVector;
import dao.databean.arthur.IPPacket;
import dao.databean.arthur.NetworkFlow;
import dao.databean.arthur.ThreeTuple;

public class DataTransAlgorithm {

	/**
	 * get flow_feature_vector by a network_flow
	 * 
	 * @param {@link
	 * 			NetworkFlow}
	 * @return {@link FlowFeatureVector}
	 */
	public static FlowFeatureVector getFlowFeatureVector(NetworkFlow netflow) {
		FlowFeatureVector ffVector = new FlowFeatureVector();
		ffVector.set_3tuple(get3tuple(netflow));
		ffVector.setSize_packet(getSize_packet(netflow));
		ffVector.setSize_bytes(getSize_bytes(netflow));
		Object[] siz_Packs = getMinMaxMeanStd_DevOfSizeInfo(netflow);
		Object[] tim_Packs = getMinMaxMeanStd_DevOfTimeInfo(netflow);
		ffVector.setMin_size((Integer) siz_Packs[0]);
		ffVector.setMax_size((Integer) siz_Packs[1]);
		ffVector.setMean_size((Float) siz_Packs[2]);
		ffVector.setStdDev_size((Float) siz_Packs[3]);

		ffVector.setMin_time((Long) tim_Packs[0]);
		ffVector.setMax_time((Long) tim_Packs[1]);
		ffVector.setMean_time((Float) tim_Packs[2]);
		ffVector.setStdDev_time((Float) tim_Packs[3]);
		return ffVector;
	}

	private static ThreeTuple get3tuple(NetworkFlow netflow) {
		FiveTuple _5tuple = netflow.get_5tuple();
		ThreeTuple _3tuple = new ThreeTuple(_5tuple.getDesAddress(), _5tuple.getDesPort(), _5tuple.getProtocol());
		return _3tuple;
	}

	private static int getSize_packet(NetworkFlow netflow) {
		return netflow.getPacketList().size();
	}

	private static int getSize_bytes(NetworkFlow netflow) {
		int size = 0;
		for (IPPacket p : netflow.getPacketList()) {
			size += p.getSize();
		}
		return size;
	}

	/**
	 * Statistics informations of Size of network flow
	 * @param {@link
	 * 			NetworkFlow}
	 * @return {@link Object[]} 
	 * Object[] contains 4 elements: the 1st is MIN packet size, the 2nd is MAX packet size,
	 * the 3rd is MEAN of packet size, the 4th is STD_DEV of packet size.
	 */
	private static Object[] getMinMaxMeanStd_DevOfSizeInfo(NetworkFlow netflow) {
		Object[] sizeInfos = new Object[4];
		int min = Integer.MAX_VALUE, max = 0;
		float mean = 0, stdDev = 0;
		for (IPPacket p : netflow.getPacketList()) {
			mean += p.getSize();
			if (min > p.getSize())
				min = p.getSize();
			if (max < p.getSize())
				max = p.getSize();
		}
		mean /= netflow.getPacketList().size();
		for (IPPacket p : netflow.getPacketList()) {
			stdDev += Math.sqrt(p.getSize() - mean);
		}
		stdDev = (float) Math.pow(stdDev / netflow.getPacketList().size(), 2);
		sizeInfos[0] = min;
		sizeInfos[1] = max;
		sizeInfos[2] = mean;
		sizeInfos[3] = stdDev;
		return sizeInfos;
	}

	/**
	 * Statistics informations of Time of network flow
	 * @param {@link
	 * 			NetworkFlow}
	 * @return {@link Object[]} 
	 * Object[] contains 4 elements: the 1st is MIN packet arrive time, the 2nd is MAX arrive time,
	 * the 3rd is MEAN of ...., the 4th is STD_DEV of ......
	 */
	private static Object[] getMinMaxMeanStd_DevOfTimeInfo(NetworkFlow netflow) {
		// !!!!!!!!use BigDecemal later!!!!!!!!!
		Object[] timeInfos = new Object[4];
		long min = Long.MAX_VALUE, max = 0;
		float mean = 0, stdDev = 0;
		for (IPPacket p : netflow.getPacketList()) {
			mean += p.getArriveTime();
			if (min > p.getArriveTime())
				min = p.getArriveTime();
			if (max < p.getArriveTime())
				max = p.getArriveTime();
		}
		mean /= netflow.getPacketList().size();
		for (IPPacket p : netflow.getPacketList()) {
			stdDev += Math.sqrt(p.getArriveTime() - mean);
		}
		stdDev = (float) Math.pow(stdDev / netflow.getPacketList().size(), 2);
		timeInfos[0] = min;
		timeInfos[1] = max;
		timeInfos[2] = mean;
		timeInfos[3] = stdDev;
		return timeInfos;
	}
	
	/**
	 * Correlation Analysis through "three tuple", divide and merge a list of
	 * flow feature vectors to BoFs. a BoF contains all the flows which have same 
	 * "three tuple".
	 * @param {@link List<FlowFeatureVector>}
	 * @return {@link Map<Integer, BoF>}
	 * about the returned map, key is integer, the hashcode of "three tuple".
	 */
	public static Map<Integer, BoF> correlationAnalysis(List<FlowFeatureVector> vecList) {
		Map<Integer, BoF> map = new HashMap<Integer, BoF>();
		for (FlowFeatureVector ffv : vecList) {
			int hashcode = ffv.get_3tuple().hashCode();
			if (map.keySet().contains(hashcode))
				continue;
			List<FlowFeatureVector> list = getffVectorBy3THash(hashcode, vecList);
			BoF bof = new BoF();
			bof.setFlowFeaVectorList(list);
			map.put(hashcode, bof);
		}
		return map;
	}

	private static List<FlowFeatureVector> getffVectorBy3THash(int hash, List<FlowFeatureVector> vecList) {
		if (vecList.size() == 0)
			return null;
		List<FlowFeatureVector> list = new LinkedList<FlowFeatureVector>();
		for (FlowFeatureVector ffv : vecList) {
			if (hash == ffv.get_3tuple().hashCode())
				list.add(ffv);
		}
		if (list.isEmpty())
			return null;
		return list;
	}

	/**
	 * 把 packet 分成流的时候要考虑到时间间隔问题，这个在这个方法中没有处理，所以他把所有相同五元组的
	 * @param packets
	 * @return
	 */
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
				map.put(hashcode, flow);
			} else {
				flow.getPacketList().add(p);
			}
		}
		return map;
	}

	/**
	 * 把 packet 分成流的时候要考虑到时间间隔问题，这个在这个方法中没有处理，所以他把所有相同五元组的
	 * @param packets
	 * @return
	 */
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
			} else {
				flow.getPacketList().add(p);
			}
		}
		return flowList;
	}

	private static NetworkFlow getNetworkFlowBy5THsah(int hashcode, Map<Integer, NetworkFlow> flowMap) {
		if (flowMap.size() == 0 && !flowMap.containsKey(new Integer(hashcode))) {
			return null;
		}
		NetworkFlow nf = flowMap.get(hashcode);
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
