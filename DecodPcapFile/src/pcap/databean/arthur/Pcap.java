package pcap.databean.arthur;

import java.util.List;

public class Pcap {
	/**
	 * @author Zh_ÕÅÓî
	 * @author En_Arhur .pcap file data bean pcap is composed of pcapHeader and
	 *         pcapData
	 */

	private PcapHeader header;
	private List<PcapData> data;

	public PcapHeader getHeader() {
		return header;
	}

	public void setHeader(PcapHeader header) {
		this.header = header;
	}

	public List<PcapData> getData() {
		return data;
	}

	public void setData(List<PcapData> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Pcap [header=" + header + ", data=" + data + "]";
	}
}
