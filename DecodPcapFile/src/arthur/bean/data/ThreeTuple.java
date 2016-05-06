package arthur.bean.data;

public class ThreeTuple {
	private String desAddress;
	private int desPort;
	private int protocol;// tcp--6

	public ThreeTuple(String desAddress, int desPort, int protocol) {
		super();
		this.desAddress = desAddress;
		this.desPort = desPort;
		this.protocol = protocol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desAddress == null) ? 0 : desAddress.hashCode());
		result = prime * result + desPort;
		result = prime * result + protocol;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThreeTuple other = (ThreeTuple) obj;
		if (desAddress == null) {
			if (other.desAddress != null)
				return false;
		} else if (!desAddress.equals(other.desAddress))
			return false;
		if (desPort != other.desPort)
			return false;
		if (protocol != other.protocol)
			return false;
		return true;
	}

	public String getDesAddress() {
		return desAddress;
	}

	public void setDesAddress(String desAddress) {
		this.desAddress = desAddress;
	}

	public int getDesPort() {
		return desPort;
	}

	public void setDesPort(int desPort) {
		this.desPort = desPort;
	}

	public int getProtocol() {
		return protocol;
	}

	public void setProtocol(byte protocol) {
		this.protocol = protocol;
	}

}
