package arthur.service.file2bean;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

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

	public static String byteArrayToString(byte[] bs) {
		String string = new String(bs);
		return string;
	}
	
	public static char[] byteArrayTochar(byte[] b) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(b.length);
		bb.put(b);
		bb.flip();
		CharBuffer cb = cs.decode(bb);

		return cb.array();

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

	/**
	 * Extract an integer from a byte array.
	 *
	 * @param bytes
	 *            an array.
	 * @param pos
	 *            the starting position where the integer is stored.
	 * @param cnt
	 *            the number of bytes which contain the integer.
	 * @return the integer, or <b>0</b> if the index/length to use would cause
	 *         an ArrayOutOfBoundsException
	 */
	public static int extractInteger(byte[] bytes, int pos, int cnt) {
		// commented out because it seems like it might mask a fundamental
		// problem, if the caller is referencing positions out of bounds and
		// silently getting back '0'.
		// if((pos + cnt) > bytes.length) return 0;
		int value = 0;
		for (int i = 0; i < cnt; i++)
			value |= ((bytes[pos + cnt - i - 1] & 0xff) << 8 * i);

		return value;
	}
	
	/**
	   * Extract a long from a byte array.
	   *
	   * @param bytes an array.
	   * @param pos the starting position where the integer is stored.
	   * @param cnt the number of bytes which contain the integer.
	   * @return the long, or <b>0</b> if the index/length to use 
	   *         would cause an ArrayOutOfBoundsException
	   */
	  public static long extractLong(byte[] bytes, int pos, int cnt) {
	    // commented out because it seems like it might mask a fundamental 
	    // problem, if the caller is referencing positions out of bounds and 
	    // silently getting back '0'.
	    //   if((pos + cnt) > bytes.length) return 0;
	    long value = 0;
	    for(int i=0; i<cnt; i++)
	      value |= ((bytes[pos + cnt - i - 1] & 0xffL) << 8 * i);

	    return value;
	  }
}
