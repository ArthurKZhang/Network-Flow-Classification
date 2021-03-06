package arthur.bean.pcapfiledata;

import java.util.Arrays;

public class PcapData {
	/**
	 * @author Zh_张宇
	 * @author En_Arhur .pcap file Data zone data bean
	 */
	
	/**
	 *  pcap package header zone--16B
	 */
	
	private PcapDataHeader dataheader = new PcapDataHeader();
	/**
	 * 明天用IPDataFrame replace----------------------------------------------
	 */
	private byte[] content; // 长度：pLength.IP数据帧
	

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
