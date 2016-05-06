package arthur.service.file2bean;

import java.io.IOException;
import java.io.InputStream;

import arthur.bean.data.IPPacket;
import arthur.bean.pcapfiledata.Pcap;

public class PcapParser {
	/**
	 * @author Zh_张宇
	 * @author En_Arhur Parser .pcap file
	 */
	public static Pcap unpack(InputStream is) throws IOException {
		byte[] buffer_4 = new byte[4];
		byte[] buffer_2 = new byte[2];
		Pcap pcap = new Pcap();
		/**
		 * read and check pcap header zone
		 */
		int m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_megic不足4字节");
			return null;
		}

		m = is.read(buffer_2);
		if (m != 2) {
			System.err.println("header_majorVersion不足2字节");
			return null;
		}

		m = is.read(buffer_2);
		if (m != 2) {
			System.err.println("header_minorVersion不足2字节");
			return null;
		}

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_timeZone不足4字节");
			return null;
		}

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_sigFlags不足4字节");
			return null;
		}

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_snaplen不足4字节");
			return null;
		}

		m = is.read(buffer_4);
		if (m != 4) {
			System.err.println("header_linkType不足4字节");
			return null;
		}

		/**
		 * read pcap data zone
		 */
		while (m > 0) {
			IPPacket ipPacket = new IPPacket();
			// -----------------
			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header time_s <4B");
				break;
			}
			long time_s = ByteArrayUtil.extractLong(buffer_4, 0, 4);

			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header time_ms <4B");
				break;
			}
			long time_ms = ByteArrayUtil.extractLong(buffer_4, 0, 4);

			ipPacket.setTime_s(time_s);
			ipPacket.setTime_ms(time_ms);

			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header pLength <4B");
				break;
			}
			ByteArrayUtil.reverseByteArray(buffer_4);

			int IPFrameLength = ByteArrayUtil.byteArrayToInt(buffer_4);

			m = is.read(buffer_4);
			if (m < 4) {
				System.err.println("pcap package header length <4B");
				break;
			}

			byte[] content = new byte[IPFrameLength];
			m = is.read(content);
			if (m < IPFrameLength) {
				System.err.println("pcap package header time_s < IPFrameLength: " + IPFrameLength);
				break;
			}
			byte[] ipPack = ContentParser.getIpPackage(content);
			String[] souAndDesIpAddress = ContentParser.getSouAddrAndDesAddr(ipPack);
			int[] souAndDesPort = ContentParser.getSouAndDesPort(content);
			int size = ContentParser.getIPPacketSize(ipPack);
			int protocol = ContentParser.getProtocol(ipPack);
			ipPacket.set_5tuple(souAndDesIpAddress[0], souAndDesIpAddress[1], souAndDesPort[0], souAndDesPort[1], protocol);
			ipPacket.setSize(size);
			
			//save to DB
		}
		return pcap;
	}

}
