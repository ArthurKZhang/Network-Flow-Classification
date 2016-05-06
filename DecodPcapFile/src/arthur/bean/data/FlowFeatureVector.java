package arthur.bean.data;

public class FlowFeatureVector {
	private ThreeTuple _3tuple; // can be used as id

	private int size_packet;// number of packets transferred in unidirection
	private int size_bytes; // volume of bytes transferred in unidirection

	private int min_size; // min of packet size
	private int max_size; // max of packet size
	private float mean_size; // mean of packet size
	private float stdDev_size; // Std Dev. of packet size

	private long min_time; // min of arriving time
	private long max_time; // max of arriving time
	private float mean_time; // mean of arriving time
	private float stdDev_time; // Std Dev. of arriving time

	public FlowFeatureVector(int size_packet, int size_bytes, int min_size, int max_size, float mean_size,
			float stdDev_size, int min_time, int max_time, float mean_time, float stdDev_time) {
		this.size_packet = size_packet;
		this.size_bytes = size_bytes;
		this.min_size = min_size;
		this.max_size = max_size;
		this.mean_size = mean_size;
		this.stdDev_size = stdDev_size;
		this.min_time = min_time;
		this.max_time = max_time;
		this.mean_time = mean_time;
		this.stdDev_time = stdDev_time;
	}
	
	public FlowFeatureVector(){
		
	}

	public ThreeTuple get_3tuple() {
		return _3tuple;
	}

	public void set_3tuple(ThreeTuple _3tuple) {
		this._3tuple = _3tuple;
	}

	public int getSize_packet() {
		return size_packet;
	}

	public void setSize_packet(int size_packet) {
		this.size_packet = size_packet;
	}

	public int getSize_bytes() {
		return size_bytes;
	}

	public void setSize_bytes(int size_bytes) {
		this.size_bytes = size_bytes;
	}

	public int getMin_size() {
		return min_size;
	}

	public void setMin_size(int min_size) {
		this.min_size = min_size;
	}

	public int getMax_size() {
		return max_size;
	}

	public void setMax_size(int max_size) {
		this.max_size = max_size;
	}

	public float getMean_size() {
		return mean_size;
	}

	public void setMean_size(float mean_size) {
		this.mean_size = mean_size;
	}

	public float getStdDev_size() {
		return stdDev_size;
	}

	public void setStdDev_size(float stdDev_size) {
		this.stdDev_size = stdDev_size;
	}

	public long getMin_time() {
		return min_time;
	}

	public void setMin_time(Long tim_Packs) {
		this.min_time = tim_Packs;
	}

	public long getMax_time() {
		return max_time;
	}

	public void setMax_time(Long tim_Packs) {
		this.max_time = tim_Packs;
	}

	public float getMean_time() {
		return mean_time;
	}

	public void setMean_time(float mean_time) {
		this.mean_time = mean_time;
	}

	public float getStdDev_time() {
		return stdDev_time;
	}

	public void setStdDev_time(float stdDev_time) {
		this.stdDev_time = stdDev_time;
	}

	@Override
	public String toString() {
		return "FlowFeatureVector [_3tuple=" + _3tuple + ", size_packet=" + size_packet + ", size_bytes=" + size_bytes
				+ ", min_size=" + min_size + ", max_size=" + max_size + ", mean_size=" + mean_size + ", stdDev_size="
				+ stdDev_size + ", min_time=" + min_time + ", max_time=" + max_time + ", mean_time=" + mean_time
				+ ", stdDev_time=" + stdDev_time + "]";
	}

}
