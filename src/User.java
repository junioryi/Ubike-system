
public class User {
	private long userID;
	private int value = 50;
	private long renttime;
	private Station rentstation;
	private long returntime;
	public boolean isused=false;

	public User(long userID)
	{
		this.userID = userID;
		this.value = 50;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public long getRenttime() {
		return renttime;
	}

	public void setRenttime(long renttime) {
		this.renttime = renttime;
	}

	public long getReturntime() {
		return returntime;
	}

	public void setReturntime(long returntime) {
		this.returntime = returntime;
	}

	public Station getRentstation() {
		return rentstation;
	}

	public void setRentstation(Station rentstation) {
		this.rentstation = rentstation;
	}


}
