package arthur.service.datatrans;

import java.util.List;
import java.util.Map;
import java.util.Set;

import arthur.bean.data.BoF;
import arthur.bean.data.FlowFeatureVector;
import arthur.bean.data.IPPacket;
import arthur.bean.data.NetworkFlow;
import arthur.dao.Mongo.DBHelperBOF;
import arthur.dao.Mongo.DBHelperFlowFeatureVector;
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
				System.err.println("save NetWorkFlow " + (count + 1) + " ERROR:flowDetails--\n" + nf.toString());
			} else {
				count++;
			}
		}
		return count;
	}

	public static int netflow2FeatureVector(NetworkFlow nf) {
		FlowFeatureVector fv = DataTransAlgorithm.getFlowFeatureVector(nf);
		DBHelperFlowFeatureVector.createCollection();
		if (!DBHelperFlowFeatureVector.save(fv)) {
			System.err.println("save FlowFeature Vector ERROR: vector details:--\n" + fv.toString());
			return 0;
		}

		return 1;
	}

	public static int featureVector2BOF(List<FlowFeatureVector> vlist) {
		int count = 0;
		Map<Integer, BoF> bofs = DataTransAlgorithm.correlationAnalysis(vlist);
		DBHelperBOF.createCollection();
		for (Integer i : bofs.keySet()) {
			BoF bof = bofs.get(i);
			if (!DBHelperBOF.save(bof)) {
				System.err.println("save BOF " + (count + 1) + "ERROR: BOF details:--\n" + bof.toString());
			} else {
				count++;
			}
		}

		return count;
	}
}
