
public class User {
	private long userID;
	private String strID;
	private int value = 50;
	private long renttime;
	private Station rentstation;
	private long returntime;
	public boolean isused=false;
    	private long totalTime;	//unit:min
	private int times;


	public User(long userID,String strID)
	{
		this.userID = userID;
		this.strID=strID;
		this.value = 50;
	}

	public long getUserID() {
		return userID;
	}
	
	public String getStrID() {
		return strID;
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

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public int getTimes(){
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}


	
}
