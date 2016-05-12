package arthur.bean.data;

import org.bson.Document;

public class IPPacket {
	// ip���ĵ����ݺܶ඼���ò�����
	// �����ǻ���ͳ��ѧ�ģ����Լ�¼�����Ϣ�ͺ�
	private long time_s;
	private long time_ms;
	private int Size;
	private FiveTuple _5tuple;
	
	public IPPacket(){}
	
	public IPPacket(Document d) {
		this.Size = d.getInteger("size");
		this.time_s = d.getLong("time_s");
		this.time_ms = d.getLong("time_ms");
		this._5tuple = new FiveTuple(d.getString("sourceAddress"), 
				d.getString("destinAddress"), 
				d.getInteger("sourcePort"), 
				d.getInteger("destinPort"), 
				d.getInteger("protocol"));
	}
	
	@Override
	public String toString() {
		return "IPPacket [time_s=" + time_s + ", time_ms=" + time_ms + ", Size=" + Size + ", _5tuple=" + _5tuple + "]";
	}

	public long getTime_s() {
		return time_s;
	}

	public void setTime_s(long time_s) {
		this.time_s = time_s;
	}

	public long getTime_ms() {
		return time_ms;
	}

	public void setTime_ms(long time_ms) {
		this.time_ms = time_ms;
	}

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}

	public FiveTuple get_5tuple() {
		return _5tuple;
	}

	public void set_5tuple(FiveTuple _5tuple) {
		this._5tuple = _5tuple;
	}

	public void set_5tuple(String souAddress, String desAddress, int souPort, int desPort, int protocol) {
		this._5tuple = new FiveTuple(souAddress, desAddress, souPort, desPort, protocol);
	}
}
