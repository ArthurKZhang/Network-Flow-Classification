package arthur.bean.data;

public class IPPacket {
	// ip报文的内容很多都是用不到的
	// 这里是基于统计学的，所以记录相关信息就好
	private long id;
	private long time_s;
	private long time_ms;
	private int Size;
	private FiveTuple _5tuple;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
