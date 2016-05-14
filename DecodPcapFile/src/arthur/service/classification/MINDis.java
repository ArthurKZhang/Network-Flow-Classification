package arthur.service.classification;

import java.util.List;

import arthur.bean.data.BoF;
import arthur.bean.data.FlowFeatureVector;

public class MINDis implements Distance {

	@Override
	public double getDistance(List<FlowFeatureVector> tranList, BoF testBof) {
		List<FlowFeatureVector> testlist = testBof.getFlowFeaVectorList();

		double min = Double.MAX_VALUE;

		for (int i = 0; i < testlist.size(); i++) {
			FlowFeatureVector bv = testlist.get(i);
			double[] b = vector2Array(bv);
			for (FlowFeatureVector tv : tranList) {
				double[] t = vector2Array(tv);
				double dis = 0;
				// ¼ÆËã¾àÀë
				for (int j = 0; j < b.length; j++) {
					dis += BigDecimalCalculate.mul(b[j] - t[j], b[j] - t[j]);
				}
				dis = Math.sqrt(dis);
				// ¸üÐÂmin
				if (dis < min) {
					min = dis;
				}
			}
		}

		return min;
	}

}
