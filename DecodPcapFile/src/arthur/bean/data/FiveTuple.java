package arthur.bean.data;

public class FiveTuple {
	private String souAddress;
	private String desAddress;
	private int souPort;
	private int desPort;
	private int protocol;// tcp--6

	public FiveTuple(String souAddress, String desAddress, int souPort, int desPort, int protocol) {
		this.souAddress = souAddress;
		this.desAddress = desAddress;
		this.souPort = souPort;
		this.desPort = desPort;
		this.protocol = protocol;
	}

	@Override
	public String toString() {
		return "FiveTuple [souAddress=" + souAddress + ", desAddress=" + desAddress + ", souPort=" + souPort
				+ ", desPort=" + desPort + ", protocol=" + protocol + "]";
	}

	public String getSouAddress() {
		return souAddress;
	}

	public void setSouAddress(String souAddress) {
		this.souAddress = souAddress;
	}

	public String getDesAddress() {
		return desAddress;
	}

	public void setDesAddress(String desAddress) {
		this.desAddress = desAddress;
	}

	public int getSouPort() {
		return souPort;
	}

	public void setSouPort(int souPort) {
		this.souPort = souPort;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desAddress == null) ? 0 : desAddress.hashCode());
		result = prime * result + desPort;
		result = prime * result + protocol;
		result = prime * result + ((souAddress == null) ? 0 : souAddress.hashCode());
		result = prime * result + souPort;
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
		FiveTuple other = (FiveTuple) obj;
		if (desAddress == null) {
			if (other.desAddress != null)
				return false;
		} else if (!desAddress.equals(other.desAddress))
			return false;
		if (desPort != other.desPort)
			return false;
		if (protocol != other.protocol)
			return false;
		if (souAddress == null) {
			if (other.souAddress != null)
				return false;
		} else if (!souAddress.equals(other.souAddress))
			return false;
		if (souPort != other.souPort)
			return false;
		return true;
	}

}
