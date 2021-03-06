package arthur.service.file2bean;

import java.util.Arrays;

public class ContentParser {
	// private byte[] frameHead = new byte[14];
	// public ContentParser

	public static byte[] getIpPackage(byte[] content) {
		byte[] ipPackage = Arrays.copyOfRange(content, 14, content.length);
		return ipPackage;
	}

	public static String[] getSouAddrAndDesAddr(byte[] ipPackage) {
		String[] SouAddrAndDesAddr = new String[2];
		SouAddrAndDesAddr[0] = "0";
		SouAddrAndDesAddr[1] = "0";
		boolean b = isIpv42(ipPackage);
		if (b) {
			byte[] sourAddrZone = Arrays.copyOfRange(ipPackage, 12, 16);
			byte[] desAddrZone = Arrays.copyOfRange(ipPackage, 16, 20);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 4; i++) {
				sb.append((int) (sourAddrZone[i] & 0xff));
				sb.append(".");
			}
			sb.deleteCharAt(sb.length() - 1);
			SouAddrAndDesAddr[0] = sb.toString();

			sb.setLength(0);
			for (int i = 0; i < 4; i++) {
				sb.append((int) (desAddrZone[i] & 0xff));
				sb.append(".");
			}
			sb.deleteCharAt(sb.length() - 1);
			SouAddrAndDesAddr[1] = sb.toString();
		}
		// else {
		// byte[] sourAddrZone = Arrays.copyOfRange(ipPackage, 8, 24);
		// byte[] desAddrZone = Arrays.copyOfRange(ipPackage, 24, 40);
		// StringBuffer sb = new StringBuffer();
		// for (int i = 0; i < 8; i++) {
		// sb.append(Integer.toHexString((int) sourAddrZone[i * 2]));
		// sb.append(Integer.toHexString((int) sourAddrZone[i * 2 + 1]));
		// sb.append(":");
		// }
		// sb.deleteCharAt(sb.length() - 1);
		// SouAddrAndDesAddr[0] = sb.toString();
		//
		// sb.delete(0, sb.length() - 1);
		// for (int i = 0; i < 8; i++) {
		// sb.append(Integer.toHexString((int) desAddrZone[i * 2]));
		// sb.append(Integer.toHexString((int) desAddrZone[i * 2 + 1]));
		// sb.append(":");
		// }
		// sb.deleteCharAt(sb.length() - 1);
		// SouAddrAndDesAddr[1] = sb.toString();
		// }
		return SouAddrAndDesAddr;
	}

	public static boolean isIpv42(byte[] ipPacket) {
		int v = (ByteArrayUtil.extractInteger(ipPacket, 0, 1) >> 4) & 0xf;
//		System.out.println("---v---- "+v);
		return v == 4 ? true : false;
	}

	private static boolean isIpv4(byte[] content) {
//		// Ethernet II 帧格式
//		byte[] versionZone = Arrays.copyOfRange(content, 12, 13);
//		ByteArrayUtil.reverseByteArray(versionZone);
//		int version = ByteArrayUtil.byteArrayToInt(versionZone);
//		if (Integer.toHexString(version).equals("0080"))
//			return true;
//		return false;
		return isIpv42(getIpPackage(content));
	}

	private static int getIPHeaderLength(byte[] content) {
		byte[] ipPackage = getIpPackage(content);
		int length;
		// ipv4
		if (isIpv4(content)) {
			length = (ByteArrayUtil.extractInteger(ipPackage, 0, 1) & 0xf) * 4;
		} else {
			// ipv6 with no "Next Header"
			length = 40;// ipv6暂时不处理
		}
		return length;
	}

	public static byte[] getTcpPackage(byte[] content) {
		byte[] ipPackage = getIpPackage(content);
		return Arrays.copyOfRange(ipPackage, getIPHeaderLength(content), ipPackage.length);
	}

	public static int[] getSouAndDesPort(byte[] content) {
		byte[] tcpPackage = getTcpPackage(content);
		int[] ports = new int[2];
		ports[0] = ByteArrayUtil.extractInteger(tcpPackage, 0, 2);
		ports[1] = ByteArrayUtil.extractInteger(tcpPackage, 2, 2);
		return ports;
	}

	public static int getProtocol(byte[] ipPacket) {
		int p = ByteArrayUtil.extractInteger(ipPacket, 9, 1);
		return p;
	}

	public static int getIPPacketSize(byte[] ipPacket) {
		return ByteArrayUtil.extractInteger(ipPacket, 2, 2);
	}
	/*
	 * for(int i=0;i<4;i++){ sbr.append((int)(content[b]&0xff));
	 * sbr.append("."); }
	 */

	/*
	 * 
	 * //数据帧头
	 * 
	 * typedef struct FramHeader_t { //Pcap捕获的数据帧头 u_int8 DstMAC[6]; //目的MAC地址
	 * u_int8 SrcMAC[6]; //源MAC地址 u_short FrameType; //帧类型 } FramHeader_t;
	 */
}
