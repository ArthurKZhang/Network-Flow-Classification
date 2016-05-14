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
import arthur.dao.Mongo.DBHelperTrainVector;

public class TransImp {
	public static int ipPacket2NetFlow2FVector(List<IPPacket> packets,int Vdb,String type) {
		Map<Integer, NetworkFlow> map = DataTransAlgorithm.packets2NetworkFlow(packets);
		Set<Integer> keys = map.keySet();
		int count = 0;
		for (Integer i : keys) {
			NetworkFlow nf = map.get(i);
			System.out.println(nf);
			
			if (!DBHelperNetWorkFlow.save(nf)) {
				System.err.println("save NetWorkFlow " + (count + 1) + " ERROR:flowDetails--\n" + nf.toString());
			} else {
				count++;
				if(Vdb==1){
					//训练数据集存储
					netflow2FeatureVector2(nf, type);
				}else{
					//待分类数据集存储
					netflow2FeatureVector(nf);
				}
				
			} 
		}
		System.out.println(count);
		return count;
	}
	
	private static boolean netflow2FeatureVector2(NetworkFlow nf, String type) {
		FlowFeatureVector fv = DataTransAlgorithm.getFlowFeatureVector(nf);
		if (!DBHelperTrainVector.save(fv, type)) {
			System.err.println("save TRAIN Vector ERROR: vector details:--\n" + fv.toString());
			return false;
		}
		return true;
	}
	
	private static boolean netflow2FeatureVector(NetworkFlow nf) {
		FlowFeatureVector fv = DataTransAlgorithm.getFlowFeatureVector(nf);
		if (!DBHelperFlowFeatureVector.save(fv)) {
			System.err.println("save FlowFeature Vector ERROR: vector details:--\n" + fv.toString());
			return false;
		}
		return true;
	}

	public static int featureVector2BOF(List<FlowFeatureVector> vlist) {
		int count = 0;
		Map<Integer, BoF> bofs = DataTransAlgorithm.correlationAnalysis(vlist);
//		DBHelperBOF.createCollection();
		for (Integer i : bofs.keySet()) {
			BoF bof = bofs.get(i);
			if (!DBHelperBOF.save(bof)) {
				System.err.println("save BOF " + (count + 1) + " ERROR: BOF details:--\n" + bof.toString());
			} else {
				count++;
			}
		}
		return count;
	}
}
