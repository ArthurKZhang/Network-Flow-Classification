package pcap.databean.arthur;

import java.util.Arrays;

public class PcapData {
	/**
	 * @author Zh_张宇
	 * @author En_Arhur .pcap file Data zone data bean
	 */
	
	/**
	 *  pcap package header zone--16B
	 */
	private int time_s;// 4B 时间戳（秒）
	private int time_ms;// 4B 时间戳（微妙）
	private int pLength;// 4B 抓包长度
	private int length;// 4B 实际长度
	
	private byte[] content; // 长度：pLength.IP数据帧
	
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
		return "PcapData [time_s=" + time_s + ", time_ms=" + time_ms
				+ ", pLength=" + pLength + ", length=" + length + ", content="
				+ Arrays.toString(content) + "]";
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
