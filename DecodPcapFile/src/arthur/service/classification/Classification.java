package arthur.service.classification;

import java.util.List;
import java.util.Map;

import arthur.bean.data.BoF;
import arthur.bean.data.FlowFeatureVector;

public class Classification {
	public static String classifier(Map<String, List<FlowFeatureVector>> trainedMap, BoF testBof,Distance disMethod) {
		Object[] clas = trainedMap.keySet().toArray();
		double[] dist = new double[clas.length - 1];
		for (int i = 0; i < clas.length; i++) {
			String cla = (String) clas[i];
			List<FlowFeatureVector> tranList = trainedMap.get(cla);
			// bof 对这个cla类的距离
			double d = disMethod.getDistance(tranList, testBof);
			dist[i] = d;
		}
		// 获得最小的距离，输出
		double temp = dist[0];
		int index = 0;
		for(int i = 0;i<dist.length;i++){
			if(dist[i]<temp){
				temp = dist[i];
				index = i;
			}
		}
		
		return clas[index].toString();
	}
}
