package dao.databean.arthur;

public class IPPacket {
	// ip报文的内容很多都是用不到的
	// 这里是基于统计学的，所以记录相关信息就好
	private long id;
	private long arriveTime;
	private int Size;
	private FiveTuple _5tuple;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(long arriveTime) {
		this.arriveTime = arriveTime;
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

	public void set_5tuple(String souAddress, String desAddress, int souPort, int desPort, byte protocol) {
		this._5tuple = new FiveTuple(souAddress, desAddress, souPort, desPort, protocol);
	}
}
