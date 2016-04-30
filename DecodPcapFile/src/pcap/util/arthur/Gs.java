package pcap.util.arthur;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import pcap.databean.arthur.Pcap;

public class Gs {
	/*
	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream("D:\\00DATA\\BS\\267num.pcap");
			Pcap pcap;
			pcap = PcapParser.unpack(is);
			is.close();
			byte[] t = pcap.getData().get(0).getContent();
			System.out.println(pcap.getData().size());
			 // data stores IP package
			byte[] data = Arrays.copyOfRange(t, 14, t.length);
			char[] chars = ByteArrayUtil.byteArrayTochar(data);
			for(int i = 0;i<chars.length;i++)
			System.out.println(chars[i]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	

	

}
