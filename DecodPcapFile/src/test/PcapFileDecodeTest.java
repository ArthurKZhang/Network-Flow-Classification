package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;

import arthur.bean.data.FiveTuple;
import arthur.bean.data.IPPacket;
import arthur.bean.pcapfiledata.Pcap;
import arthur.service.file2bean.ContentParser;
import arthur.service.file2bean.PcapParser;

/**
 * for don't know certain content of .pcap file, we can't use JUnit test
 * framework. printing out contents is the only choice we can make.
 * 
 * @author Arthur
 */
public class PcapFileDecodeTest {
	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream("D:\\00DATA\\BS\\267num.pcap");
			Pcap pcap;
			pcap = null;//PcapParser.unpack(is);
			is.close();
			// the number of packets caught in file.
			int contentSize = pcap.getData().size();
			print("file contains " + contentSize + "packages");

			// the first package had caught
			byte[] content1 = pcap.getData().get(0).getContent();
			byte[] ipPacket1 = ContentParser.getIpPackage(content1);
			String[] AddreS = ContentParser.getSouAddrAndDesAddr(ipPacket1);
			print("Source Address: " + AddreS[0] + " Desti Address " + AddreS[1]);
			int[] ports = ContentParser.getSouAndDesPort(content1);
			print("Source Port: " + ports[0] + " Desti Port: " + ports[1]);
			int protocol =ContentParser.getProtocol(ipPacket1);
			print("Protocol: " + protocol);
			print("IP Packet Size: " + ContentParser.getIPPacketSize(ipPacket1));
			
			FiveTuple fiveTuple = new FiveTuple(AddreS[0], AddreS[1], ports[0], ports[1], protocol);
			IPPacket ipp = new IPPacket();
			ipp.set_5tuple(fiveTuple);
			ipp.setTime_s(pcap.getData().get(0).getDataheader().getTime_s());
			ipp.setSize(ContentParser.getIPPacketSize(ipPacket1));
			

			// the last packet had caught----recheck
//			byte[] content2 = pcap.getData().get(contentSize - 1).getContent();
//			byte[] ipPacket2 = ContentParser.getIpPackage(content2);
//			AddreS = ContentParser.getSouAddrAndDesAddr(ipPacket2);
//			print("Source Address: " + AddreS[0] + " Desti Address " + AddreS[1]);
//			ports = ContentParser.getSouAndDesPort(content2);
//			print("Source Port: " + ports[0] + " Desti Port: " + ports[1]);
//			print("Protocol: " + ContentParser.getProtocol(ipPacket2));
//			print("IP Packet Size: " + ContentParser.getIPPacketSize(ipPacket2));
//			
			
			print("TEST DONE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void print(String s) {
		System.out.println(s);
	}
}
/*
 * String java.util.Date.toString()
 * 
 * 
 * Converts this Date object to a String of the form:
 * 
 * dow mon dd hh:mm:ss zzz yyyy
 * 
 * where: dow is the day of the week (Sun, Mon, Tue, Wed, Thu, Fri, Sat). mon is
 * the month (Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec). dd is
 * the day of the month (01 through 31), as two decimal digits. hh is the hour
 * of the day (00 through 23), as two decimal digits. mm is the minute within
 * the hour (00 through 59), as two decimal digits. ss is the second within the
 * minute (00 through 61, as two decimal digits. zzz is the time zone (and may
 * reflect daylight saving time). Standard time zone abbreviations include those
 * recognized by the method parse. If time zone information is not available,
 * then zzz is empty - that is, it consists of no characters at all. yyyy is the
 * year, as four decimal digits. Overrides: toString() in Object Returns: a
 * string representation of this date. See Also: java.util.Date.toLocaleString()
 * java.util.Date.toGMTString()
 */
