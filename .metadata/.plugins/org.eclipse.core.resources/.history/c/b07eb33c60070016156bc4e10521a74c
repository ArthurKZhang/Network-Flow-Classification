package pcap.util;

public class ByteArrayUtil {

	public static int byteArrayToInt(byte[] b) {
		int value = 0;
		int offset = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	public static short byteArrayToShort(byte[] b) {
		short value = 0;
		int offset = 0;
		for (int i = 0; i < 2; i++) {
			int shift = (2 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}
	
	public static String byteArrayToString(byte[] bs){
		String string = new String(bs);
		return string;
	}

	/**
	 * 反转数组
	 * 
	 * @param arr
	 * @return
	 */
	public static void reverseByteArray(byte[] arr) {
		byte temp;
		int n = arr.length;
		for (int i = 0; i < n / 2; i++) {
			temp = arr[i];
			arr[i] = arr[n - 1 - i];
			arr[n - 1 - i] = temp;
		}
	}
}
