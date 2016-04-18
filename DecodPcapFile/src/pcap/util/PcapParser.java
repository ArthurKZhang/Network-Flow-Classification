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
		reverseByteArray(buffer_4);
		header.setMagic(byteArrayToInt(buffer_4));

		m = is.read(buffer_2);
		if (m != 2) {
			System.err.println("header_majorVersion不足2字节");
			return null;
		}
		reverseByteArray(buffer_2);
		header.setmajor_version(byteArrayToShort(buffer_2));

		m = is.read(buffer_2);
		if (m != 2) {
			System.err.println("header_minorVersion不足2字节");
			return null;
		}
		reverseByteArray(buffer_2);
		header.setMinor_version(byteArrayToShort(buffer_2));

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_timeZone不足4字节");
			return null;
		}
		reverseByteArray(buffer_4);
		header.setTimezone(byteArrayToInt(buffer_4));

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_sigFlags不足4字节");
			return null;
		}
		reverseByteArray(buffer_4);
		header.setSigflags(byteArrayToInt(buffer_4));

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_snaplen不足4字节");
			return null;
		}
		reverseByteArray(buffer_4);
		header.setSnaplen(byteArrayToInt(buffer_4));

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_linkType不足4字节");
			return null;
		}
		reverseByteArray(buffer_4);
		header.setLinktype(byteArrayToInt(buffer_4));

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
			reverseByteArray(buffer_4);
			data.setTime_s(byteArrayToInt(buffer_4));

			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header time_ms <4B");
				break;
			}
			reverseByteArray(buffer_4);
			data.setTime_ms(byteArrayToInt(buffer_4));
			
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header pLength <4B");
				break;
			}
			int IPFrameLength00 = byteArrayToInt(buffer_4);
			reverseByteArray(buffer_4);
			int IPFrameLength = byteArrayToInt(buffer_4);
			data.setpLength(IPFrameLength);
			
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header length <4B");
				break;
			}
			reverseByteArray(buffer_4);
			data.setLength(byteArrayToInt(buffer_4));
			
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
	 * @return
	 */
	private static void reverseByteArray(byte[] arr) {
		byte temp;
		int n = arr.length;
		for (int i = 0; i < n / 2; i++) {
			temp = arr[i];
			arr[i] = arr[n - 1 - i];
			arr[n - 1 - i] = temp;
		}
	}
}
