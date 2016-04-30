package pcap.util.arthur;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pcap.databean.arthur.Pcap;
import pcap.databean.arthur.PcapData;
import pcap.databean.arthur.PcapHeader;

public class PcapParser {
	/**
	 * @author Zh_张宇
	 * @author En_Arhur Parser .pcap file
	 */
//	public static byte[] time1;
//	public static byte[] time2;
	public static Pcap unpack(InputStream is) throws IOException {
		byte[] buffer_4 = new byte[4];
		byte[] buffer_2 = new byte[2];
		Pcap pcap = new Pcap();
		PcapHeader header = new PcapHeader();
		/**
		 * read pcap header zone
		 */
		int m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_megic不足4字节");
			return null;
		}
		ByteArrayUtil.reverseByteArray(buffer_4);
		header.setMagic(ByteArrayUtil.byteArrayToInt(buffer_4));

		m = is.read(buffer_2);
		if (m != 2) {
			System.err.println("header_majorVersion不足2字节");
			return null;
		}
		ByteArrayUtil.reverseByteArray(buffer_2);
		header.setmajor_version(ByteArrayUtil.byteArrayToShort(buffer_2));

		m = is.read(buffer_2);
		if (m != 2) {
			System.err.println("header_minorVersion不足2字节");
			return null;
		}
		ByteArrayUtil.reverseByteArray(buffer_2);
		header.setMinor_version(ByteArrayUtil.byteArrayToShort(buffer_2));

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_timeZone不足4字节");
			return null;
		}
		ByteArrayUtil.reverseByteArray(buffer_4);
		header.setTimezone(ByteArrayUtil.byteArrayToInt(buffer_4));

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_sigFlags不足4字节");
			return null;
		}
		ByteArrayUtil.reverseByteArray(buffer_4);
		header.setSigflags(ByteArrayUtil.byteArrayToInt(buffer_4));

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_snaplen不足4字节");
			return null;
		}
		ByteArrayUtil.reverseByteArray(buffer_4);
		header.setSnaplen(ByteArrayUtil.byteArrayToInt(buffer_4));

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_linkType不足4字节");
			return null;
		}
		ByteArrayUtil.reverseByteArray(buffer_4);
		header.setLinktype(ByteArrayUtil.byteArrayToInt(buffer_4));

		pcap.setHeader(header);

		/**
		 * read pcap data zone
		 */
		List<PcapData> dataList = new ArrayList<PcapData>();
		while (m>0) {
			PcapData data = new PcapData();
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header time_s <4B");
				break;
			}
			ByteArrayUtil.reverseByteArray(buffer_4);
			data.getDataheader().setTime_s(ByteArrayUtil.byteArrayToInt(buffer_4));
//			time1 = buffer_4;
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header time_ms <4B");
				break;
			}
			ByteArrayUtil.reverseByteArray(buffer_4);
			data.getDataheader().setTime_ms(ByteArrayUtil.byteArrayToInt(buffer_4));
//			time2 = buffer_4;
			
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header pLength <4B");
				break;
			}
//			int IPFrameLength00 = ByteArrayUtil.byteArrayToInt(buffer_4);
			ByteArrayUtil.reverseByteArray(buffer_4);
			int IPFrameLength = ByteArrayUtil.byteArrayToInt(buffer_4);
			data.getDataheader().setpLength(IPFrameLength);
			
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header length <4B");
				break;
			}
			ByteArrayUtil.reverseByteArray(buffer_4);
			data.getDataheader().setLength(ByteArrayUtil.byteArrayToInt(buffer_4));
			
//			int IPFrameLength = data.getpLength();
			byte[] content = new byte[IPFrameLength];
			m = is.read(content);
			if (m < IPFrameLength) {
				System.err.println("pcap package header time_s < IPFrameLength: " + IPFrameLength);
				break;
			}
			data.setContent(content);
			dataList.add(data);
		}
		pcap.setData(dataList);
		return pcap;
	}

}
