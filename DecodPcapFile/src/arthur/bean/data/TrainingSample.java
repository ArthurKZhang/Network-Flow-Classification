package arthur.bean.data;

import java.util.List;

public class TrainingSample {
	private String w;
	private List<FlowFeatureVector> trains;

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}

	public List<FlowFeatureVector> getTrains() {
		return trains;
	}

	public void setTrains(List<FlowFeatureVector> trains) {
		this.trains = trains;
	}

}
