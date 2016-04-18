package pcap.util;

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
		header.setMagic(byteArrayToInt(reverseByteArray(buffer_4)));
		
		m = is.read(buffer_2);
		if (m != 2) {
			System.err.println("header_majorVersion不足2字节");
			return null;
		}
		header.setmajor_version(byteArrayToShort(reverseByteArray(buffer_2)));

		m = is.read(buffer_2);
		if (m != 2) {
			System.err.println("header_minorVersion不足2字节");
			return null;
		}
		header.setMinor_version(byteArrayToShort(reverseByteArray(buffer_2)));
		
		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_timeZone不足4字节");
			return null;
		}
		header.setTimezone(byteArrayToInt(reverseByteArray(buffer_4)));
		
		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_sigFlags不足4字节");
			return null;
		}
		header.setSigflags(byteArrayToInt(reverseByteArray(buffer_4)));
		
		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_snaplen不足4字节");
			return null;
		}
		header.setSnaplen(byteArrayToInt(reverseByteArray(buffer_4)));

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_linkType不足4字节");
			return null;
		}
		header.setLinktype(byteArrayToInt(reverseByteArray(buffer_4)));
		
		pcap.setHeader(header);
		
		/**
		 * read pcap data zone
		 */
		List<PcapData> dataList = new ArrayList<PcapData>();
		PcapData data = new PcapData();
		byte[] content;
		while (true) {
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header time_s <4B");
				break;
			}
			data.setTime_s(byteArrayToInt(reverseByteArray(buffer_4)));
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header time_ms <4B");
				break;
			}
			data.setTime_ms(byteArrayToInt(reverseByteArray(buffer_4)));
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header pLength <4B");
				break;
			}
			data.setpLength(byteArrayToInt(reverseByteArray(buffer_4)));
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header length <4B");
				break;
			}
			data.setLength(byteArrayToInt(reverseByteArray(buffer_4)));
			int IPFrameLength = data.getpLength();
			content = new byte[IPFrameLength];
			m = is.read(content);
			if (m < IPFrameLength) {
				System.err.println("pcap package header time_s < IPFrameLength: "+IPFrameLength);
				break;
			}
			data.setContent(content);
			dataList.add(data);
		}
		pcap.setData(dataList);
		return pcap;
	}

	private static int byteArrayToInt(byte[] b) {
		int value = 0;
		int offset = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	private static short byteArrayToShort(byte[] b) {
		short value = 0;
		int offset = 0;
		for (int i = 0; i < 2; i++) {
			int shift = (2 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	/**
	 * 反转数组
	 * 
	 * @param arr
	 */
	private static byte[] reverseByteArray(byte[] arr) {
		byte temp;
		int n = arr.length;
		for (int i = 0; i < n / 2; i++) {
			temp = arr[i];
			arr[i] = arr[n - 1 - i];
			arr[n - 1 - i] = temp;
		}
		return arr;
	}
}
