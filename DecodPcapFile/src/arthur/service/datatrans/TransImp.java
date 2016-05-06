package arthur.service.datatrans;

import java.util.List;
import java.util.Map;
import java.util.Set;

import arthur.bean.data.IPPacket;
import arthur.bean.data.NetworkFlow;
import arthur.dao.Mongo.DBHelperNetWorkFlow;

public class TransImp {
	public static int ipPacket2NetFlow(List<IPPacket> packets) {
		Map<Integer, NetworkFlow> map = DataTransAlgorithm.packets2NetworkFlow(packets);
		Set<Integer> keys = map.keySet();
		DBHelperNetWorkFlow.createCollection();
		int count = 0;
		for (Integer i : keys) {
			NetworkFlow nf = map.get(i);

			if (!DBHelperNetWorkFlow.save(nf)) {
				System.err.println("save NetWorkFlow" + (count + 1) + " ERROR:flowDetails--\n" + nf.toString());
			} else {
				count++;
			}
		}
		return count;
	}
	public static int 
}
