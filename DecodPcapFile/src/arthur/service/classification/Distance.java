package arthur.service.classification;

import java.util.List;

import arthur.bean.data.BoF;
import arthur.bean.data.FlowFeatureVector;

public interface Distance {
	public double getDistance(List<FlowFeatureVector> tranList, BoF testBof);
	public default double[] vector2Array(FlowFeatureVector v) {
		double[] a = new double[10];
		a[0] = v.getSize_packet();
		a[1] = v.getSize_bytes();
		
		a[2] = v.getMin_size();
		a[3] = v.getMax_size();
		a[4] = v.getMean_size();
		a[5] = v.getStdDev_size();
		
		a[6] = v.getMin_time();
		a[7] = v.getMax_time();
		a[8] = v.getMean_time();
		a[9] = v.getStdDev_time();
		return a;
	}
}
