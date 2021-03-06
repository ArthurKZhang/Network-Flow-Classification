package arthur.bean.pcapfiledata;

public class PcapHeader {
	/**
	 * @author Zh_张宇
	 * @author En_Arhur .pcap file Header zone data bean
	 */

	private int magic;// 4B 文件识别头,为0xA1B2C3D4
	private short major_version;// 2B 主要版本
	private short minor_version;// 2B 次要版本
	private int timezone;// 4B 区域时间
	private int sigflags;// 4B 精确时间戳
	private int snaplen;// 4B 数据包最大长度
	private int linktype;// 4B 链路层类型

	public int getMagic() {
		return magic;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	public short getmajor_version() {
		return major_version;
	}

	public void setmajor_version(short major_version) {
		this.major_version = major_version;
	}

	public short getMinor_version() {
		return minor_version;
	}

	public void setMinor_version(short minor_version) {
		this.minor_version = minor_version;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public int getSigflags() {
		return sigflags;
	}

	public void setSigflags(int sigflags) {
		this.sigflags = sigflags;
	}

	public int getSnaplen() {
		return snaplen;
	}

	public void setSnaplen(int snaplen) {
		this.snaplen = snaplen;
	}

	public int getLinktype() {
		return linktype;
	}

	public void setLinktype(int linktype) {
		this.linktype = linktype;
	}

	@Override
	public String toString() {
		return "PcapHeader [magic=" + magic + ", major_version="
				+ major_version + ", minor_version=" + minor_version
				+ ", timezone=" + timezone + ", sigflags=" + sigflags
				+ ", snaplen=" + snaplen + ", linktype=" + linktype + "]";
	}

	/*
	 * LINKTYPE MAPPING List
	 * 0 BSD loopback devices, except for later OpenBSD
	 * 1 Ethernet, and Linux loopback devices
	 * 6 802.5 Token Ring
	 * 7 ARCnet
	 * 8 SLIP
	 * 9 PPP
	 * 10 FDDI
	 * 100 LLC/SNAP-encapsulated ATM
	 * 101 “raw IP”, with no link
	 * 102 BSD/OS SLIP
	 * 103 BSD/OS PPP
	 * 104 Cisco HDLC 
	 * 105 802.11
	 * 108 later OpenBSD loopback devices (with the AF_value in network byte order)
	 * 113 special Linux “cooked” capture
	 * 114 LocalTalk
	 */

}
