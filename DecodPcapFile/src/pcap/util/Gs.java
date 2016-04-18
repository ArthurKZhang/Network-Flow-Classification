package pcap.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import pcap.databean.arthur.Pcap;

public class Gs {
	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream("D:\\00DATA\\BS\\1604051222.pcap");
			Pcap pcap;
			pcap = PcapParser.unpack(is);
			is.close();
			byte[] t = pcap.getData().get(0).getContent();
			/**
			 * data stores IP package
			 */
			byte[] data = Arrays.copyOfRange(t, 112, t.length);
			System.out.println(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
