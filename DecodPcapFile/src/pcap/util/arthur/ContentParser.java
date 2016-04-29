package pcap.util.arthur;

import java.util.Arrays;

public class ContentParser {
	// private byte[] frameHead = new byte[14];
	// public ContentParser

	public static byte[] getIpPackage(byte[] content) {
		byte[] ipPackage = Arrays.copyOfRange(content, 14, content.length - 1);
		return ipPackage;
	}

	public static String[] getSouAddrAndDesAddr(byte[] ipPackage) {
		String[] SouAddrAndDesAddr = new String[2];
		if (isIpv4(ipPackage)) {
			byte[] sourAddrZone = Arrays.copyOfRange(ipPackage, 12, 15);
			byte[] desAddrZone = Arrays.copyOfRange(ipPackage, 16, 19);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 4; i++) {
				sb.append((int) (sourAddrZone[i] & 0xff));
				sb.append(".");
			}
			sb.deleteCharAt(sb.length() - 1);
			SouAddrAndDesAddr[0] = sb.toString();

			sb.delete(0, sb.length() - 1);
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

	private static boolean isIpv4(byte[] content) {
		// Ethernet II 帧格式
		byte[] versionZone = Arrays.copyOfRange(content, 12, 13);
		ByteArrayUtil.reverseByteArray(versionZone);
		int version = ByteArrayUtil.byteArrayToInt(versionZone);
		if (Integer.toHexString(version).equals("0080"))
			return true;
		return false;
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
		return Arrays.copyOfRange(ipPackage, getIPHeaderLength(content), ipPackage.length - 1);
	}

	public static int[] getSouAndDesPort(byte[] content) {
		byte[] tcpPackage = getTcpPackage(content);
		int[] ports = new int[2];
		ports[0] = ByteArrayUtil.extractInteger(tcpPackage, 0, 2);
		ports[1] = ByteArrayUtil.extractInteger(tcpPackage, 2, 2);
		return ports;
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
