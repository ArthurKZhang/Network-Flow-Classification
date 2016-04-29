package pcap.databean.arthur;

import java.util.Arrays;

public class PcapData {
	/**
	 * @author Zh_����
	 * @author En_Arhur .pcap file Data zone data bean
	 */
	
	/**
	 *  pcap package header zone--16B
	 */
	
	private PcapDataHeader dataheader;
	/**
	 * ������IPDataFrame replace----------------------------------------------
	 */
	private byte[] content; // ���ȣ�pLength.IP����֡
	

	@Override
	public String toString() {
		return "PcapData [dataheader=" + dataheader + ", content=" + Arrays.toString(content) + "]";
	}

	public PcapDataHeader getDataheader() {
		return dataheader;
	}

	public void setDataheader(PcapDataHeader dataheader) {
		this.dataheader = dataheader;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
