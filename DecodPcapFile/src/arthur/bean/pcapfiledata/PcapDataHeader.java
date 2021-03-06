package arthur.bean.pcapfiledata;

public class PcapDataHeader {
	private int time_s;// 4B 时间戳（秒）
	private int time_ms;// 4B 时间戳（微妙）
	private int pLength;// 4B 抓包长度
	private int length;// 4B 实际长度

	public int getTime_s() {
		return time_s;
	}

	public void setTime_s(int time_s) {
		this.time_s = time_s;
	}

	public int getTime_ms() {
		return time_ms;
	}

	public void setTime_ms(int time_ms) {
		this.time_ms = time_ms;
	}

	public int getpLength() {
		return pLength;
	}

	public void setpLength(int pLength) {
		this.pLength = pLength;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "PcapDataHeader [time_s=" + time_s + ", time_ms=" + time_ms + ", pLength=" + pLength + ", length="
				+ length + "]";
	}

}
