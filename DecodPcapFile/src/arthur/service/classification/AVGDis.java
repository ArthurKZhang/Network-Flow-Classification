package arthur.service.classification;

import java.util.List;

import arthur.bean.data.BoF;
import arthur.bean.data.FlowFeatureVector;

public class AVGDis implements Distance {

	@Override
	public double getDistance(List<FlowFeatureVector> tranList, BoF testBof) {
		List<FlowFeatureVector> testlist = testBof.getFlowFeaVectorList();
		double index = Double.MAX_VALUE, min = 0;
		for (FlowFeatureVector bv : testlist) {
			double[] b = vector2Array(bv);
			for (FlowFeatureVector tv : tranList) {
				double[] t = vector2Array(tv);
				double dis = 0;
				// �������
				for (int i = 0; i < b.length; i++) {
					dis += BigDecimalCalculate.mul(b[i] - t[i], b[i] - t[i]);
				}
				dis = Math.sqrt(dis);
				// ����min
				if (dis < index) {
					index = dis;
				}
			}
			min += index;
		}

		return min/(testlist.size());
	}

}
