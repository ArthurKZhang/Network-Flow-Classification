package data.bean.arthur;

import java.util.LinkedList;
import java.util.List;

public class BoF {
	private ThreeTuple _3tuple; // can be used as id.
	private List<FlowFeatureVector> flowFeaVectorList = new LinkedList<FlowFeatureVector>();

	public ThreeTuple get_3tuple() {
		return _3tuple;
	}

	public void set_3tuple(ThreeTuple _3tuple) {
		this._3tuple = _3tuple;
	}

	public List<FlowFeatureVector> getFlowFeaVectorList() {
		return flowFeaVectorList;
	}

	public void setFlowFeaVectorList(List<FlowFeatureVector> flowFeaVectorList) {
		this.flowFeaVectorList = flowFeaVectorList;
	}

}
